package entity;

import entity.SModulo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(SAplicacion.class)
public class SAplicacion_ { 

    public static volatile SingularAttribute<SAplicacion, String> icono;
    public static volatile SingularAttribute<SAplicacion, Integer> idAplicacion;
    public static volatile SingularAttribute<SAplicacion, Date> fechaBaja;
    public static volatile SingularAttribute<SAplicacion, Date> fechaAlta;
    public static volatile SingularAttribute<SAplicacion, Integer> orden;
    public static volatile SingularAttribute<SAplicacion, String> nombreAplicacion;
    public static volatile SingularAttribute<SAplicacion, String> url;
    public static volatile CollectionAttribute<SAplicacion, SModulo> sModuloCollection;
    public static volatile SingularAttribute<SAplicacion, Boolean> activo;

}