/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "sPerfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SPerfil.findAll", query = "SELECT s FROM SPerfil s"),
    @NamedQuery(name = "SPerfil.findByIdPerfil", query = "SELECT s FROM SPerfil s WHERE s.idPerfil = :idPerfil"),
    @NamedQuery(name = "SPerfil.findByNombrePerfil", query = "SELECT s FROM SPerfil s WHERE s.nombrePerfil = :nombrePerfil"),
    @NamedQuery(name = "SPerfil.findByDescripcionPerfil", query = "SELECT s FROM SPerfil s WHERE s.descripcionPerfil = :descripcionPerfil"),
    @NamedQuery(name = "SPerfil.findByActivo", query = "SELECT s FROM SPerfil s WHERE s.activo = :activo"),
    @NamedQuery(name = "SPerfil.findByFechaAlta", query = "SELECT s FROM SPerfil s WHERE s.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "SPerfil.findByFechaBaja", query = "SELECT s FROM SPerfil s WHERE s.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "SPerfil.findByFechaServidor", query = "SELECT s FROM SPerfil s WHERE s.fechaServidor = :fechaServidor")})
    
    public class SPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Basic(optional = false)
    //@NotNull
    @Column(name = "idPerfil")
    private Integer idPerfil;
    @Size(max = 50)
    @Column(name = "nombrePerfil")
    private String nombrePerfil;
    @Size(max = 150)
    @Column(name = "descripcionPerfil")
    private String descripcionPerfil;
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
    @OneToMany(mappedBy = "idPerfil")
    private Collection<SUsuario> sUsuarioCollection;
   // Relación muchos a muchos con SAcceso
    @ManyToMany
    @JoinTable(
            name = "perfil_acceso",
            joinColumns = @JoinColumn(name = "idPerfil"),
            inverseJoinColumns = @JoinColumn(name = "idAcceso"))
    
    private List<SAcceso> sAccesoCollection;;  // Accesos asignados a este perfil
    

    public SPerfil() {
    }

    public SPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public SPerfil(Integer idPerfil, boolean activo, Date fechaAlta, Date fechaServidor) {
        this.idPerfil = idPerfil;
        this.activo = activo;
        this.fechaAlta = fechaAlta;
        this.fechaServidor = fechaServidor;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public String getDescripcionPerfil() {
        return descripcionPerfil;
    }

    public void setDescripcionPerfil(String descripcionPerfil) {
        this.descripcionPerfil = descripcionPerfil;
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
    public Collection<SUsuario> getSUsuarioCollection() {
        return sUsuarioCollection;
    }

    public void setSUsuarioCollection(Collection<SUsuario> sUsuarioCollection) {
        this.sUsuarioCollection = sUsuarioCollection;
    }
    
     public List<SAcceso> getSAccesoCollection() {
        return sAccesoCollection;
    }

    public void setSAccesoCollection(List<SAcceso> sAccesoCollection) {
        this.sAccesoCollection = sAccesoCollection;
    }
  /*   @XmlTransient
    public Collection<SAcceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(Collection<SAcceso> accesos) {
        this.accesos = accesos;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SPerfil)) {
            return false;
        }
        SPerfil other = (SPerfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SPerfil[ idPerfil=" + idPerfil + " ]";
    }

    // Método @PrePersist que asigna las fechas automáticamente si es null
    @PrePersist
    public void prePersist() {
        if (activo == false) {
            activo = true; // La fecha del servidor de la aplicación (fecha local)
        }
        
        if (fechaAlta == null) {
            fechaAlta = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }

        if (fechaServidor == null) {
            fechaServidor = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }

        if (fechaBaja == null) {
            fechaBaja = new Date(); // La fecha del servidor de la aplicación (fecha local)
        }

    }
}
