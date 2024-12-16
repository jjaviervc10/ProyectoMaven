package entity;

import entity.MVenta;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(HVenta.class)
public class HVenta_ { 

    public static volatile SingularAttribute<HVenta, Integer> cantidadProductos;
    public static volatile SingularAttribute<HVenta, BigDecimal> totalVenta;
    public static volatile SingularAttribute<HVenta, String> nombreSucursal;
    public static volatile SingularAttribute<HVenta, String> nombreCompletoEmpleado;
    public static volatile SingularAttribute<HVenta, String> nombreUsuario;
    public static volatile SingularAttribute<HVenta, Integer> idHistVenta;
    public static volatile SingularAttribute<HVenta, String> nombreEmpresa;
    public static volatile SingularAttribute<HVenta, MVenta> idVenta;

}