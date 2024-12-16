package beans;

import controller.HVentaJpaController;
import entity.HVenta;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import entity.MVenta;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import respuestas.RespuestaReporteHistorico;
import utils.LocalEntityManagerFactory;

@Named("reporteBean")
@ViewScoped
public class ReporteBean implements Serializable {

    private Date fechaInicio;
    private Date fechaFin;
    private List<HVenta> ventas;
    private int totalRegistros;
    private RespuestaReporteHistorico<List<HVenta>> respuestaConsulta;
    private HVentaJpaController hVentaController;  // Aquí lo declaramos sin la inyección

  /*  public ReporteBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_LastMaven_war_1.0-SNAPSHOTPU");

        hVentaController = new HVentaJpaController(emf);
    }*/

    
    public ReporteBean() {
    }
     // Constructor
    @PostConstruct
     public void ReporteBean() {
        try {
          // Ahora inicializamos el EntityManagerFactory después de la construcción del bean
            EntityManagerFactory emf = LocalEntityManagerFactory.getEntityManagerFactory();
            hVentaController = new HVentaJpaController(emf);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // Aquí puedes manejar la excepción si ocurre antes de la inicialización
        }
     
     }
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<HVenta> getVentas() {
        return ventas;
    }

    public void setVentas(List<HVenta> ventas) {
        this.ventas = ventas;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public RespuestaReporteHistorico<List<HVenta>> getRespuestaConsulta() {
        return respuestaConsulta;
    }

    public void setRespuestaConsulta(RespuestaReporteHistorico<List<HVenta>> respuestaConsulta) {
        this.respuestaConsulta = respuestaConsulta;
    }

    public void buscarVentas() {
        Timestamp fechaInicioTimes;
        Timestamp fechaFinTimes;

        System.out.println("Iniciando búsqueda de ventas...");

        if (fechaInicio == null || fechaFin == null) {
            System.out.println("Error: fechaInicio o fechaFin son null");
            return;
        }

        System.out.println("Fecha Inicio original: " + fechaInicio);
        System.out.println("Fecha Fin original: " + fechaFin);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String fechaInicioStr = sdf.format(fechaInicio);
            String fechaFinStr = sdf.format(fechaFin);

            fechaInicioStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio) + " 00:00:00";
            fechaInicio = sdf.parse(fechaInicioStr);
            System.out.println("Fecha Inicio formateada: " + fechaInicio);

            fechaFinStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin) + " 23:59:59";
            fechaFin = sdf.parse(fechaFinStr);
            System.out.println("Fecha Fin formateada: " + fechaFin);

            fechaInicioTimes = new Timestamp(fechaInicio.getTime());
            fechaFinTimes = new Timestamp(fechaFin.getTime());

            System.out.println("Fecha Inicio Timestamp: " + fechaInicioTimes);
            System.out.println("Fecha Fin Timestamp: " + fechaFinTimes);

        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (hVentaController.findHVentaEntities() == null) {
            System.out.println("Error: hVentaController es null");
            return;
        } else {
            System.out.println("Exito hVentaController en funcionamiento");
        }

        if (fechaInicioTimes != null && fechaFinTimes != null) {
            // Si las fechas son válidas, llamar a la lógica del controlador para obtener las ventas por fecha
            respuestaConsulta = hVentaController.obtenerVentasPorFecha(fechaInicioTimes, fechaFinTimes);
 
            
            switch (respuestaConsulta.getCodigo()) {
                case 0:
                    {
                        // Consulta exitosa, se encontró ventas
                        ventas = respuestaConsulta.getDatos();
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reporte Histórico", "Consulta exitosa, registros encontrados: " + ventas.size()));
                        System.out.println("Se encontraron " + ventas.size() + " ventas.");
                        break;
                    }
                case 1:
                    {
                        // Si no se encontraron registros
                        ventas = new ArrayList<>();
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reporte Histórico", respuestaConsulta.getMensaje()));
                        System.out.println(respuestaConsulta.getMensaje());
                        break;
                    }
                default:
                    {
                     
                        ventas = new ArrayList<>();
                        FacesContext facesContext = FacesContext.getCurrentInstance();
                        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuestaConsulta.getMensaje()));
                        System.out.println(respuestaConsulta.getMensaje());
                        break;
                    }
            }

        } else {
          
            respuestaConsulta = new RespuestaReporteHistorico<>(-1, "Las fechas no son válidas", null);
            ventas = new ArrayList<>();  // Evita que sea null
        }
    }

}
