package model;

import java.io.Serializable;

public class Login implements Serializable {

    private String usuario;
    private String pass;

    // Constructor vacío
    public Login() {}

    // Constructor con parámetros
    public Login(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    //</editor-fold>
}
