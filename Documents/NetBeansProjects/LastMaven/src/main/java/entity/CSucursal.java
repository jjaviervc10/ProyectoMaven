
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
@Table(name = "cSucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CSucursal.findAll", query = "SELECT c FROM CSucursal c"),
    @NamedQuery(name = "CSucursal.findByIdSucursal", query = "SELECT c FROM CSucursal c WHERE c.idSucursal = :idSucursal"),
    @NamedQuery(name = "CSucursal.findByNombreSucursal", query = "SELECT c FROM CSucursal c WHERE c.nombreSucursal = :nombreSucursal"),
    @NamedQuery(name = "CSucursal.findByCiudad", query = "SELECT c FROM CSucursal c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "CSucursal.findByEstado", query = "SELECT c FROM CSucursal c WHERE c.estado = :estado"),
    @NamedQuery(name = "CSucursal.findByActivo", query = "SELECT c FROM CSucursal c WHERE c.activo = :activo"),
    @NamedQuery(name = "CSucursal.findByFechaAlta", query = "SELECT c FROM CSucursal c WHERE c.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "CSucursal.findByFechaBaja", query = "SELECT c FROM CSucursal c WHERE c.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "CSucursal.findByFechaServidor", query = "SELECT c FROM CSucursal c WHERE c.fechaServidor = :fechaServidor")})
public class CSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "idSucursal")
    private Integer idSucursal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreSucursal")
    private String nombreSucursal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "estado")
    private String estado;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSucursal")
    private Collection<CEmpleado> cEmpleadoCollection;
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa")
    @ManyToOne(optional = false)
    private CEmpresa idEmpresa;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private SUsuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSucursal")
    private Collection<MVenta> mVentaCollection;

    public CSucursal() {
    }

    public CSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public CSucursal(Integer idSucursal, String nombreSucursal, String ciudad, String estado, boolean activo, Date fechaAlta, Date fechaServidor) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaServidor = fechaServidor;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    @XmlTransient
    public Collection<CEmpleado> getCEmpleadoCollection() {
        return cEmpleadoCollection;
    }

    public void setCEmpleadoCollection(Collection<CEmpleado> cEmpleadoCollection) {
        this.cEmpleadoCollection = cEmpleadoCollection;
    }

    public CEmpresa getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(CEmpresa idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        hash += (idSucursal != null ? idSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSucursal)) {
            return false;
        }
        CSucursal other = (CSucursal) object;
        if ((this.idSucursal == null && other.idSucursal != null) || (this.idSucursal != null && !this.idSucursal.equals(other.idSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CSucursal[ idSucursal=" + idSucursal + " ]";
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
