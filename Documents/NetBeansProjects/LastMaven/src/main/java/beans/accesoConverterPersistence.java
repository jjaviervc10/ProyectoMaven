package beans;

import controller.RPerfilAccesoJpaController;
import controller.SAccesoJpaController;
import entity.SAcceso;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Blueweb
 */
@FacesConverter(value="accesoConverterPersistence")
public class accesoConverterPersistence implements Converter  {

    private SAccesoJpaController accesoJpaController;


    public accesoConverterPersistence() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_LastMaven_war_1.0-SNAPSHOTPU");
        this.accesoJpaController = new SAccesoJpaController(emf);
    }

   @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            Integer idAcceso = Integer.parseInt(value);
            return accesoJpaController.findSAcceso(idAcceso); 
        } catch (NumberFormatException e) {
           
            e.printStackTrace();

        return null;
    }

    }     
 // Convertir el objeto SAcceso en su ID como un String
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return "";
        }

        if (object instanceof SAcceso) {

            SAcceso acceso = (SAcceso) object;
            return String.valueOf(acceso.getIdAcceso());
        } else {
         
            return "";
        }
    }

  
}
