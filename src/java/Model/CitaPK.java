/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Romario
 */
@Embeddable
public class CitaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Basic(optional = false)
    @Column(name = "agenda")
    private int agenda;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;

    public CitaPK() {
    }

    public CitaPK(long id, int agenda, String usuario) {
        this.id = id;
        this.agenda = agenda;
        this.usuario = usuario;
    }

    public CitaPK(String user, int agenda, long idCita) {
        this.id = idCita;
        this.agenda = agenda;
        this.usuario = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAgenda() {
        return agenda;
    }

    public void setAgenda(int agenda) {
        this.agenda = agenda;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) agenda;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CitaPK)) {
            return false;
        }
        CitaPK other = (CitaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.agenda != other.agenda) {
            return false;
        }
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.CitaPK[ id=" + id + ", agenda=" + agenda + ", usuario=" + usuario + " ]";
    }
    
}
