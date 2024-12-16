/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "hVenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HVenta.findAll", query = "SELECT h FROM HVenta h"),
    @NamedQuery(name = "HVenta.findByIdHistVenta", query = "SELECT h FROM HVenta h WHERE h.idHistVenta = :idHistVenta"),
    @NamedQuery(name = "HVenta.findByNombreEmpresa", query = "SELECT h FROM HVenta h WHERE h.nombreEmpresa = :nombreEmpresa"),
    @NamedQuery(name = "HVenta.findByNombreSucursal", query = "SELECT h FROM HVenta h WHERE h.nombreSucursal = :nombreSucursal"),
    @NamedQuery(name = "HVenta.findByNombreCompletoEmpleado", query = "SELECT h FROM HVenta h WHERE h.nombreCompletoEmpleado = :nombreCompletoEmpleado"),
    @NamedQuery(name = "HVenta.findByNombreUsuario", query = "SELECT h FROM HVenta h WHERE h.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "HVenta.findByCantidadProductos", query = "SELECT h FROM HVenta h WHERE h.cantidadProductos = :cantidadProductos"),
    @NamedQuery(name = "HVenta.findByTotalVenta", query = "SELECT h FROM HVenta h WHERE h.totalVenta = :totalVenta")})

@SqlResultSetMapping(
    name = "HVentaMapping",
    entities = @EntityResult(
        entityClass = HVenta.class,
        fields = {
            @FieldResult(name = "idHistVenta", column = "idVenta"),
            @FieldResult(name = "totalVenta", column = "totalVenta"),
            @FieldResult(name = "nombreSucursal", column = "nombreSucursal"),
            @FieldResult(name = "nombreEmpresa", column = "nombreEmpresa"),
            @FieldResult(name = "nombreCompletoEmpleado", column = "nombreCompleto"),
            @FieldResult(name = "cantidadProductos", column = "cantidadProductos")
        }
    )
)


public class HVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Esto asegura que idHistVenta es auto-generado
    @Basic(optional = false)
    @NotNull
    @Column(name = "idHistVenta")
    private Integer idHistVenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreEmpresa")
    private String nombreEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreSucursal")
    private String nombreSucursal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombreCompletoEmpleado")
    private String nombreCompletoEmpleado;
    @Size(max = 150)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidadProductos")
    private int cantidadProductos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalVenta")
    private BigDecimal totalVenta;
    @JoinColumn(name = "idVenta", referencedColumnName = "idVenta")
    @ManyToOne(optional = false)
    private MVenta idVenta;

    public HVenta() {
    }

    public HVenta(Integer idHistVenta) {
        this.idHistVenta = idHistVenta;
    }

    public HVenta(Integer idHistVenta, String nombreEmpresa, String nombreSucursal, String nombreCompletoEmpleado, int cantidadProductos, BigDecimal totalVenta) {
        this.idHistVenta = idHistVenta;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreSucursal = nombreSucursal;
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
        this.cantidadProductos = cantidadProductos;
        this.totalVenta = totalVenta;
    }

    public Integer getIdHistVenta() {
        return idHistVenta;
    }

    public void setIdHistVenta(Integer idHistVenta) {
        this.idHistVenta = idHistVenta;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getNombreCompletoEmpleado() {
        return nombreCompletoEmpleado;
    }

    public void setNombreCompletoEmpleado(String nombreCompletoEmpleado) {
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public MVenta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(MVenta idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistVenta != null ? idHistVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HVenta)) {
            return false;
        }
        HVenta other = (HVenta) object;
        if ((this.idHistVenta == null && other.idHistVenta != null) || (this.idHistVenta != null && !this.idHistVenta.equals(other.idHistVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.HVenta[ idHistVenta=" + idHistVenta + " ]";
    }
    
}
