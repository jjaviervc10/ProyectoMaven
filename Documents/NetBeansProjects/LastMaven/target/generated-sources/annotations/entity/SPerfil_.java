package entity;

import entity.SAcceso;
import entity.SUsuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(SPerfil.class)
public class SPerfil_ { 

    public static volatile SingularAttribute<SPerfil, Date> fechaBaja;
    public static volatile SingularAttribute<SPerfil, Date> fechaAlta;
    public static volatile CollectionAttribute<SPerfil, SUsuario> sUsuarioCollection;
    public static volatile SingularAttribute<SPerfil, Integer> idPerfil;
    public static volatile SingularAttribute<SPerfil, SUsuario> idUsuario;
    public static volatile SingularAttribute<SPerfil, String> descripcionPerfil;
    public static volatile SingularAttribute<SPerfil, String> nombrePerfil;
    public static volatile CollectionAttribute<SPerfil, SAcceso> accesos;
    public static volatile SingularAttribute<SPerfil, Date> fechaServidor;
    public static volatile SingularAttribute<SPerfil, Boolean> activo;

}