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
public class AgendaCompartidaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "owner")
    private String owner;
    @Basic(optional = false)
    @Column(name = "agenda")
    private int agenda;
    @Basic(optional = false)
    @Column(name = "compartido")
    private String compartido;

    public AgendaCompartidaPK() {
    }

    public AgendaCompartidaPK(String owner, int agenda, String compartido) {
        this.owner = owner;
        this.agenda = agenda;
        this.compartido = compartido;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getAgenda() {
        return agenda;
    }

    public void setAgenda(int agenda) {
        this.agenda = agenda;
    }

    public String getCompartido() {
        return compartido;
    }

    public void setCompartido(String compartido) {
        this.compartido = compartido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (owner != null ? owner.hashCode() : 0);
        hash += (int) agenda;
        hash += (compartido != null ? compartido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaCompartidaPK)) {
            return false;
        }
        AgendaCompartidaPK other = (AgendaCompartidaPK) object;
        if ((this.owner == null && other.owner != null) || (this.owner != null && !this.owner.equals(other.owner))) {
            return false;
        }
        if (this.agenda != other.agenda) {
            return false;
        }
        if ((this.compartido == null && other.compartido != null) || (this.compartido != null && !this.compartido.equals(other.compartido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.AgendaCompartidaPK[ owner=" + owner + ", agenda=" + agenda + ", compartido=" + compartido + " ]";
    }
    
}
