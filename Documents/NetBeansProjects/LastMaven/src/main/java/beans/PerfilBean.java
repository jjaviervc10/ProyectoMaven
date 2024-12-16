package beans;

import model.CatalogPerfil;
import objetos.Perfil;
import objetos.PerfilAcceso;
import objetos.Acceso;
import respuestas.RespuestaPerfil;
import sesiones.Sesion;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import org.primefaces.PrimeFaces;
import static org.primefaces.component.menuitem.UIMenuItemBase.PropertyKeys.target;

@ManagedBean(name = "perfilBean")
@ViewScoped

public class PerfilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Perfil> listaPerfiles;
    private int selectedPerfil;
    private CatalogPerfil catalogPerfil;
    private RespuestaPerfil resPerfil;
    private Perfil perfil = new Perfil();
    private Perfil nuevoPerfil;
    private Perfil PerfilAEliminar;
    private DualListModel<Acceso> dualistaAccesos;
    private List<Acceso> accesosTarget;
    private List<Acceso> accesosSource;

    public PerfilBean() {
        nuevoPerfil = new Perfil();
        PerfilAEliminar = new Perfil();
        catalogPerfil = new CatalogPerfil();
        accesosSource = new ArrayList<>();
        accesosTarget = new ArrayList<>();
        dualistaAccesos = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
        loadPerfiles();

    }

    public void loadPerfiles() {
        setResPerfil(catalogPerfil.getListaPerfil());
        if (getResPerfil().getId() == 0) {
            listaPerfiles = getResPerfil().getListaPerfil();
        }

    }
 
    public void loadAccesos() {
        System.out.println("Valor del idPerfil en boton save Load accesos: " + selectedPerfil);

        List<Acceso> accesosAsignados = catalogPerfil.obtenerAccesosAsignados(selectedPerfil);
        System.out.println("Accesos asiganos carganodose:" + accesosAsignados);

        List<Acceso> accesosDisponibles = catalogPerfil.obtenerAccesosDisponibles(selectedPerfil);
        System.out.println("Accesos source carganodose:" + accesosDisponibles);

        List<Acceso> accesosSource = new ArrayList<>();

        for (Acceso accesoDisponible : accesosDisponibles) {
            boolean accesoYaAsignado = false;
            for (Acceso accesoAsignado : accesosAsignados) {
                System.out.println("Accesos asiganos carganodse: " + accesosAsignados);
                if (accesoDisponible.getidAcceso() == accesoAsignado.getidAcceso()) {
                    accesoYaAsignado = true;  // Este acceso ya está asignado
                    break;
                }
            }
            if (!accesoYaAsignado) {
                accesosSource.add(accesoDisponible);
            }
        }

        List<Acceso> accesosTarget = new ArrayList<>(accesosAsignados);

        dualistaAccesos = new DualListModel<>(accesosSource, accesosTarget);

        System.out.println("Accesos disponibles en listSource: ");
        for (Acceso acceso : accesosSource) {
            System.out.println(acceso.getNombreAcceso());
        }

        System.out.println("Accesos asignados en listTarget: ");
        for (Acceso acceso : accesosTarget) {
            System.out.println(acceso.getNombreAcceso());
        }
    }

    public void seleccionarPerfil(Perfil perfil) {

        selectedPerfil = perfil.getIdPerfil();
        System.out.println("Valor del idPerfil en el setter desde pantalla: " + selectedPerfil);
        // System.out.println("Valor del idPerfilAcceso en el setter desde pantalla: " + selectedPerfil.getIdPerfil());
        if (selectedPerfil != 0) {
            loadAccesos();
            PrimeFaces.current().executeScript("PF('dialogoPickList').show();");
        }

    }

    public int getSelectedPerfil() {
        return selectedPerfil;
    }

    public void guardarAccesos() {

        List<Acceso> accesosSource = dualistaAccesos.getSource();
        List<Acceso> accesosTarget = dualistaAccesos.getTarget();

        System.out.println("Valor del idPerfil en boton save: " + selectedPerfil);
        System.out.println("Valores en lista de disponibles: " + accesosSource);
        System.out.println("Valores en lista de seleccionados basedata: " + accesosTarget);

        if (accesosTarget == null || accesosTarget.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se seleccionaron accesos para asignar.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        // Imprimir la lista de accesos seleccionados
        System.out.println("Accesos seleccionados: ");
        for (Acceso acceso : accesosTarget) {
            System.out.println(acceso.getNombreAcceso());  // Muestra el nombre del acceso
        }

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");
        // Llamar al servicio para guardar los accesos asignados
        RespuestaPerfil respuesta = catalogPerfil.guardarAccesosAsignados(selectedPerfil, accesosTarget,idUsuario);

        // Manejar la respuesta
        if (respuesta.getId() == 0) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Accesos asignados correctamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        PrimeFaces.current().ajax().update("perfilForm:pickList");
        // Recargar la lista de accesos para que el usuario vea los cambios reflejados
        loadAccesos();

    }

    public void guardarNuevoPerfil() {

        boolean hasError = false;

        if (nuevoPerfil.getNombrePerfil() == null || nuevoPerfil.getNombrePerfil().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El Nombre Perfil no puede estar vacío."));
            hasError = true;
        }
        if (nuevoPerfil.getDescripcionPerfil() == null || nuevoPerfil.getDescripcionPerfil().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Descripcion Perfil no puede estar vacía."));
            hasError = true;
        }

        if (hasError) {
            return;
        }
        nuevoPerfil.setIdPerfil(selectedPerfil);

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");

        RespuestaPerfil respuestaPerfil = catalogPerfil.insertPerfil(nuevoPerfil, idUsuario);
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuestaPerfil.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "La sucursal " + nuevoPerfil.getNombrePerfil() + " ha sido añadida."));

            // String statusEmpresa = nuevaSucursal.getStatusEmpresa() ? "Activa" : "Inactiva";
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalles de la Empresa",
                    "Nombre Perfil: " + nuevoPerfil.getNombrePerfil()
                    + ",Descripcion: " + nuevoPerfil.getDescripcionPerfil()
            ));

            listaPerfiles.add(nuevoPerfil);
            nuevoPerfil = new Perfil();
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuestaPerfil.getMensaje()));
        }

    }

    public void eliminarPerfil(int idPerfil) {
        PerfilAEliminar.setIdPerfil(idPerfil);

        try {

            RespuestaPerfil respuesta = catalogPerfil.deletePerfiles(PerfilAEliminar);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            if (respuesta.getId() == 0) {

                for (int i = 0; i < listaPerfiles.size(); i++) {
                    if (listaPerfiles.get(i).getIdPerfil() == idPerfil) {
                        listaPerfiles.remove(i);
                        break;
                    }
                }
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La sucursal con el ID: " + idPerfil + " ha sido eliminada correctamente."));

            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));

            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la sucursal: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void onRowEdit(RowEditEvent event) {
        Perfil perfil = (Perfil) event.getObject();

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");

        RespuestaPerfil respuesta = catalogPerfil.updatePerfil(perfil, idUsuario, perfil.getIdPerfil());

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuesta.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición exitosa", "El perfil " + perfil.getNombrePerfil() + " ha sido actualizado."));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Perfil perfil = (Perfil) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "Los cambios en el perfil" + perfil.getNombrePerfil() + " han sido revertidos."));
    }

    public void reset() {
        PrimeFaces.current().resetInputs("perfilForm:panel");
    }

    public Perfil getNuevoPerfil() {
        return nuevoPerfil;
    }

    public void setNuevoPerfil(Perfil nuevoPerfil) {
        this.nuevoPerfil = nuevoPerfil;
    }

    /*Seccion de prueba*/
    public List<Acceso> getAccesosSource() {
        return accesosSource;
    }

    public void setAccesosSource(List<Acceso> accesosSource) {
        this.accesosSource = accesosSource;
    }

    public List<Acceso> getAccesosTarget() {
        return accesosTarget;
    }

    public void setAccesosTarget(List<Acceso> accesosTarget) {
        this.accesosTarget = accesosTarget;
    }

    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    private void setResPerfil(RespuestaPerfil resPerfil) {
        this.resPerfil = resPerfil;
    }

    public RespuestaPerfil getResPerfil() {
        return resPerfil;
    }

    public DualListModel<Acceso> getDualistaAccesos() {
        return dualistaAccesos;
    }

    public void setDualistaAccesos(DualListModel<Acceso> dualistaAccesos) {
        this.dualistaAccesos = dualistaAccesos;
    }

}
 