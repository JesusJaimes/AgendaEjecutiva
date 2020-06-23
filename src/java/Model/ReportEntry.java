/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author Romario
 */
public class ReportEntry {
    
    private String accion;
    private Date fecha;
    private String asuntoCita;
    private String fechaCita;
    private String horaInicio;
    private String horaFinal;

    public ReportEntry(String accion, Date fecha, String asuntoCita, String fechaCita, String horaInicio, String horaFinal) {
        this.accion = accion;
        this.fecha = fecha;
        this.asuntoCita = asuntoCita;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAsuntoCita() {
        return asuntoCita;
    }

    public void setAsuntoCita(String asuntoCita) {
        this.asuntoCita = asuntoCita;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
    
    

    
    
}
