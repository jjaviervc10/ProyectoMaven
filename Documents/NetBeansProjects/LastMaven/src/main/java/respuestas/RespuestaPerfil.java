package respuestas;

import java.util.ArrayList;
import java.util.List;
import objetos.Acceso;
import objetos.Perfil;
import org.primefaces.model.DualListModel;

public class RespuestaPerfil extends Respuesta{

    private Acceso acceso;
    private List<Perfil> listaPerfiles;
    private List<Acceso> listaAccesos;
    //Añadiremos una lista de target/destino
    private List<Acceso> dualistaAccesosSource;
    private List<Acceso> dualistaAccesosTarget;

    public RespuestaPerfil(int idAcceso, String mensaje,Acceso acceso,List<Perfil> listaPerfiles, List<Acceso> listaAccesos, List<Acceso> dualistaAccesosSource, List<Acceso> dualistaAccesosTarget){
        super(idAcceso, mensaje);
        this.acceso =  acceso;
        //this.listaAccesos = listaAccesos;
         this.listaAccesos = listaAccesos != null ? listaAccesos : new ArrayList<>();  // Si es nula, inicializamos una lista vacía
        this.listaPerfiles = listaPerfiles;
        this.dualistaAccesosSource = dualistaAccesosSource;
        this.dualistaAccesosTarget = dualistaAccesosTarget;
    }

    public RespuestaPerfil(int idAcceso, String mensaje) {
        super(idAcceso, mensaje);
        this.acceso = new Acceso();
         this.listaAccesos = new ArrayList<>();
    }
    
    public Acceso getAcceso(){
    return acceso;
    }

    public List<Perfil> getListaPerfil(){
    return listaPerfiles;
    }
    
    public void setListaPerfil(List<Perfil> listaPerfiles){
    this.listaPerfiles =  listaPerfiles;
    }
    
    
    public List<Acceso> getListaAccesos() {
        return listaAccesos;
    }

    public void setListaAccesos(List<Acceso> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }

    public List<Acceso> getDualistaAccesosSource() {
        return dualistaAccesosSource;
    }

    public void setDualistaAccesosSource(List<Acceso> dualistaAccesosSource) {
        this.dualistaAccesosSource = dualistaAccesosSource;
    }

    public List<Acceso> getDualistaAccesosTarget() {
        return dualistaAccesosTarget;
    }

    public void setDualistaAccesosTarget(List<Acceso> dualistaAccesosTarget) {
        this.dualistaAccesosTarget = dualistaAccesosTarget;
    }

}
