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
import javax.persistence.Id;
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
@Table(name = "sAplicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SAplicacion.findAll", query = "SELECT s FROM SAplicacion s"),
    @NamedQuery(name = "SAplicacion.findByIdAplicacion", query = "SELECT s FROM SAplicacion s WHERE s.idAplicacion = :idAplicacion"),
    @NamedQuery(name = "SAplicacion.findByNombreAplicacion", query = "SELECT s FROM SAplicacion s WHERE s.nombreAplicacion = :nombreAplicacion"),
    @NamedQuery(name = "SAplicacion.findByIcono", query = "SELECT s FROM SAplicacion s WHERE s.icono = :icono"),
    @NamedQuery(name = "SAplicacion.findByUrl", query = "SELECT s FROM SAplicacion s WHERE s.url = :url"),
    @NamedQuery(name = "SAplicacion.findByOrden", query = "SELECT s FROM SAplicacion s WHERE s.orden = :orden"),
    @NamedQuery(name = "SAplicacion.findByActivo", query = "SELECT s FROM SAplicacion s WHERE s.activo = :activo"),
    @NamedQuery(name = "SAplicacion.findByFechaAlta", query = "SELECT s FROM SAplicacion s WHERE s.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "SAplicacion.findByFechaBaja", query = "SELECT s FROM SAplicacion s WHERE s.fechaBaja = :fechaBaja")})
public class SAplicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAplicacion")
    private Integer idAplicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreAplicacion")
    private String nombreAplicacion;
    @Size(max = 50)
    @Column(name = "icono")
    private String icono;
    @Size(max = 50)
    @Column(name = "url")
    private String url;
    @Column(name = "orden")
    private Integer orden;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAplicacion")
    private Collection<SModulo> sModuloCollection;

    public SAplicacion() {
    }

    public SAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public SAplicacion(Integer idAplicacion, String nombreAplicacion, boolean activo, Date fechaAlta) {
        this.idAplicacion = idAplicacion;
        this.nombreAplicacion = nombreAplicacion;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(Integer idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    public String getNombreAplicacion() {
        return nombreAplicacion;
    }

    public void setNombreAplicacion(String nombreAplicacion) {
        this.nombreAplicacion = nombreAplicacion;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    @XmlTransient
    public Collection<SModulo> getSModuloCollection() {
        return sModuloCollection;
    }

    public void setSModuloCollection(Collection<SModulo> sModuloCollection) {
        this.sModuloCollection = sModuloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAplicacion != null ? idAplicacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SAplicacion)) {
            return false;
        }
        SAplicacion other = (SAplicacion) object;
        if ((this.idAplicacion == null && other.idAplicacion != null) || (this.idAplicacion != null && !this.idAplicacion.equals(other.idAplicacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SAplicacion[ idAplicacion=" + idAplicacion + " ]";
    }
    
}
