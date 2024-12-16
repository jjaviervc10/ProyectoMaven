package entity;

import entity.SModulo;
import entity.SPerfil;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(SAcceso.class)
public class SAcceso_ { 

    public static volatile ListAttribute<SAcceso, SPerfil> sPerfilCollection;
    public static volatile SingularAttribute<SAcceso, Date> fechaBaja;
    public static volatile SingularAttribute<SAcceso, Date> fechaAlta;
    public static volatile SingularAttribute<SAcceso, String> nombreAcceso;
    public static volatile SingularAttribute<SAcceso, SModulo> idModulo;
    public static volatile SingularAttribute<SAcceso, Integer> orden;
    public static volatile SingularAttribute<SAcceso, Integer> idAcceso;
    public static volatile SingularAttribute<SAcceso, Boolean> activo;

}