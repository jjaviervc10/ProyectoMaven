/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Blueweb
 */
@Embeddable
public class RPerfilAccesoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idPerfil")
    private int idPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idAcceso")
    private int idAcceso;

    public RPerfilAccesoPK() {
    }

    public RPerfilAccesoPK(int idPerfil, int idAcceso) {
        this.idPerfil = idPerfil;
        this.idAcceso = idAcceso;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPerfil;
        hash += (int) idAcceso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RPerfilAccesoPK)) {
            return false;
        }
        RPerfilAccesoPK other = (RPerfilAccesoPK) object;
        if (this.idPerfil != other.idPerfil) {
            return false;
        }
        if (this.idAcceso != other.idAcceso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RPerfilAccesoPK[ idPerfil=" + idPerfil + ", idAcceso=" + idAcceso + " ]";
    }
    
}
