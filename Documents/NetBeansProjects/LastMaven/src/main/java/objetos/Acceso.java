
package objetos;


import java.util.Date;


public class Acceso {
    private int idModulo;
    
    private int idAcceso;
    private String nombreAcceso;
    private int orden;
    private boolean activo;
    private Date fechaAlta;
    private Date fechaBaja;
    
   public int getidModulo(){
   return idModulo;
   }
   public void setidModulo(int idModulo){
   this.idModulo = idModulo;
   }
   
   public int getidAcceso(){
   return idAcceso;
   }
   
   public void setidAcceso(int idAcceso){
   this.idAcceso = idAcceso;
   }
   
   public String getNombreAcceso(){
   return nombreAcceso;
   }
   
   public void setNombreAcceso(String nombreAcceso){
       this.nombreAcceso = nombreAcceso;
   
   }
   
   public int getOrden(){
   return orden; 
   }
   
   public void setOrden(int orden){
   this.orden =  orden;
   }
   
   public boolean getActivo(){
   return activo;
   }
   
   public void setActivo(boolean activo){
   this.activo =  activo;
   }
   
   public Date getFechaAlta(){
    return fechaAlta;
   }
   
   public void setFechaAlta(Date fechaAlta){
   this.fechaAlta =  fechaAlta;
   }
   
   public Date getFechaBaja(){
   return fechaBaja;
   }
   
   public void setFechaBaja (Date fechaBaja){
   this.fechaBaja =  fechaBaja;
   }
   
    @Override
    public String toString() {
        return nombreAcceso;  // Aquí puedes cambiarlo por cualquier otro atributo que desees mostrar
    }
   
}
