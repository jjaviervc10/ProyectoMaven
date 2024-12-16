package beans;

import model.CatalogSucursal;
//import model.ResultadoConsulta;
import objetos.Sucursales;
import objetos.Empresas;
import model.CatalogEmpresa;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;
import java.util.ArrayList;
import beans.LoginBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import sesiones.Sesion;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import respuestas.RespuestaSucursal;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "sucursalBean")
@ViewScoped
public class SucursalBean implements Serializable {

    private List<Sucursales> listaSucursales;
    private List<Empresas> listaEmpresas;
    private CatalogEmpresa catalogEmpresa;
    private CatalogSucursal catalogSucursal;
    private EmpresaBean empresaBean;
    private RespuestaSucursal resSucursal;
    private Sucursales SucursalAEliminar;
    private Sucursales nuevaSucursal;
    private int selectedIdEmpresa;
    private boolean fechaServidorManipulada = false;
        private int selectedPerfil;

    public SucursalBean() {
        System.out.println("Bean SucursalBean inicializado");
        catalogSucursal = new CatalogSucursal();
        nuevaSucursal = new Sucursales();
        SucursalAEliminar = new Sucursales();
        catalogEmpresa = new CatalogEmpresa();
        empresaBean = new EmpresaBean();

        loadServicios();
        loadEmpresas();
    }

    public void loadServicios() {
        setResSucursal(catalogSucursal.getListaSucursal());
        if (getResSucursal().getId() == 0) {
            listaSucursales = getResSucursal().getListaSucursal();
        }
    }

    public void loadEmpresas() {
        listaEmpresas = empresaBean.getListaEmpresa(); // Llama al método del EmpresaBean
    }

    public void onRowEdit(RowEditEvent event) {
        Sucursales sucursal = (Sucursales) event.getObject();

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");

        RespuestaSucursal respuesta = catalogSucursal.updateSucursal(sucursal, idUsuario, sucursal.getIdSucursal());

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuesta.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición exitosa", "La sucursal " + sucursal.getNombreSucursal() + " ha sido actualizada."));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Sucursales sucursal = (Sucursales) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "Los cambios en la sucursal " + sucursal.getNombreSucursal() + " han sido revertidos."));
    }

    public void reset() {
        PrimeFaces.current().resetInputs("mainForm:panel");
    }

    public void guardarNuevaSucursal() {
        boolean hasError = false;

        if (!fechaServidorManipulada) {
            nuevaSucursal.setFechaServidor(null); // Asigna null si no fue manipulada
        } else {
            if (nuevaSucursal.getFechaServidor() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La Fecha Servidor es obligatoria."));
                hasError = true;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Fecha Servidor: " + (nuevaSucursal.getFechaServidor() != null ? sdf.format(nuevaSucursal.getFechaServidor()) : "No definida"));

        if (nuevaSucursal.getNombreSucursal() == null || nuevaSucursal.getNombreSucursal().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El Nombre Empresa no puede estar vacío."));
            hasError = true;
        }
        if (nuevaSucursal.getCiudad() == null || nuevaSucursal.getCiudad().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La Clave Empresa no puede estar vacía."));
            hasError = true;
        }
        if (nuevaSucursal.getEstado() == null || nuevaSucursal.getEstado().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La Clave Empresa no puede estar vacía."));
            hasError = true;
        }

        if (selectedIdEmpresa <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una empresa."));
            hasError = true;
        }
        if (hasError) {
            return;
        }

        nuevaSucursal.setIdEmpresa(selectedIdEmpresa);

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");

        RespuestaSucursal respuesta = catalogSucursal.insertSucursal(nuevaSucursal, idUsuario);
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuesta.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "La sucursal " + nuevaSucursal.getNombreSucursal() + " ha sido añadida."));

            // String statusEmpresa = nuevaSucursal.getStatusEmpresa() ? "Activa" : "Inactiva";
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalles de la Empresa",
                    "Nombre Sucursal: " + nuevaSucursal.getNombreSucursal()
                    + ",Ciudad: " + nuevaSucursal.getCiudad()
                    + ", Estado: " + nuevaSucursal.getEstado()
                    //    + ", Fecha Alta" + nuevaSucursal.getFechaAlta()
                    + ", Fecha Servidor: " + (nuevaSucursal.getFechaServidor() != null ? sdf.format(nuevaSucursal.getFechaServidor()) : "No definida")));

            listaSucursales.add(nuevaSucursal);
            nuevaSucursal = new Sucursales();
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));
        }

    }

    public void eliminarSucursal(int idSucursal) {

        SucursalAEliminar.setIdSucursal(idSucursal);

        try {

            RespuestaSucursal respuesta = catalogSucursal.deleteSucursal(SucursalAEliminar);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (respuesta.getId() == 0) {

                for (int i = 0; i < listaSucursales.size(); i++) {
                    if (listaSucursales.get(i).getIdSucursal() == idSucursal) {
                        listaSucursales.remove(i);
                        break;
                    }
                }
            facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La sucursal con el ID: " + idSucursal  + " ha sido eliminada correctamente."));
               

            } else {
                facesContext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Error",respuesta.getMensaje()));
    
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la sucursal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Sucursales> getListaSucursal() {
        return listaSucursales;
    }

    public RespuestaSucursal getResSucursal() {
        return resSucursal;
    }

    /**
     * @param resSucursal the resSucursal to set
     */
    public void setResSucursal(RespuestaSucursal resSucursal) {
        this.resSucursal = resSucursal;
    }

    public Sucursales getNuevaSucursal() {
        return nuevaSucursal;
    }

    public void setNuevaSucursal(Sucursales nuevaSucursal) {
        this.nuevaSucursal = nuevaSucursal;
    }

    public List<Empresas> getListaEmpresas() {
        return listaEmpresas;
    }

    public int getSelectedIdEmpresa() {
        return selectedIdEmpresa;
    }

    public void setSelectedIdEmpresa(int selectedIdEmpresa) {
        this.selectedIdEmpresa = selectedIdEmpresa;
        System.out.println("Empresa seleccionada:" + selectedIdEmpresa);
    }

    public void fechaServidorCambiada() {
        this.fechaServidorManipulada = true; // Marcar como manipulada
    }

    
    
    public void seleccionarPerfil(int Perfil) {

        selectedPerfil = Perfil;
        System.out.println("Valor del idPerfil en el setter desde pantalla: " + selectedPerfil);
        // System.out.println("Valor del idPerfilAcceso en el setter desde pantalla: " + selectedPerfil.getIdPerfil());
        PrimeFaces.current().executeScript("PF('dialogoPickList').show();");

    }

      public void guardarAccesos() {

        System.out.println("Valor del idPerfil en boton save: " + selectedPerfil);

    }

}
