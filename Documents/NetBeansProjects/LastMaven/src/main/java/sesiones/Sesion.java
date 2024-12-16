/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sesiones;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Blueweb
 */
public class Sesion {
 
    public static void guardarEnSesion(String nombreVariable, Object valor) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute(nombreVariable, valor);
    }

    
    public static Object obtenerDeSesion(String nombreVariable) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return session != null ? session.getAttribute(nombreVariable) : null;
    }


    public static String obtenerNombreCompleto() {
        Object nombreCompleto = obtenerDeSesion("nombreCompleto");
        return nombreCompleto != null ? nombreCompleto.toString() : null;
    }

 
    public static Object obtenerUsuario() {
        return obtenerDeSesion("usuario");
    }
}
