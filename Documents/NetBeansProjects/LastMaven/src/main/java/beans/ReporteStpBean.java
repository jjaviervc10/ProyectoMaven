package beans;

import controller.HVentaJpaController;
import entity.HVenta;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import objetos.Empresas;
import objetos.Sucursales;
import respuestas.RespuestaReporteHistorico;
import utils.LocalEntityManagerFactory;

/**
 *
 * @author Blueweb
 */
@Named("reporteStpBean")
@ViewScoped
public class ReporteStpBean implements Serializable {

    private RespuestaReporteHistorico<List<HVenta>> resConsulta;
    private Date fechaInicio;
    private Date fechaFin;
    private Long empresaId;
    private Long sucursalId;
    private List<Empresas> empresas;
    private List<Sucursales> sucursales;
    private List<HVenta> ventas;
    private HVentaJpaController hVentaController;
    private Timestamp fechaInicioTimes;
    private Timestamp fechaFinTimes;

    
   public ReporteStpBean() {
      
    }
   
    @PostConstruct
     public void ReporteSTPBean() {
        try {
          // Ahora inicializamos el EntityManagerFactory después de la construcción del bean
            EntityManagerFactory emf = LocalEntityManagerFactory.getEntityManagerFactory();
            hVentaController = new HVentaJpaController(emf);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // Aquí puedes manejar la excepción si ocurre antes de la inicialización
        }
     
     }
  /*  public ReporteStpBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_LastMaven_war_1.0-SNAPSHOTPU");
        hVentaController = new HVentaJpaController(emf);

    }*/

    public void generarReporte() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        System.out.println("Iniciando búsqueda de ventas con STP...");

        if (fechaInicio == null || fechaFin == null) {
            System.out.println("Error: Fecha de inicio o fin no definidas.");
            return;
        }

        try {
        
            fechaInicioTimes = convertirATimestamp(fechaInicio, "inicio");
            fechaFinTimes = convertirATimestamp(fechaFin, "fin");

            
            resConsulta = hVentaController.obtenerVentasPorFiltros(fechaInicioTimes, fechaFinTimes);

            
            switch (resConsulta.getCodigo()) {
                case 0: // Consulta exitosa
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Consulta Exitosa", resConsulta.getMensaje()));
                    ventas = resConsulta.getDatos();
                    System.out.println("Ventas encontradas: " + ventas.size());
                    break;

                case 1: // No se encontraron ventas
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", resConsulta.getMensaje()));
                    ventas = null;
                    break;

                case -1: // Error en la base de datos
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la Base de Datos", resConsulta.getMensaje()));
                    ventas = null;
                    break;

                case -2: // Error inesperado
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Inesperado", resConsulta.getMensaje()));
                    ventas = null;
                    break;

                default: // Código desconocido
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Desconocido", "Código de error desconocido."));
                    ventas = null;
            }

        } catch (Exception e) {
            System.out.println("Error al procesar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        FacesContext.getCurrentInstance().renderResponse();
    }

    
    private Timestamp convertirATimestamp(Date fecha, String tipo) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaStr = new SimpleDateFormat("yyyy-MM-dd").format(fecha);

        if ("inicio".equals(tipo)) {
            fechaStr += " 00:00:00";
        } else if ("fin".equals(tipo)) {
            fechaStr += " 23:59:59";
        }

        Date fechaRango = sdf.parse(fechaStr);
        return new Timestamp(fechaRango.getTime());
    }

    // Métodos getter y setter
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public List<Empresas> getEmpresas() {
        return empresas;
    }

    public List<Sucursales> getSucursales() {
        return sucursales;
    }

    public List<HVenta> getVentas() {
        return ventas;
    }

    public void setVentas(List<HVenta> ventas) {
        this.ventas = ventas;
    }

    public Timestamp getFechaInicioTimes() {
        return fechaInicioTimes;
    }

    public Timestamp getFechaFinTimes() {
        return fechaFinTimes;
    }

    private void setResConsulta(RespuestaReporteHistorico<List<HVenta>> resConsulta) {
        this.resConsulta = resConsulta;
    }

    public RespuestaReporteHistorico<List<HVenta>> getResConsulta() {
        return resConsulta;
    }
}
