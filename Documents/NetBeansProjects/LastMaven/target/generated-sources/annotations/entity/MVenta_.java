package entity;

import entity.CEmpleado;
import entity.CSucursal;
import entity.DVenta;
import entity.HVenta;
import entity.SUsuario;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(MVenta.class)
public class MVenta_ { 

    public static volatile SingularAttribute<MVenta, CSucursal> idSucursal;
    public static volatile CollectionAttribute<MVenta, HVenta> hVentaCollection;
    public static volatile SingularAttribute<MVenta, CEmpleado> idEmpleado;
    public static volatile SingularAttribute<MVenta, BigDecimal> totalVenta;
    public static volatile SingularAttribute<MVenta, SUsuario> idUsuario;
    public static volatile CollectionAttribute<MVenta, DVenta> dVentaCollection;
    public static volatile SingularAttribute<MVenta, Integer> idVenta;
    public static volatile SingularAttribute<MVenta, Date> fechaVenta;

}