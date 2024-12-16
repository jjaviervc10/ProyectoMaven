package beans;

/**
 *
 * @author Blueweb
 */
import controller.CEmpresaJpaController;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import controller.CSucursalJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import entity.CEmpresa;
import entity.CSucursal;
import java.io.Serializable;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import utils.LocalEntityManagerFactory;

@Named("empresaBeanPersistence")
@ViewScoped
public class empresaPersistenceBean implements Serializable {

    private CEmpresa empresaEntity;

    private List<CEmpresa> listaEmpresas;

    private CEmpresa nuevaEmpresa;           // Objeto para capturar una nueva sucursal
    private CEmpresa EmpresaAEliminar;
    private CEmpresaJpaController empresaController;
    private Integer idEmpresa;  // Almacena la empresa seleccionada

    public empresaPersistenceBean() {
    }

    // Constructor
    @PostConstruct
    public void empresaBeanPersistence() {
        try {
            // Ahora inicializamos el EntityManagerFactory después de la construcción del bean
            EntityManagerFactory emf = LocalEntityManagerFactory.getEntityManagerFactory();
            empresaController = new CEmpresaJpaController(emf);
            listaEmpresas = empresaController.findCEmpresaEntities();
            nuevaEmpresa = new CEmpresa();
        } catch (IllegalStateException e) {
            e.printStackTrace();

        }
    }

   
    //Metodos CRUD
    public void guardarNuevaEmpresa() {

        try {
            empresaController.create(nuevaEmpresa);
            listaEmpresas = empresaController.findCEmpresaEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empresa " + nuevaEmpresa.getNombreEmpresa() + " añadida exitosamente."));

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

    //Eliminar empresa
    public void eliminarEmpresa(Integer Id) {
        try {
            empresaController.destroy(Id);
            listaEmpresas = empresaController.findCEmpresaEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empresaeliminada correctamente."));

        } catch (IllegalOrphanException | NonexistentEntityException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la empresa."));

        }
    }

    //Metodos edicion empresa
    public void onRowEdit(RowEditEvent event) {

        CEmpresa empresa = (CEmpresa) event.getObject();

        try {
            empresaController.edit(empresa);
            listaEmpresas = empresaController.findCEmpresaEntities();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empresa editada exitosamente."));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al editar la sempresa."));
        }

    }

    // Método para cancelar la edición de una sucursal
    public void onRowCancel(RowEditEvent event) {
        CEmpresa empresa = (CEmpresa) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "La edición de la empresa " + empresa.getNombreEmpresa() + " ha sido cancelada."));
    }

    public void reset() {
        PrimeFaces.current().resetInputs("mainform:panel");
    }

    public List<CEmpresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public CEmpresa getNuevaEmpresa() {
        return nuevaEmpresa;
    }

    public void setNuevaEmpresa(CEmpresa nuevaEmpresa) {
        this.nuevaEmpresa = nuevaEmpresa;
    }

    public void setIdSucursal(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
