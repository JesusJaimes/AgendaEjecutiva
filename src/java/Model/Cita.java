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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "cita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByAsunto", query = "SELECT c FROM Cita c WHERE c.asunto = :asunto"),
    @NamedQuery(name = "Cita.findByDescripcion", query = "SELECT c FROM Cita c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Cita.findByHora", query = "SELECT c FROM Cita c WHERE c.hora = :hora"),
    @NamedQuery(name = "Cita.findByFecha", query = "SELECT c FROM Cita c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cita.findByCompletada", query = "SELECT c FROM Cita c WHERE c.completada = :completada"),
    @NamedQuery(name = "Cita.findByUsuario", query = "SELECT c FROM Cita c WHERE c.citaPK.usuario = :usuario"),
    @NamedQuery(name = "Cita.findByAgenda", query = "SELECT c FROM Cita c WHERE c.citaPK.agenda = :agenda"),
    @NamedQuery(name = "Cita.findById", query = "SELECT c FROM Cita c WHERE c.citaPK.id = :id")})
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CitaPK citaPK;
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "completada")
    private Boolean completada;
    @JoinColumns({
        @JoinColumn(name = "usuario", referencedColumnName = "usuario", insertable = false, updatable = false),
        @JoinColumn(name = "agenda", referencedColumnName = "nombre", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agenda agenda;

    public Cita() {
    }

    public Cita(CitaPK citaPK) {
        this.citaPK = citaPK;
    }

    public Cita(String usuario, String agenda, long id) {
        this.citaPK = new CitaPK(usuario, agenda, id);
    }
    
    public Cita(CitaPK citaPK, String asunto, String descripcion, Date hora, Date fecha, Boolean completada) {
        this.citaPK = citaPK;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.hora = hora;
        this.fecha = fecha;
        this.completada = completada;
    }

    public CitaPK getCitaPK() {
        return citaPK;
    }

    public void setCitaPK(CitaPK citaPK) {
        this.citaPK = citaPK;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

    public Agenda getAgenda1() {
        return agenda;
    }

    public void setAgenda1(Agenda agenda) {
        this.agenda = agenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (citaPK != null ? citaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.citaPK == null && other.citaPK != null) || (this.citaPK != null && !this.citaPK.equals(other.citaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Cita[ citaPK=" + citaPK + " ]";
    }
    
    public String citaToCardHtmlFormat(){
        
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        String date = DATE_FORMAT.format(this.getFecha());
        Instant instant = Instant.ofEpochMilli(this.getHora().getTime());
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

        String cita = "<li class='cita'>"
        + "<div class='datos-cita'>"
        + "<span><b>Fecha:</b> "+date+" <b>|</b> <b>Hora:</b> "+time
        + "<span class='contbtncita'>"
        + "<form action='ir_detalle_cita.do' method='POST'>"
                + "<input type='text' name='idcita' style='display:none;' value='"+this.getCitaPK().getId()+"' required/>"
                + "<button class='btncita'><i class=\"fas fa-info\"></i></button></form>"
        + "<form action='eliminar_cita.do' method='POST'>"
                + "<input type='text' name='idcita' style='display:none;' value='"+this.getCitaPK().getId()+"' required/>"
                + "<button class='btncita'><i class=\"fas fa-trash-alt\"></i></button></form>"
        + "</span>"
        + "</span>"
        + "<p class='separator'></p>"
        + "<p style='word-wrap: break-word;'><b>"+this.getAsunto()+"</b></p>"
        + "</div>"
        + "</li>";
            
        
        return cita;
    }
    
}
