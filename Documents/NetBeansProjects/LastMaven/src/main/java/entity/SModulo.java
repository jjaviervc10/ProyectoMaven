/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
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
@Table(name = "sModulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SModulo.findAll", query = "SELECT s FROM SModulo s"),
    @NamedQuery(name = "SModulo.findByIdModulo", query = "SELECT s FROM SModulo s WHERE s.idModulo = :idModulo"),
    @NamedQuery(name = "SModulo.findByNombreModulo", query = "SELECT s FROM SModulo s WHERE s.nombreModulo = :nombreModulo"),
    @NamedQuery(name = "SModulo.findByIcono", query = "SELECT s FROM SModulo s WHERE s.icono = :icono"),
    @NamedQuery(name = "SModulo.findByUrl", query = "SELECT s FROM SModulo s WHERE s.url = :url"),
    @NamedQuery(name = "SModulo.findByOrden", query = "SELECT s FROM SModulo s WHERE s.orden = :orden"),
    @NamedQuery(name = "SModulo.findByActivo", query = "SELECT s FROM SModulo s WHERE s.activo = :activo"),
    @NamedQuery(name = "SModulo.findByFechaAlta", query = "SELECT s FROM SModulo s WHERE s.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "SModulo.findByFechaBaja", query = "SELECT s FROM SModulo s WHERE s.fechaBaja = :fechaBaja")})
public class SModulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idModulo")
    private Integer idModulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreModulo")
    private String nombreModulo;
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
    @JoinColumn(name = "idAplicacion", referencedColumnName = "idAplicacion")
    @ManyToOne(optional = false)
    private SAplicacion idAplicacion;
    @OneToMany(mappedBy = "idModulo")
    private Collection<SAcceso> sAccesoCollection;

    public SModulo() {
    }

    public SModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public SModulo(Integer idModulo, String nombreModulo, boolean activo, Date fechaAlta) {
        this.idModulo = idModulo;
        this.nombreModulo = nombreModulo;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
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

    public SAplicacion getIdAplicacion() {
        return idAplicacion;
    }

    public void setIdAplicacion(SAplicacion idAplicacion) {
        this.idAplicacion = idAplicacion;
    }

    @XmlTransient
    public Collection<SAcceso> getSAccesoCollection() {
        return sAccesoCollection;
    }

    public void setSAccesoCollection(Collection<SAcceso> sAccesoCollection) {
        this.sAccesoCollection = sAccesoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SModulo)) {
            return false;
        }
        SModulo other = (SModulo) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SModulo[ idModulo=" + idModulo + " ]";
    }
    
}
