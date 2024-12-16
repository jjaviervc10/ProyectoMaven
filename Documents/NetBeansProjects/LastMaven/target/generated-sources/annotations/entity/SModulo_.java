package entity;

import entity.SAcceso;
import entity.SAplicacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(SModulo.class)
public class SModulo_ { 

    public static volatile SingularAttribute<SModulo, String> icono;
    public static volatile SingularAttribute<SModulo, Date> fechaBaja;
    public static volatile SingularAttribute<SModulo, SAplicacion> idAplicacion;
    public static volatile SingularAttribute<SModulo, Date> fechaAlta;
    public static volatile CollectionAttribute<SModulo, SAcceso> sAccesoCollection;
    public static volatile SingularAttribute<SModulo, Integer> idModulo;
    public static volatile SingularAttribute<SModulo, Integer> orden;
    public static volatile SingularAttribute<SModulo, String> nombreModulo;
    public static volatile SingularAttribute<SModulo, String> url;
    public static volatile SingularAttribute<SModulo, Boolean> activo;

}