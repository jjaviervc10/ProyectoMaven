/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package respuestas;

/**
 *
 * @author Blueweb
 */
public class Respuesta {

    private int id;
    private String mensaje;

    public Respuesta(int id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    // Métodos getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
