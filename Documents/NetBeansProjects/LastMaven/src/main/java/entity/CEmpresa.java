/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
@Table(name = "cEmpresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CEmpresa.findAll", query = "SELECT c FROM CEmpresa c"),
    @NamedQuery(name = "CEmpresa.findByIdEmpresa", query = "SELECT c FROM CEmpresa c WHERE c.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "CEmpresa.findByClaveEmpresa", query = "SELECT c FROM CEmpresa c WHERE c.claveEmpresa = :claveEmpresa"),
    @NamedQuery(name = "CEmpresa.findByNombreEmpresa", query = "SELECT c FROM CEmpresa c WHERE c.nombreEmpresa = :nombreEmpresa"),
    @NamedQuery(name = "CEmpresa.findByActivo", query = "SELECT c FROM CEmpresa c WHERE c.activo = :activo"),
    @NamedQuery(name = "CEmpresa.findByFechaAlta", query = "SELECT c FROM CEmpresa c WHERE c.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "CEmpresa.findByFechaBaja", query = "SELECT c FROM CEmpresa c WHERE c.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "CEmpresa.findByFechaServidor", query = "SELECT c FROM CEmpresa c WHERE c.fechaServidor = :fechaServidor")})
public class CEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "idEmpresa")
    private Integer idEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "claveEmpresa")
    private String claveEmpresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreEmpresa")
    private String nombreEmpresa;
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
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private SUsuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa")
    private Collection<CSucursal> cSucursalCollection;

    public CEmpresa() {
    }

    public CEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public CEmpresa(Integer idEmpresa, String claveEmpresa, String nombreEmpresa, boolean activo, Date fechaAlta, Date fechaServidor) {
        this.idEmpresa = idEmpresa;
        this.claveEmpresa = claveEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaServidor = fechaServidor;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getClaveEmpresa() {
        return claveEmpresa;
    }

    public void setClaveEmpresa(String claveEmpresa) {
        this.claveEmpresa = claveEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

    public SUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(SUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public Collection<CSucursal> getCSucursalCollection() {
        return cSucursalCollection;
    }

    public void setCSucursalCollection(Collection<CSucursal> cSucursalCollection) {
        this.cSucursalCollection = cSucursalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CEmpresa)) {
            return false;
        }
        CEmpresa other = (CEmpresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CEmpresa[ idEmpresa=" + idEmpresa + " ]";
    }

    // Método @PrePersist que asigna la fecha de alta automáticamente si es null
    @PrePersist
    public void prePersist() {
        if (fechaAlta == null) {
            fechaAlta = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }
        
         if (fechaServidor == null) {
            fechaServidor = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }
    }
   

}
