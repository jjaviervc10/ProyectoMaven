package entity;

import entity.MVenta;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(DVenta.class)
public class DVenta_ { 

    public static volatile SingularAttribute<DVenta, BigDecimal> precio;
    public static volatile SingularAttribute<DVenta, Integer> idDetalleVenta;
    public static volatile SingularAttribute<DVenta, Integer> idProducto;
    public static volatile SingularAttribute<DVenta, Integer> cantidad;
    public static volatile SingularAttribute<DVenta, MVenta> idVenta;

}