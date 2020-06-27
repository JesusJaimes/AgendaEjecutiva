/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "agenda_compartida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgendaCompartida.findAll", query = "SELECT a FROM AgendaCompartida a"),
    @NamedQuery(name = "AgendaCompartida.findByOwner", query = "SELECT a FROM AgendaCompartida a WHERE a.agendaCompartidaPK.owner = :owner"),
    @NamedQuery(name = "AgendaCompartida.findByAgenda", query = "SELECT a FROM AgendaCompartida a WHERE a.agendaCompartidaPK.agenda = :agenda"),
    @NamedQuery(name = "AgendaCompartida.findByCompartido", query = "SELECT a FROM AgendaCompartida a WHERE a.agendaCompartidaPK.compartido = :compartido"),
    @NamedQuery(name = "AgendaCompartida.findByPermisos", query = "SELECT a FROM AgendaCompartida a WHERE a.permisos = :permisos")})
public class AgendaCompartida implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgendaCompartidaPK agendaCompartidaPK;
    @Column(name = "permisos")
    private Integer permisos;
    @JoinColumns({
        @JoinColumn(name = "owner", referencedColumnName = "usuario", insertable = false, updatable = false),
        @JoinColumn(name = "agenda", referencedColumnName = "id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agenda agenda;

    public AgendaCompartida() {
    }

    public AgendaCompartida(AgendaCompartidaPK agendaCompartidaPK) {
        this.agendaCompartidaPK = agendaCompartidaPK;
    }

    public AgendaCompartida(String owner, int agenda, String compartido) {
        this.agendaCompartidaPK = new AgendaCompartidaPK(owner, agenda, compartido);
    }

    public AgendaCompartidaPK getAgendaCompartidaPK() {
        return agendaCompartidaPK;
    }

    public void setAgendaCompartidaPK(AgendaCompartidaPK agendaCompartidaPK) {
        this.agendaCompartidaPK = agendaCompartidaPK;
    }

    public Integer getPermisos() {
        return permisos;
    }

    public void setPermisos(Integer permisos) {
        this.permisos = permisos;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agendaCompartidaPK != null ? agendaCompartidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaCompartida)) {
            return false;
        }
        AgendaCompartida other = (AgendaCompartida) object;
        if ((this.agendaCompartidaPK == null && other.agendaCompartidaPK != null) || (this.agendaCompartidaPK != null && !this.agendaCompartidaPK.equals(other.agendaCompartidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.AgendaCompartida[ agendaCompartidaPK=" + agendaCompartidaPK + " ]";
    }
    
}
