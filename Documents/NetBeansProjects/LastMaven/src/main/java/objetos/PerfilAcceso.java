package objetos;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
@ManagedBean(name = "perfilAcceso")
@ViewScoped

public class PerfilAcceso implements Serializable{
    private int idPerfil;
    private int idAcceso;
    private Date fechaServidor;
    private int idUsuario;
    
    
    public int getIdPerfil(){
     return idPerfil;
    }
    
    public void setIdPerfil(int idPerfil){
        this.idPerfil =  idPerfil;
    }
    
    
    public int getIdAcceso(){
    return idAcceso;
    }
    
    public void setIdAcceso(int idAcceso){
     this.idAcceso =  idAcceso;
    }
    
    public Date getFechaServidor(){
    return fechaServidor;
    }
    
    public void setFechaServidor(Date fechaServidor){
    this.fechaServidor = fechaServidor;
    }
    
    public int getIdUsuario(){
    return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
     this.idUsuario =  idUsuario;
    }
    
}
