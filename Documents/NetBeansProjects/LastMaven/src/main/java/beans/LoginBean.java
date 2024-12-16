package beans;

/**
 *
 * @author Blueweb
 */
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import respuestas.RespuestaLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.NamingException;
import java.sql.SQLException;
import data.PoolDB;
import sesiones.Sesion;
import javax.faces.view.ViewScoped;
import model.Login;
import java.security.NoSuchAlgorithmException;
import beans.HexDigest;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private Login login = new Login(); // Instanciamos el modelo Login
    private RespuestaLogin resLogin;

    public String login() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Manejo de NoSuchAlgorithmException
            String encryptedPassword;
            try {
                encryptedPassword = HexDigest.hexDigest(login.getPass());
            } catch (NoSuchAlgorithmException e) {
                FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage("Error al encriptar la contraseña"));
                e.printStackTrace();
                return null;
            }

            connection = PoolDB.getConnection("MyDB");

            // Asegúrate de que el nombre de la tabla y las columnas sean correctos
            String sql = "SELECT * FROM sUsuario WHERE usuario = ? AND pass = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, login.getUsuario());
            ps.setString(2, encryptedPassword);

            boolean credencialesCorrectas = false;

            rs = ps.executeQuery();

            if (rs.next()) {
                credencialesCorrectas = true;

                String nombreCompleto = rs.getString("nombreCompleto");
                int idUsuario = rs.getInt("idUsuario");

                Sesion.guardarEnSesion("nombreCompleto", nombreCompleto);
                Sesion.guardarEnSesion("idUsuario", idUsuario);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                try {
                    facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/inicio.xhtml?faces-redirect=true");
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio de sesión exitoso", "Bienvenido, " + nombreCompleto));
                } catch (IOException e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la redirección", ""));
                    e.printStackTrace();
                    return null;
                }
                return null;
            } else {
                // Credenciales incorrectas
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales incorrectas", "Verificar credenciales, usuario o contraseña incorrectos."));
                return null;
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de base de datos", ""));
            return null;

        } finally {
            try { 
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() {
        
      invalidateSession();
        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión cerrada", "Has cerrado sesión exitosamente."));

        try {

            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/login.xhtml");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resetSessionTimeout() {

        FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sesión extendida."));
    }

    public static void invalidateSession() {
      //  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            // Obtén la sesión actual
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            
            // Si la sesión no es nula, invalidala
            if (session != null) {
                session.invalidate();
            }
        } 
    
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }
}
