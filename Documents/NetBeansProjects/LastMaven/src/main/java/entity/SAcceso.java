/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "sAcceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SAcceso.findAll", query = "SELECT s FROM SAcceso s"),
    @NamedQuery(name = "SAcceso.findByIdAcceso", query = "SELECT s FROM SAcceso s WHERE s.idAcceso = :idAcceso"),
    @NamedQuery(name = "SAcceso.findByNombreAcceso", query = "SELECT s FROM SAcceso s WHERE s.nombreAcceso = :nombreAcceso"),
    @NamedQuery(name = "SAcceso.findByOrden", query = "SELECT s FROM SAcceso s WHERE s.orden = :orden"),
    @NamedQuery(name = "SAcceso.findByActivo", query = "SELECT s FROM SAcceso s WHERE s.activo = :activo"),
    @NamedQuery(name = "SAcceso.findByFechaAlta", query = "SELECT s FROM SAcceso s WHERE s.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "SAcceso.findByFechaBaja", query = "SELECT s FROM SAcceso s WHERE s.fechaBaja = :fechaBaja")})
public class SAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@Basic(optional = false)
    //@NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAcceso")
    private Integer idAcceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreAcceso")
    private String nombreAcceso;
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
    @JoinColumn(name = "idModulo", referencedColumnName = "idModulo")
    @ManyToOne
    private SModulo idModulo;

    @ManyToMany(mappedBy = "sAccesoCollection")  // Relación inversa con SPerfil
    private List<SPerfil> sPerfilCollection;

    public SAcceso() {
    }

    public SAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public SAcceso(Integer idAcceso, String nombreAcceso, boolean activo, Date fechaAlta) {
        this.idAcceso = idAcceso;
        this.nombreAcceso = nombreAcceso;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }

    public String getNombreAcceso() {
        return nombreAcceso;
    }

    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
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

    public SModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(SModulo idModulo) {
        this.idModulo = idModulo;
    }

    /*public SPerfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(SPerfil idPerfil) {
        this.idPerfil = idPerfil;
    }*/
    public List<SPerfil> getSPerfilCollection() {
        return sPerfilCollection;
    }

    public void setSPerfilCollection(List<SPerfil> sPerfilCollection) {
        this.sPerfilCollection = sPerfilCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcceso != null ? idAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SAcceso)) {
            return false;
        }
        SAcceso other = (SAcceso) object;
        if ((this.idAcceso == null && other.idAcceso != null) || (this.idAcceso != null && !this.idAcceso.equals(other.idAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SAcceso[ idAcceso=" + idAcceso + " ]";
    }

}
