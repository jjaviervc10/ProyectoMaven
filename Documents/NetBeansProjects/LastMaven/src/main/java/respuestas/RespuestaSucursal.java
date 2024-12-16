/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respuestas;

import java.util.List; 
import objetos.Sucursales;

/**
 *
 * @author Blueweb
 */
public class RespuestaSucursal extends Respuesta{
     
     private List<Sucursales> listaSucursal;
  

   public RespuestaSucursal (int id, String mensaje, List<Sucursales> listaSucursal){
      super(id,mensaje);
     this.listaSucursal = listaSucursal;
    }

 

    public RespuestaSucursal(int id, String mensaje) {
        super(id, mensaje);
    }
/**
     * @return the listaSucursal
     */
   public List<Sucursales> getListaSucursal(){
   return listaSucursal;
  
   }
   
     /**
     * @param listaSucursal the listaSucursal to set
     */
   public void setListaSucursal(List<Sucursales> listaSucursal){
   this.listaSucursal = listaSucursal;
   }
}
