/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Negocio.AgendaEjecutiva;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "agenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findByNombre", query = "SELECT a FROM Agenda a WHERE a.agendaPK.nombre = :nombre"),
    @NamedQuery(name = "Agenda.findByDescripcion", query = "SELECT a FROM Agenda a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Agenda.findByUsuario", query = "SELECT a FROM Agenda a WHERE a.agendaPK.usuario = :usuario"),
    @NamedQuery(name = "Agenda.findByFecha", query = "SELECT a FROM Agenda a WHERE a.fecha = :fecha")})
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgendaPK agendaPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "usuario", referencedColumnName = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agenda1")
    private List<Cita> citaList;

    public Agenda() {
    }

    public Agenda(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public Agenda(String nombre, String usuario) {
        this.agendaPK = new AgendaPK(nombre, usuario);
    }

    public Agenda(AgendaPK agendaPK, String descripcion, Date fecha, Usuario usuario1) {
        this.agendaPK = agendaPK;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.usuario1 = usuario1;
        this.citaList = citaList;
    }

    public AgendaPK getAgendaPK() {
        return agendaPK;
    }

    public void setAgendaPK(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @XmlTransient
    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agendaPK != null ? agendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.agendaPK == null && other.agendaPK != null) || (this.agendaPK != null && !this.agendaPK.equals(other.agendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Agenda[ agendaPK=" + agendaPK + " ]";
    }
    
    public String citasToHtmlFormat(){
        List<Cita> citasList = getCitaList();
        String citas = "";
        if(citasList!=null && !citasList.isEmpty()){
            AgendaEjecutiva.odernarCitas(citasList, 0, ((citasList.size())-1));
            for(Cita c: citasList){
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                String date = DATE_FORMAT.format(c.getFecha());
                Instant instant = Instant.ofEpochMilli(c.getHora().getTime());
                LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
                String horaStr = ""+res.getHour();
                String minutoStr = ""+res.getMinute();

                if(horaStr.length()==1){
                    horaStr = "0"+horaStr;
                }

                if(minutoStr.length()==1){
                    minutoStr = "0"+minutoStr;
                }

                String time = horaStr+":"+minutoStr;

                citas += "<li class='cita'>"
                + "<div class='datos-cita'>"
                + "<span><b>Fecha:</b> "+date+" <b>|</b> <b>Hora:</b> "+time
                + "<span class='contbtncita'>"
                + "<form action='ir_detalle_cita.do' method='POST'>"
                        + "<input type='text' name='idcita' style='display:none;' value='"+c.getCitaPK().getId()+"' required/>"
                        + "<button class='btncita'><i class=\"fas fa-info\"></i></button></form>"
                + "<form action='eliminar_cita.do' method='POST'>"
                        + "<input type='text' name='idcita' style='display:none;' value='"+c.getCitaPK().getId()+"' required/>"
                        + "<button class='btncita'><i class=\"fas fa-trash-alt\"></i></button></form>"
                + "</span>"
                + "</span>"
                + "<p class='separator'></p>"
                + "<p><b>"+c.getAsunto()+"</b></p>"
                + "</div>"
                + "</li>";
            }
        }
        return citas;
    }
    
}
