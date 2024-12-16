package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "dVenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DVenta.findAll", query = "SELECT d FROM DVenta d"),
    @NamedQuery(name = "DVenta.findByIdDetalleVenta", query = "SELECT d FROM DVenta d WHERE d.idDetalleVenta = :idDetalleVenta"),
    @NamedQuery(name = "DVenta.findByIdProducto", query = "SELECT d FROM DVenta d WHERE d.idProducto = :idProducto"),
    @NamedQuery(name = "DVenta.findByCantidad", query = "SELECT d FROM DVenta d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DVenta.findByPrecio", query = "SELECT d FROM DVenta d WHERE d.precio = :precio")})
public class DVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDetalleVenta")
    private Integer idDetalleVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProducto")
    private int idProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private BigDecimal precio;
    @JoinColumn(name = "idVenta", referencedColumnName = "idVenta")
    @ManyToOne(optional = false)
    private MVenta idVenta;

    public DVenta() {
    }

    public DVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public DVenta(Integer idDetalleVenta, int idProducto, int cantidad, BigDecimal precio) {
        this.idDetalleVenta = idDetalleVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Integer getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(Integer idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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
        hash += (idDetalleVenta != null ? idDetalleVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DVenta)) {
            return false;
        }
        DVenta other = (DVenta) object;
        if ((this.idDetalleVenta == null && other.idDetalleVenta != null) || (this.idDetalleVenta != null && !this.idDetalleVenta.equals(other.idDetalleVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DVenta[ idDetalleVenta=" + idDetalleVenta + " ]";
    }
    
}
