
package data;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sesiones.Sesion;

public class FiltroValidacion  implements Filter{
    
     // Método init sin @Override
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    // Método doFilter sin @Override
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
           // Desactiva la caché en el navegador
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        // Verifica si la sesión existe y si tiene el atributo 'idUsuario'
        if (session == null || session.getAttribute("idUsuario") == null) {
           
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        } else {
           
            chain.doFilter(request, response);
        }
    }

    // Método destroy sin @Override
    @Override
    public void destroy() {
        
    } 
}
