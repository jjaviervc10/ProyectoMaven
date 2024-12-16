/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HexDigest {

    // Método para hash de la contraseña usando SHA-1
    public static String hexDigest(String message)throws NoSuchAlgorithmException {
        MessageDigest md;
        byte[] buffer, digest;
        String hash = "";

        try {
            buffer = message.getBytes("UTF-8"); // Convierte el mensaje a bytes con UTF-8
            md = MessageDigest.getInstance("SHA1"); // Obtiene una instancia de MessageDigest para SHA-1

            md.update(buffer); // Actualiza el MessageDigest con los bytes del mensaje
            digest = md.digest(); // Calcula el hash del mensaje

            for (byte aux : digest) {
                int b = aux & 0xff; // Convierte el byte a un valor entero positivo
                String s = Integer.toHexString(b); // Convierte el entero a una cadena hexadecimal

                if (s.length() == 1) {
                    hash += "0"; // Asegura que cada byte tenga dos dígitos hexadecimales
                }
                hash += s; // Agrega la cadena hexadecimal a la cadena final
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hash; // Devuelve el hash en formato hexadecimal
    }

    public static void main(String[] args) {

        String password = "jAvier";
        try {

            String hash = HexDigest.hexDigest(password);

            System.out.println("Contraseña encriptada (SHA-1): " + hash);

        } catch (NoSuchAlgorithmException e) {
             System.err.println("Error: El algoritmo SHA-1 no está disponible.");
            e.printStackTrace();
        }
    }

}
