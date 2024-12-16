/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Blueweb
 */
@Entity
@Table(name = "sUsuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SUsuario.findAll", query = "SELECT s FROM SUsuario s"),
    @NamedQuery(name = "SUsuario.findByIdUsuario", query = "SELECT s FROM SUsuario s WHERE s.idUsuario = :idUsuario"),
    @NamedQuery(name = "SUsuario.findByUsuario", query = "SELECT s FROM SUsuario s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SUsuario.findByPass", query = "SELECT s FROM SUsuario s WHERE s.pass = :pass"),
    @NamedQuery(name = "SUsuario.findByNombreCompleto", query = "SELECT s FROM SUsuario s WHERE s.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "SUsuario.findByCorreo", query = "SELECT s FROM SUsuario s WHERE s.correo = :correo"),
    @NamedQuery(name = "SUsuario.findByTelefono", query = "SELECT s FROM SUsuario s WHERE s.telefono = :telefono")})
public class SUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombreCompleto")
    private String nombreCompleto;
    @Size(max = 50)
    @Column(name = "correo")
    private String correo;
    @Size(max = 10)
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<CEmpleado> cEmpleadoCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<CEmpresa> cEmpresaCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<CSucursal> cSucursalCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<MVenta> mVentaCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<SPerfil> sPerfilCollection;
    @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")
    @ManyToOne
    private SPerfil idPerfil;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<RPerfilAcceso> rPerfilAccesoCollection;  // NUEVO: Campo para la relación

    public SUsuario() {
    }

    public SUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public SUsuario(Integer idUsuario, String usuario, String pass, String nombreCompleto) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.pass = pass;
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public Collection<RPerfilAcceso> getRPerfilAccesoCollection() {
        return rPerfilAccesoCollection;  // Método para acceder a la colección de RPerfilAcceso
    }

    public void setRPerfilAccesoCollection(Collection<RPerfilAcceso> rPerfilAccesoCollection) {
        this.rPerfilAccesoCollection = rPerfilAccesoCollection;
    }

    @XmlTransient
    public Collection<CEmpleado> getCEmpleadoCollection() {
        return cEmpleadoCollection;
    }

    public void setCEmpleadoCollection(Collection<CEmpleado> cEmpleadoCollection) {
        this.cEmpleadoCollection = cEmpleadoCollection;
    }

    @XmlTransient
    public Collection<CEmpresa> getCEmpresaCollection() {
        return cEmpresaCollection;
    }

    public void setCEmpresaCollection(Collection<CEmpresa> cEmpresaCollection) {
        this.cEmpresaCollection = cEmpresaCollection;
    }

    @XmlTransient
    public Collection<CSucursal> getCSucursalCollection() {
        return cSucursalCollection;
    }

    public void setCSucursalCollection(Collection<CSucursal> cSucursalCollection) {
        this.cSucursalCollection = cSucursalCollection;
    }

    @XmlTransient
    public Collection<MVenta> getMVentaCollection() {
        return mVentaCollection;
    }

    public void setMVentaCollection(Collection<MVenta> mVentaCollection) {
        this.mVentaCollection = mVentaCollection;
    }

    @XmlTransient
    public Collection<SPerfil> getSPerfilCollection() {
        return sPerfilCollection;
    }

    public void setSPerfilCollection(Collection<SPerfil> sPerfilCollection) {
        this.sPerfilCollection = sPerfilCollection;
    }

    public SPerfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(SPerfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SUsuario)) {
            return false;
        }
        SUsuario other = (SUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SUsuario[ idUsuario=" + idUsuario + " ]";
    }

}
