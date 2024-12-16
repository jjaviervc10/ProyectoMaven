package beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.CatalogPerfil;
import objetos.Acceso;
import respuestas.RespuestaPerfil;

@FacesConverter("accesoConverter") 
public class AccesoConverter implements Converter {

    // Método para obtener el bean CatalogPerfil del contexto
    private CatalogPerfil getCatalogPerfil() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{catalogPerfil}", CatalogPerfil.class);
    }

    // Obtiene el perfilBean desde el contexto (como se hace en tu código original)
    private PerfilBean getPerfilBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{perfilBean}", PerfilBean.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        // Obtener el perfilBean y asegurarse de que hay un perfil seleccionado
        PerfilBean perfilBean = getPerfilBean();
        if (perfilBean == null || perfilBean.getSelectedPerfil() == 0) {
            throw new ConverterException(new FacesMessage("No se ha seleccionado un perfil."));
        }

        // Obtener el CatalogPerfil (catalogo de perfiles) y cargar los accesos para el perfil seleccionado
        CatalogPerfil catalogPerfil = getCatalogPerfil();
        if (catalogPerfil == null) {
            throw new ConverterException(new FacesMessage("No se pudo obtener el catálogo de perfiles."));
        }

        // Llamar al servicio que devuelve los accesos para el perfil seleccionado
        RespuestaPerfil respuesta = catalogPerfil.getListaAccesos(perfilBean.getSelectedPerfil());
        if (respuesta == null || respuesta.getDualistaAccesosSource() == null) {
            throw new ConverterException(new FacesMessage("No se encontraron los accesos para el perfil seleccionado."));
        }

        // Buscar el acceso correspondiente en la lista de accesos disponibles
        for (Acceso acceso : respuesta.getDualistaAccesosSource()) {
            if (acceso.getNombreAcceso().equals(value)) {
                return acceso;
            }
        }

        return null; 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Acceso) {
            return ((Acceso) value).getNombreAcceso(); // Devuelve el nombre del acceso como cadena
        }
        return ""; 
    }
}