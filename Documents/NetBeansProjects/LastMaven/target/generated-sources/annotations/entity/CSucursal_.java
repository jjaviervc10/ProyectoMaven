package entity;

import entity.CEmpleado;
import entity.CEmpresa;
import entity.MVenta;
import entity.SUsuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(CSucursal.class)
public class CSucursal_ { 

    public static volatile SingularAttribute<CSucursal, Integer> idSucursal;
    public static volatile SingularAttribute<CSucursal, Date> fechaBaja;
    public static volatile CollectionAttribute<CSucursal, MVenta> mVentaCollection;
    public static volatile SingularAttribute<CSucursal, String> estado;
    public static volatile SingularAttribute<CSucursal, Date> fechaAlta;
    public static volatile SingularAttribute<CSucursal, String> ciudad;
    public static volatile SingularAttribute<CSucursal, SUsuario> idUsuario;
    public static volatile SingularAttribute<CSucursal, String> nombreSucursal;
    public static volatile CollectionAttribute<CSucursal, CEmpleado> cEmpleadoCollection;
    public static volatile SingularAttribute<CSucursal, CEmpresa> idEmpresa;
    public static volatile SingularAttribute<CSucursal, Date> fechaServidor;
    public static volatile SingularAttribute<CSucursal, Boolean> activo;

}