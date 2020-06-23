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
    @NamedQuery(name = "Cita.findById", query = "SELECT c FROM Cita c WHERE c.citaPK.id = :id"),
    @NamedQuery(name = "Cita.findByFechaCreacion", query = "SELECT c FROM Cita c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Cita.findByHoraFinal", query = "SELECT c FROM Cita c WHERE c.horaFinal = :horaFinal")})
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
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "hora_final")
    @Temporal(TemporalType.TIME)
    private Date horaFinal;
    @JoinColumns({
        @JoinColumn(name = "usuario", referencedColumnName = "usuario", insertable = false, updatable = false),
        @JoinColumn(name = "agenda", referencedColumnName = "nombre", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agenda agenda1;

    public Cita() {
    }

    public Cita(CitaPK citaPK) {
        this.citaPK = citaPK;
    }

    public Cita(String usuario, String agenda, long id) {
        this.citaPK = new CitaPK(usuario, agenda, id);
    }

    public Cita(CitaPK citaPK, String asunto, String descripcion, Date hora, Date fecha, Boolean completada, Date fechaCreacion, Date horaFinal) {
        this.citaPK = citaPK;
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.hora = hora;
        this.fecha = fecha;
        this.completada = completada;
        this.fechaCreacion = fechaCreacion;
        this.horaFinal = horaFinal;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Agenda getAgenda1() {
        return agenda1;
    }

    public void setAgenda1(Agenda agenda1) {
        this.agenda1 = agenda1;
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

        String time = AgendaEjecutiva.formatDateToStringHour(this.hora);
        String time2 = AgendaEjecutiva.formatDateToStringHour(this.horaFinal);
        String cita ="";
        if(getCompletada()){
            cita = "<li class='cita'>"
            + "<div class='datos-cita'>"
            + "<span><b>Fecha:</b> "+date+" <b>|</b><b>Hora Inicio:</b> "+time+" <b>|</b> <b>Hora Terminacion:</b> "+time2
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
        }else{
            cita = "<li class='cita'>"
            + "<div class='datos-cita'>"
            + "<span><b>Fecha:</b> "+date+" <b>|</b> <b>Hora Inicio:</b> "+time+" <b>|</b> <b>Hora Terminacion:</b> "+time2
            + "<span class='contbtncita'>"
            + "<form action='ir_detalle_cita.do' method='POST'>"
                    + "<input type='text' name='idcita' style='display:none;' value='"+this.getCitaPK().getId()+"' required/>"
                    + "<button class='btncita'><i class=\"fas fa-info\"></i></button></form>"
            + "<form action='eliminar_cita.do' method='POST'>"
                    + "<input type='text' name='idcita' style='display:none;' value='"+this.getCitaPK().getId()+"' required/>"
                    + "<button class='btncita'><i class=\"fas fa-trash-alt\"></i></button></form>"
            + "<form action='marcar_cita.do' method='POST'>"
                    + "<input type='text' name='idcita' style='display:none;' value='"+this.getCitaPK().getId()+"' required/>"
                    + "<button class='btncita'><i class=\"fas fa-check-square\"></i></button></form>"
            + "</span>"
            + "</span>"
            + "<p class='separator'></p>"
            + "<p style='word-wrap: break-word;'><b>"+this.getAsunto()+"</b></p>"
            + "</div>"
            + "</li>";
        }
        
        return cita;
    }
    
}
