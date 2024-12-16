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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "cEmpleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CEmpleado.findAll", query = "SELECT c FROM CEmpleado c"),
    @NamedQuery(name = "CEmpleado.findByIdEmpleado", query = "SELECT c FROM CEmpleado c WHERE c.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "CEmpleado.findByNombre", query = "SELECT c FROM CEmpleado c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CEmpleado.findByApellidoPaterno", query = "SELECT c FROM CEmpleado c WHERE c.apellidoPaterno = :apellidoPaterno"),
    @NamedQuery(name = "CEmpleado.findByApellidoMaterno", query = "SELECT c FROM CEmpleado c WHERE c.apellidoMaterno = :apellidoMaterno"),
    @NamedQuery(name = "CEmpleado.findByPuesto", query = "SELECT c FROM CEmpleado c WHERE c.puesto = :puesto"),
    @NamedQuery(name = "CEmpleado.findBySalario", query = "SELECT c FROM CEmpleado c WHERE c.salario = :salario"),
    @NamedQuery(name = "CEmpleado.findByActivo", query = "SELECT c FROM CEmpleado c WHERE c.activo = :activo"),
    @NamedQuery(name = "CEmpleado.findByFechaAlta", query = "SELECT c FROM CEmpleado c WHERE c.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "CEmpleado.findByFechaBaja", query = "SELECT c FROM CEmpleado c WHERE c.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "CEmpleado.findByFechaServidor", query = "SELECT c FROM CEmpleado c WHERE c.fechaServidor = :fechaServidor")})
public class CEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEmpleado")
    private Integer idEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Size(max = 50)
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "puesto")
    private String puesto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "salario")
    private BigDecimal salario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaAlta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "fechaBaja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaServidor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaServidor;
    @JoinColumn(name = "idSucursal", referencedColumnName = "idSucursal")
    @ManyToOne(optional = false)
    private CSucursal idSucursal;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private SUsuario idUsuario;
    @OneToMany(mappedBy = "idEmpleado")
    private Collection<MVenta> mVentaCollection;

    public CEmpleado() {
    }

    public CEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public CEmpleado(Integer idEmpleado, String nombre, String apellidoPaterno, String puesto, BigDecimal salario, boolean activo, Date fechaAlta, Date fechaServidor) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.puesto = puesto;
        this.salario = salario;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaServidor = fechaServidor;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Date fechaServidor) {
        this.fechaServidor = fechaServidor;
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
    public Collection<MVenta> getMVentaCollection() {
        return mVentaCollection;
    }

    public void setMVentaCollection(Collection<MVenta> mVentaCollection) {
        this.mVentaCollection = mVentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CEmpleado)) {
            return false;
        }
        CEmpleado other = (CEmpleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CEmpleado[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
