package entity;

import entity.CSucursal;
import entity.MVenta;
import entity.SUsuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(CEmpleado.class)
public class CEmpleado_ { 

    public static volatile SingularAttribute<CEmpleado, String> apellidoPaterno;
    public static volatile SingularAttribute<CEmpleado, Date> fechaBaja;
    public static volatile SingularAttribute<CEmpleado, Date> fechaAlta;
    public static volatile SingularAttribute<CEmpleado, BigDecimal> salario;
    public static volatile SingularAttribute<CEmpleado, SUsuario> idUsuario;
    public static volatile SingularAttribute<CEmpleado, String> nombre;
    public static volatile SingularAttribute<CEmpleado, String> apellidoMaterno;
    public static volatile SingularAttribute<CEmpleado, CSucursal> idSucursal;
    public static volatile SingularAttribute<CEmpleado, String> puesto;
    public static volatile CollectionAttribute<CEmpleado, MVenta> mVentaCollection;
    public static volatile SingularAttribute<CEmpleado, Integer> idEmpleado;
    public static volatile SingularAttribute<CEmpleado, Date> fechaServidor;
    public static volatile SingularAttribute<CEmpleado, Boolean> activo;

}