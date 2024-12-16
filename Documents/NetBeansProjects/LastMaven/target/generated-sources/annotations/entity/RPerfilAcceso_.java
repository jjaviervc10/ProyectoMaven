package entity;

import entity.RPerfilAccesoPK;
import entity.SUsuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(RPerfilAcceso.class)
public class RPerfilAcceso_ { 

    public static volatile SingularAttribute<RPerfilAcceso, RPerfilAccesoPK> rPerfilAccesoPK;
    public static volatile CollectionAttribute<RPerfilAcceso, SUsuario> sUsuarioCollection;
    public static volatile SingularAttribute<RPerfilAcceso, SUsuario> idUsuario;
    public static volatile SingularAttribute<RPerfilAcceso, Date> fechaServidor;

}