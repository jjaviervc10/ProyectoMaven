package beans;

import controller.CEmpresaJpaController;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.SelectableDataModel;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import utils.LocalEntityManagerFactory;

@Named("sucursalPersistenceBean")
@ViewScoped 
public class sucursalPersistenceBean implements Serializable {

    private CSucursal sucursalEntity;
    private CEmpresa empresaEntity;

    private List<CEmpresa> listaEmpresas;
    private List<CSucursal> listaSucursal;     // Lista de sucursales
    private CSucursal nuevaSucursal;           // Objeto para capturar una nueva sucursal
    private CEmpresaJpaController empresaController;
    private CSucursalJpaController sucursalController;
    private Integer selectedIdEmpresa;  // Almacena la empresa seleccionada
    private Integer selectedPerfil;     // Almacena el perfil seleccionado
    private Integer idSucursal;
    private String nombreEmpresa;
    
     public sucursalPersistenceBean() {
    }

    // Constructor
    @PostConstruct
    public void sucursalBeanPersistence() {
        try {
            // Ahora inicializamos el EntityManagerFactory después de la construcción del bean
            EntityManagerFactory emf = LocalEntityManagerFactory.getEntityManagerFactory();
            sucursalController = new CSucursalJpaController(emf);
            listaSucursal = sucursalController.findCSucursalEntities();
            nuevaSucursal = new CSucursal();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            // Aquí puedes manejar la excepción si ocurre antes de la inicialización
        }
    }
    // Métodos CRUD
    public String guardarNuevaSucursal() {
        try {

            if (selectedIdEmpresa == null || selectedIdEmpresa <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar una empresa."));
                return null;
            }

            CEmpresa empresa = new CEmpresa();  // Asegúrate de que este objeto se asocie correctamente
            empresa.setIdEmpresa(selectedIdEmpresa);
            nuevaSucursal.setIdEmpresa(empresa);// Asocia la empresa con la nueva sucursal

            for (CEmpresa emp : listaEmpresas) {
                if (emp.getIdEmpresa().equals(selectedIdEmpresa)) {
                    nombreEmpresa = emp.getNombreEmpresa();  // Asigna el nombre de la empresa
                    break;
                }
            }

            // Guarda la nueva sucursal
            sucursalController.create(nuevaSucursal);
            listaSucursal = sucursalController.findCSucursalEntities();  // Actualiza la lista
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Sucursal " + nuevaSucursal.getNombreSucursal() + " añadida exitosamente."));

           // return "listCSucursales?faces-redirect=true"; // Redirige a la lista de sucursales
               return null;
        
        } catch (PreexistingEntityException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La sucursal ya existe."));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al guardar la sucursal."));
            return "error";
        }
    }

    // Eliminar sucursal
    public String eliminarSucursal(Integer id) {
        try {
            sucursalController.destroy(id);
            listaSucursal = sucursalController.findCSucursalEntities();  // Actualiza la lista
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Sucursal eliminada correctamente."));
            return null;  // No se redirige, ya que la tabla está actualizada
        } catch (IllegalOrphanException | NonexistentEntityException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar la sucursal."));
            return "error";
        }
    }

    // Método para obtener la lista de empresas
    public List<CEmpresa> obtenerListaEmpresas() {

        listaEmpresas = empresaController.findCEmpresaEntities();
        return listaEmpresas;
    }

    // Método para seleccionar perfil
    public void seleccionarPerfil() {
        if (selectedPerfil == null || selectedPerfil <= 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Debe seleccionar un perfil válido."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil seleccionado", "Perfil con ID: " + selectedPerfil));
        }
    }

// Métodos de edición de sucursal
    public void onRowEdit(RowEditEvent event) {
      

        CSucursal sucursal = (CSucursal) event.getObject();

        // Verificar que la empresa esté correctamente asignada antes de proceder
        if (sucursal.getIdEmpresa() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La sucursal debe tener una empresa asociada."));
            return;
        }

        try {
            // Guardar la sucursal modificada
            sucursalController.edit(sucursal);
            listaSucursal = sucursalController.findCSucursalEntities();  // Actualiza la lista
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Sucursal editada exitosamente."));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error al editar la sucursal."));
        }
    }

    // Método para cancelar la edición de una sucursal
    public void onRowCancel(RowEditEvent event) {
        CSucursal sucursal = (CSucursal) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "La edición de la sucursal " + sucursal.getNombreSucursal() + " ha sido cancelada."));
    }

    public void reset() {
        PrimeFaces.current().resetInputs("mainForm:panel");
    }

    // Métodos de getter y setter
    public List<CSucursal> getListaSucursal() {
        return listaSucursal;
    }

    public List<CEmpresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public Integer getSelectedIdEmpresa() {
        return selectedIdEmpresa;
    }

    public void setSelectedIdEmpresa(Integer selectedIdEmpresa) {
        this.selectedIdEmpresa = selectedIdEmpresa;
    }

    public CSucursal getNuevaSucursal() {
        return nuevaSucursal;
    }

    public void setNuevaSucursal(CSucursal nuevaSucursal) {
        this.nuevaSucursal = nuevaSucursal;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }
    


}
