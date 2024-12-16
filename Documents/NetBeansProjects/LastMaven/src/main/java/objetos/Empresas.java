package objetos;
import java.util.Date;
/**
 *
 * @author Blueweb
 */
public class Empresas {

    private int id;
    private String claveEmpresa;
    private String nombreEmpresa;
    private Boolean statusEmpresa;
    private Date fechaAlta;
    private Date fechaServidor;
    
  public Empresas() {
        this.statusEmpresa = false; // Inicializa statusEmpresa a false
    }
    //<editor-fold default="collapsed" desc="Getters y Setters">
    //Getters y Setters
    public int getIdEmpresa() {
        return id;
    }

    public void setIdEmpresa(int id) {
        this.id = id;
    }

    public String getClaveEmpresa() {
        return claveEmpresa;
    }

    public void setClaveEmpresa(String claveEmpresa) {
        this.claveEmpresa = claveEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public boolean getStatusEmpresa() {
        return statusEmpresa;
    }

    public void setStatusEmpresa(boolean statusEmpresa) {
        this.statusEmpresa = statusEmpresa;
    }

    public Date getFechaAltaEmpresa() {
        return fechaAlta;
    }

    public void setFechaAltaEmpresa(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Date fechaServidor) {
        this.fechaServidor = fechaServidor;
    }

}
