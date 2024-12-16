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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
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
@Table(name = "rPerfilAcceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RPerfilAcceso.findAll", query = "SELECT r FROM RPerfilAcceso r"),
    @NamedQuery(name = "RPerfilAcceso.findByIdPerfil", query = "SELECT r FROM RPerfilAcceso r WHERE r.rPerfilAccesoPK.idPerfil = :idPerfil"),
    @NamedQuery(name = "RPerfilAcceso.findByIdAcceso", query = "SELECT r FROM RPerfilAcceso r WHERE r.rPerfilAccesoPK.idAcceso = :idAcceso"),
    @NamedQuery(name = "RPerfilAcceso.findByFechaServidor", query = "SELECT r FROM RPerfilAcceso r WHERE r.fechaServidor = :fechaServidor"),
    @NamedQuery(name = "RPerfilAcceso.findByIdUsuario", query = "SELECT r FROM RPerfilAcceso r WHERE r.idUsuario = :idUsuario")})
public class RPerfilAcceso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RPerfilAccesoPK rPerfilAccesoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaServidor")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaServidor;
   
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private SUsuario idUsuario; // Referencia a la entidad SUsuario
    private Collection<SUsuario> sUsuarioCollection;
 

    public RPerfilAcceso() {
    }

    public RPerfilAcceso(RPerfilAccesoPK rPerfilAccesoPK) {
        this.rPerfilAccesoPK = rPerfilAccesoPK;
    }

    public RPerfilAcceso(RPerfilAccesoPK rPerfilAccesoPK, Date fechaServidor) {
        this.rPerfilAccesoPK = rPerfilAccesoPK;
        this.fechaServidor = fechaServidor;
    }

    public RPerfilAcceso(int idPerfil, int idAcceso) {
        this.rPerfilAccesoPK = new RPerfilAccesoPK(idPerfil, idAcceso);
    }

    public RPerfilAccesoPK getRPerfilAccesoPK() {
        return rPerfilAccesoPK;
    }

    public void setRPerfilAccesoPK(RPerfilAccesoPK rPerfilAccesoPK) {
        this.rPerfilAccesoPK = rPerfilAccesoPK;
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
    public Collection<SUsuario> getSUsuarioCollection() {
        return sUsuarioCollection;
    }

    public void setSUsuarioCollection(Collection<SUsuario> sUsuarioCollection) {
        this.sUsuarioCollection = sUsuarioCollection;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rPerfilAccesoPK != null ? rPerfilAccesoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RPerfilAcceso)) {
            return false;
        }
        RPerfilAcceso other = (RPerfilAcceso) object;
        if ((this.rPerfilAccesoPK == null && other.rPerfilAccesoPK != null) || (this.rPerfilAccesoPK != null && !this.rPerfilAccesoPK.equals(other.rPerfilAccesoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RPerfilAcceso[ rPerfilAccesoPK=" + rPerfilAccesoPK + " ]";
    }
    
    
    // Método @PrePersist que asigna las fechas automáticamente si es null
    @PrePersist
    public void prePersist() {
    

        if (fechaServidor == null) {
            fechaServidor = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }

       

    }
    
}
