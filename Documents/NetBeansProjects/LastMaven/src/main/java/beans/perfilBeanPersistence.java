package beans;

import controller.RPerfilAccesoJpaController;
import controller.SAccesoJpaController;
import controller.SPerfilJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.RPerfilAcceso;
import entity.RPerfilAccesoPK;
import entity.SAcceso;
import entity.SPerfil;
import entity.SUsuario;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManagerFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import sesiones.Sesion;
import utils.LocalEntityManagerFactory;

/**
 *
 * @author Blueweb
 */
@Named("perfilBeanPersistence")
@ViewScoped
public class perfilBeanPersistence implements Serializable {

    private static final long serialVersionUID = 1L;

    private SPerfilJpaController perfilController;
    private RPerfilAccesoJpaController rPerfilAccesoJpaController;
    private SAccesoJpaController accesoListaController;
    private RPerfilAccesoJpaController perfilAccesoController;

    private List<SPerfil> listaPerfiles;
    private List<SAcceso> accesosAsignados;
    private List<SAcceso> listaAccesos;
    private List<SAcceso> accesosDisponibles;
    private DualListModel<SAcceso> dualListModel;  // Para PickList

    private int selectedPerfil;
    private String nombrePerfil;
    private SUsuario idUsuario;
    private SPerfil perfil = new SPerfil();
    private SPerfil nuevoPerfil;

    public perfilBeanPersistence() {
    }

    @PostConstruct
    public void perfilBeanPersistence() {

        try {
            EntityManagerFactory emf = LocalEntityManagerFactory.getEntityManagerFactory();
            perfilController = new SPerfilJpaController(emf);
            rPerfilAccesoJpaController = new RPerfilAccesoJpaController(emf);
            accesoListaController = new SAccesoJpaController(emf);
            perfilAccesoController = new RPerfilAccesoJpaController(emf);
            listaAccesos = accesoListaController.findSAccesoEntities();
            listaPerfiles = perfilController.findSPerfilEntities();
            nuevoPerfil = new SPerfil();
            idUsuario = new SUsuario();

            dualListModel = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
        } catch (IllegalStateException e) {
            e.printStackTrace();

        }
    }

    //Metodos CRUD 
    public void guardarNuevoPerfil() {

        try {

            nuevoPerfil.setIdPerfil(selectedPerfil);

            Integer idUsuarioSesion = (Integer) Sesion.obtenerDeSesion("idUsuario");

            if (idUsuarioSesion == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el usuario en la sesión (vuelva a iniciar secion.)"));
                return;
            }

            nuevoPerfil.setIdUsuario(new SUsuario(idUsuarioSesion));

            perfilController.create(nuevoPerfil);
            listaPerfiles = perfilController.findSPerfilEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Perfil " + nuevoPerfil.getNombrePerfil() + " añadido exitosamente."));

        } catch (PreexistingEntityException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La empresa ya existe."));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar la empresa."));

        }

    }

    //Eliminar Perfil
    public void eliminarPerfil(Integer Id) {

        try {
            perfilController.destroy(Id);
            listaPerfiles = perfilController.findSPerfilEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Perfil eliminado correctamente."));

        } catch (NonexistentEntityException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el perfil."));

        }
    }

    //Metodos edicion Perfiles
    public void onRowEdit(RowEditEvent event) {
        SPerfil perfil = (SPerfil) event.getObject();

        try {
            perfilController.edit(perfil);
            listaPerfiles = perfilController.findSPerfilEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Perfil editado exitosamente."));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al editar el Perfil."));
        }

    }

    // Método para cancelar la edición de una sucursal
    public void onRowCancel(RowEditEvent event) {
        SPerfil perfil = (SPerfil) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "La edición del Perfil " + perfil.getNombrePerfil() + " ha sido cancelada."));
    }

    // Métodos para manejar accesos
    public void obtenerAccesos(int idPerfil) {
        System.out.println("Obteniendo accesos para el perfil con ID: " + idPerfil);

        accesosDisponibles = rPerfilAccesoJpaController.findAccesosDisponibles(idPerfil);
      
        System.out.println("Accesos source carganodose:" + accesosDisponibles);

        accesosAsignados = rPerfilAccesoJpaController.findAccesosAsignados(idPerfil);

        System.out.println("Accesos asiganos carganodose:" + accesosAsignados);

        dualListModel = new DualListModel<>(accesosDisponibles, accesosAsignados);
        System.out.println("Accesos obtenidos correctamente. Accesos disponibles: " + accesosDisponibles.size() + ", Accesos asignados: " + accesosAsignados.size());

    }

    public void seleccionarPerfil(SPerfil perfil) {

        selectedPerfil = perfil.getIdPerfil();
        nombrePerfil = perfil.getNombrePerfil();
        System.out.println("ID de selectedPerfil:" + selectedPerfil);
        obtenerAccesos(perfil.getIdPerfil());
        PrimeFaces.current().executeScript("PF('dialogoPickList').show();");

    }

    public void guardarRPCambios() {

        Integer idUsuarioSesion = (Integer) Sesion.obtenerDeSesion("idUsuario");


        for (SAcceso acceso : dualListModel.getTarget()) {
            RPerfilAccesoPK pk = new RPerfilAccesoPK();
            pk.setIdPerfil(selectedPerfil);
            pk.setIdAcceso(acceso.getIdAcceso());
 
            try {
                // Verificar si la relación ya existe antes de intentar insertarla
                RPerfilAcceso rPerfilAccesoExistente = perfilAccesoController.findRPerfilAcceso(pk);
                if (rPerfilAccesoExistente == null) {
                    // Si no existe, insertar la nueva relación
                    perfilAccesoController.create(selectedPerfil, acceso.getIdAcceso(), idUsuarioSesion);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación del acceso " + acceso.getNombreAcceso() + " al Perfil " + nombrePerfil));
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        for (SAcceso acceso :  dualListModel.getSource()) {
            RPerfilAccesoPK pk = new RPerfilAccesoPK();
            pk.setIdPerfil(selectedPerfil);
            pk.setIdAcceso(acceso.getIdAcceso());

            try {
                RPerfilAcceso rPerfilAccesoExistente = perfilAccesoController.findRPerfilAcceso(pk);

                // Si existe, proceder a eliminarla
                if (rPerfilAccesoExistente != null) {
                    perfilAccesoController.destroy(pk);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Desasignación del acceso " + acceso.getNombreAcceso() + " del Perfil " + nombrePerfil));
                }

            } catch (Exception e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Hubo un error al intentar desasignar el acceso " + acceso.getNombreAcceso()));
            }
        }
    }

   
    public void reset() {
        PrimeFaces.current().resetInputs("perfilForm:panel");
    }

    public int getSelectedPerfil() {
        return selectedPerfil;
    }

    public SPerfil getNuevoPerfil() {
        return nuevoPerfil;
    }

    public void setNuevoPerfil(SPerfil nuevoPerfil) {
        this.nuevoPerfil = nuevoPerfil;
    }

    public List<SPerfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public List<SAcceso> getAccesosDisponibles() {
        return accesosDisponibles;
    }

    public List<SAcceso> getAccesosAsignados() {
        return accesosAsignados;
    }

    public DualListModel<SAcceso> getDualListModel() {
        return dualListModel;
    }

    public void setDualListModel(DualListModel<SAcceso> dualListModel) {
        this.dualListModel = dualListModel;
    }

}
