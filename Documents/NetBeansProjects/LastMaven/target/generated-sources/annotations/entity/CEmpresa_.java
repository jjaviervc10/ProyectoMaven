package entity;

import entity.CSucursal;
import entity.SUsuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(CEmpresa.class)
public class CEmpresa_ { 

    public static volatile SingularAttribute<CEmpresa, Date> fechaBaja;
    public static volatile SingularAttribute<CEmpresa, String> claveEmpresa;
    public static volatile SingularAttribute<CEmpresa, Date> fechaAlta;
    public static volatile CollectionAttribute<CEmpresa, CSucursal> cSucursalCollection;
    public static volatile SingularAttribute<CEmpresa, SUsuario> idUsuario;
    public static volatile SingularAttribute<CEmpresa, Integer> idEmpresa;
    public static volatile SingularAttribute<CEmpresa, String> nombreEmpresa;
    public static volatile SingularAttribute<CEmpresa, Date> fechaServidor;
    public static volatile SingularAttribute<CEmpresa, Boolean> activo;

}