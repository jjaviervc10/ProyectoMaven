package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;

import objetos.Empresas;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;
import sesiones.Sesion;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.CatalogEmpresa;
import respuestas.RespuestaEmpresa;
import org.primefaces.event.RowEditEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "empresaBean")
@ViewScoped

public class EmpresaBean implements Serializable {

    private List<Empresas> listaEmpresas;
    private CatalogEmpresa catalogEmpresa;
    private RespuestaEmpresa resEmpresa;
    private Empresas EmpresaAEliminar;
    private Empresas nuevaEmpresa;
    private boolean fechaServidorManipulada = false;

    public EmpresaBean() {
        catalogEmpresa = new CatalogEmpresa();
        nuevaEmpresa = new Empresas();
        EmpresaAEliminar = new Empresas();

        loadServicios();
    }

    public void loadServicios() {

        setResEmpresa(catalogEmpresa.getListaEmpresa());
        if (getResEmpresa().getId() == 0) {
            listaEmpresas = getResEmpresa().getListaEmpresa();
        }

    }

    public void onRowEdit(RowEditEvent event) {
        Empresas empresa = (Empresas) event.getObject();

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");
        Logger.getLogger(CatalogEmpresa.class.getName()).log(Level.INFO, "Fecha Servidor a actualizar: " + empresa.getFechaServidor());

        RespuestaEmpresa respuesta = catalogEmpresa.updateEmpresa(empresa, idUsuario, empresa.getIdEmpresa());

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuesta.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición exitosa", "La empresa " + empresa.getNombreEmpresa() + " ha sido actualizada."));
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));
        }
    }

    public void onRowCancel(RowEditEvent event) {
        Empresas empresa = (Empresas) event.getObject();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Edición cancelada", "Los cambios en la empresa " + empresa.getNombreEmpresa() + " han sido revertidos."));
    }

    public void reset() {
        PrimeFaces.current().resetInputs("mainform:panel");
    }

    public void guardarNuevaEmpresa() {
        boolean hasError = false;
        if (!fechaServidorManipulada) {
            nuevaEmpresa.setFechaServidor(null); // Asigna null si no fue manipulada
        } else {
            if (nuevaEmpresa.getFechaServidor() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La Fecha Servidor es obligatoria."));
                hasError = true;
            }
        }

        System.out.println("Clave Empresa: " + nuevaEmpresa.getClaveEmpresa());
        System.out.println("Nombre Empresa: " + nuevaEmpresa.getNombreEmpresa());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Fecha Servidor: " + (nuevaEmpresa.getFechaServidor() != null ? sdf.format(nuevaEmpresa.getFechaServidor()) : "No definida"));

        if (nuevaEmpresa.getClaveEmpresa() == null || nuevaEmpresa.getClaveEmpresa().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "La Clave Empresa no puede estar vacía."));
            hasError = true;
        }

        if (nuevaEmpresa.getNombreEmpresa() == null || nuevaEmpresa.getNombreEmpresa().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El Nombre Empresa no puede estar vacío."));
            hasError = true;
        }

        if (hasError) {
            return; // Detener la ejecución solo si hubo al menos un error
        }

        int idUsuario = (Integer) Sesion.obtenerDeSesion("idUsuario");

        RespuestaEmpresa respuesta = catalogEmpresa.insertEmpresa(nuevaEmpresa, idUsuario);
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (respuesta.getId() == 0) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "La empresa " + nuevaEmpresa.getNombreEmpresa() + " ha sido añadida."));

            // String statusEmpresa = nuevaEmpresa.getStatusEmpresa() ? "Activa" : "Inactiva";
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Detalles de la Empresa",
                    "Clave: " + nuevaEmpresa.getClaveEmpresa()
                    + ", Fecha Servidor: " + (nuevaEmpresa.getFechaServidor() != null ? sdf.format(nuevaEmpresa.getFechaServidor()) : "No definida")));

            listaEmpresas.add(nuevaEmpresa);
            nuevaEmpresa = new Empresas();
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", respuesta.getMensaje()));
        }

    }

    public void eliminarEmpresa(int id) {
        FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("ID de la empresa a eliminar: " + id);

        if (id == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "ID de empresa no válido."));
            return;
        }

        EmpresaAEliminar.setIdEmpresa(id);

        try {

            RespuestaEmpresa respuesta = catalogEmpresa.deleteEmpresa(EmpresaAEliminar);

            if (respuesta.getId() == 0) {

                for (int i = 0; i < listaEmpresas.size(); i++) {
                    if (listaEmpresas.get(i).getIdEmpresa() == id) {
                        listaEmpresas.remove(i);
                        break;
                    }
                }
                FacesMessage successMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "La empresa con el ID: " + id + " ha sido eliminada correctamente.");
                context.addMessage("msgs", successMessage);

            } else {
                FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
                context.addMessage("msgs", errorMessage);

            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la empresa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Empresas getNuevaEmpresa() {
        return nuevaEmpresa;
    }

    public void setNuevaEmpresa(Empresas nuevaEmpresa) {
        this.nuevaEmpresa = nuevaEmpresa;
    }

    public List<Empresas> getListaEmpresa() {
        return listaEmpresas;
    }

    public RespuestaEmpresa getResEmpresa() {
        return resEmpresa;
    }

    public void setResEmpresa(RespuestaEmpresa resEmpresa) {
        this.resEmpresa = resEmpresa;
    }

    public void fechaServidorCambiada() {
        this.fechaServidorManipulada = true; // Marcar como manipulada
    }

}
