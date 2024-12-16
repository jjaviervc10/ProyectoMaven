/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "mVenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MVenta.findAll", query = "SELECT m FROM MVenta m"),
    @NamedQuery(name = "MVenta.findByIdVenta", query = "SELECT m FROM MVenta m WHERE m.idVenta = :idVenta"),
    @NamedQuery(name = "MVenta.findByFechaVenta", query = "SELECT m FROM MVenta m WHERE m.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "MVenta.findByTotalVenta", query = "SELECT m FROM MVenta m WHERE m.totalVenta = :totalVenta")})
public class MVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idVenta")
    private Integer idVenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaVenta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalVenta")
    private BigDecimal totalVenta;
    @JoinColumn(name = "idEmpleado", referencedColumnName = "idEmpleado")
    @ManyToOne
    private CEmpleado idEmpleado;
    @JoinColumn(name = "idSucursal", referencedColumnName = "idSucursal")
    @ManyToOne(optional = false)
    private CSucursal idSucursal;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private SUsuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private Collection<HVenta> hVentaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVenta")
    private Collection<DVenta> dVentaCollection;

    public MVenta() {
    }

    public MVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public MVenta(Integer idVenta, Date fechaVenta, BigDecimal totalVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public CEmpleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(CEmpleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public CSucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(CSucursal idSucursal) {
        this.idSucursal = idSucursal;
    }

    public SUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(SUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public Collection<HVenta> getHVentaCollection() {
        return hVentaCollection;
    }

    public void setHVentaCollection(Collection<HVenta> hVentaCollection) {
        this.hVentaCollection = hVentaCollection;
    }

    @XmlTransient
    public Collection<DVenta> getDVentaCollection() {
        return dVentaCollection;
    }

    public void setDVentaCollection(Collection<DVenta> dVentaCollection) {
        this.dVentaCollection = dVentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenta != null ? idVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MVenta)) {
            return false;
        }
        MVenta other = (MVenta) object;
        if ((this.idVenta == null && other.idVenta != null) || (this.idVenta != null && !this.idVenta.equals(other.idVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MVenta[ idVenta=" + idVenta + " ]";
    }
    
}
