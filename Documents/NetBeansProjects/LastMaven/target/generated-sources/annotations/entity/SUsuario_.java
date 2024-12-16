package entity;

import entity.CEmpleado;
import entity.CEmpresa;
import entity.CSucursal;
import entity.MVenta;
import entity.RPerfilAcceso;
import entity.SPerfil;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-12-05T10:01:01")
@StaticMetamodel(SUsuario.class)
public class SUsuario_ { 

    public static volatile SingularAttribute<SUsuario, String> pass;
    public static volatile SingularAttribute<SUsuario, Integer> idUsuario;
    public static volatile CollectionAttribute<SUsuario, CEmpleado> cEmpleadoCollection;
    public static volatile SingularAttribute<SUsuario, String> nombreCompleto;
    public static volatile CollectionAttribute<SUsuario, SPerfil> sPerfilCollection;
    public static volatile CollectionAttribute<SUsuario, MVenta> mVentaCollection;
    public static volatile CollectionAttribute<SUsuario, CSucursal> cSucursalCollection;
    public static volatile SingularAttribute<SUsuario, SPerfil> idPerfil;
    public static volatile SingularAttribute<SUsuario, String> correo;
    public static volatile CollectionAttribute<SUsuario, RPerfilAcceso> rPerfilAccesoCollection;
    public static volatile SingularAttribute<SUsuario, String> usuario;
    public static volatile CollectionAttribute<SUsuario, CEmpresa> cEmpresaCollection;
    public static volatile SingularAttribute<SUsuario, String> telefono;

}