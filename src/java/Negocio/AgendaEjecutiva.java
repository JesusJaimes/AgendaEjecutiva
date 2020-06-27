/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.AgendaJpaController;
import DAO.CitaJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Model.Agenda;
import Model.AgendaPK;
import Model.Cita;
import Model.CitaPK;
import Model.ReportEntry;
import Model.Usuario;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Romario
 */
public class AgendaEjecutiva {
    
    static UsuarioJpaController usuarioDAO = new UsuarioJpaController();
    static CitaJpaController citaDAO = new CitaJpaController();
    static AgendaJpaController agendaDAO = new AgendaJpaController();
    
    public AgendaEjecutiva(){
    }
    
    public static List<Agenda> getAgendas(){
        
        return agendaDAO.findAgendaEntities();
    }
    
    public static Usuario getUsuario(String usuario){
        
        return usuarioDAO.findUsuario(usuario);
    }
    
    public static boolean validarUsuario(String user, String password){
        Usuario usuario = usuarioDAO.findUsuario(user);
        if(usuario!=null){
            if(usuario.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean insertarUsuario(Usuario usuario){
         try{
            usuarioDAO.create(usuario);
//            System.out.println("true");
            return true;
            
        }catch(Exception e){
//            System.out.println("false");
            return false;
        }
    }
    
    public static boolean insertarAgenda(Agenda agenda){
         try{
            agendaDAO.create(agenda);
//            System.out.println("true");
            return true;
            
        }catch(Exception e){
//            System.out.println("false");
            return false;
        }
    }
    
    public static List<Cita> getCitasAgenda(Agenda agenda){
        return agenda.getCitaList();
    }
    
    public static Agenda getAgenda(String usuario, int agenda){
        return agendaDAO.findAgenda(new AgendaPK(usuario, agenda));
    }
    
    public static void actualizarAgenda(Agenda agendaObj, String usuario, int agenda){
        try{
            Agenda agendaExistente = getAgenda(usuario, agenda);
            agendaExistente.setCitaList(agendaObj.getCitaList());
            agendaDAO.edit(agendaExistente);
        }catch(Exception e){
            System.out.println(e.getStackTrace().toString());
        }
    }
    
    public static void actualizarUsuario(Usuario usuario){
        try{
            Usuario usuarioExistente = getUsuario(usuario.getEmail());
            usuarioExistente.setAgendaList(usuario.getAgendaList());
            usuarioDAO.edit(usuarioExistente);
        }catch(Exception e){
            System.out.println(e.getStackTrace().toString());
        }
    }
    
    public static boolean insertarCita(Cita cita){
         try{
//            System.out.println("SE VA A INTENTAR INSERTAR CITA");
            citaDAO.create(cita);
//            System.out.println("SE LOGRO INSERTAR CITA");
//            System.out.println("true");
            return true;
            
        }catch(Exception e){
//            System.out.println("SE FALLO INSERTAR CITA");
//            System.out.println("false");
            return false;
        }
    }
    
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static Date localTimeToDate(LocalTime localTime){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(0, 0, 0, localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        return calendar.getTime();
    }
    
    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public static LocalTime dateToLocalTime(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }
    
    public static void odernarCitas(List<Cita> citas, int start, int end){
 
        int partition = partition(citas, start, end);
 
        if(partition-1>start) {
            odernarCitas(citas, start, partition - 1);
        }
        if(partition+1<end) {
            odernarCitas(citas, partition + 1, end);
        }
    }
 
    public static int partition(List<Cita> citas, int start, int end){
        Cita pivot = citas.get(end);
 
        for(int i=start; i<end; i++){
            LocalDate citaDate = dateToLocalDate(citas.get(i).getFecha());
            LocalDateTime citaLocalDateTime = citaDate.atTime(dateToLocalTime(citas.get(i).getHora()));
            
            LocalDate pivotDate = dateToLocalDate(pivot.getFecha());
            LocalDateTime pivotLocalDateTime = pivotDate.atTime(dateToLocalTime(pivot.getHora()));
            
            if(citaLocalDateTime.isBefore(pivotLocalDateTime)){
                Cita temp= citas.get(start);
                citas.set(start, citas.get(i));
                citas.set(i, temp);
                start++;
            }
        }
 
        Cita temp = citas.get(start);
        citas.set(start, pivot);
        citas.set(end, temp);
 
        return start;
    }
    
    public static String getFechaStringYearMonthDay() {
        LocalDate localDate = LocalDate.now();
    
        String year = ""+localDate.getYear();
        String month = ""+localDate.getMonthValue();
        String day = ""+localDate.getDayOfMonth();

        if(month.length()<2){
            month="0"+month;
        }
        if(day.length()<2){
            day="0"+day;
        }

        String fecha = year+"-"+month+"-"+day;
        return fecha;
    }
    
    public static String getTimeStringHourMinute() {
        LocalTime localTime = LocalTime.now();
    
        String hour = ""+localTime.getHour();
        String minute = ""+localTime.getMinute();

        if(hour.length()<2){
            hour="0"+hour;
        }
        if(minute.length()<2){
            minute="0"+minute;
        }

        String hora = hour+":"+minute;
        return hora;
    }
    
    public static Cita getCita(CitaPK citaPK){
        return citaDAO.findCita(citaPK);
    }
    
    public static boolean eliminarCita(CitaPK citaPK){
        try{
            citaDAO.destroy(citaPK);
            return true;
        }catch(NonexistentEntityException e){
            return false;
        }
    }
    
    public static boolean eliminarAgenda(Agenda agenda) {
        try{
            if(!agenda.getCitaList().isEmpty()){
                for(Cita c: agenda.getCitaList()){
                    citaDAO.destroy(c.getCitaPK());
                }
            }
            agendaDAO.destroy(agenda.getAgendaPK());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public static boolean actualizarCita(Cita cita, CitaPK citaId){
        try{
            Cita citaExistente = getCita(citaId);
            citaExistente.setAsunto(cita.getAsunto());
            citaExistente.setFecha(cita.getFecha());
            citaExistente.setHora(cita.getHora());
            citaExistente.setHoraFinal(cita.getHoraFinal());
            citaExistente.setDescripcion(cita.getDescripcion());
            citaDAO.edit(citaExistente);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public static boolean actualizarEstadoCita(Cita cita, CitaPK citaId){
        try{
            Cita citaExistente = getCita(citaId);
            citaExistente.setCompletada(true);
            citaDAO.edit(citaExistente);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    public static String obtenerTablaHtmlReporte(List<Cita> citasAgenda, Date fechaDesde, Date fechaHasta){
        String reporte = "<table>"
                + "<tr>"
                + "<th>Accion</th>"
                + "<th>Fecha</th>"
                + "<th>Asunto de cita</th>"
                + "<th>Fecha de cita</th>"
                + "<th>Hora de inicio</th>"
                + "<th>Hora de finalizacion</th>"
                + "</tr>";
        
        
        ArrayList<ReportEntry> report = obtenerArrayListReporte(citasAgenda, fechaDesde, fechaHasta);
        
        for(ReportEntry re:report){
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = DATE_FORMAT.format(re.getFecha());
            reporte+="<tr>"
                + "<td>"+re.getAccion()+"</td>"
                + "<td>"+fecha+"</td>"
                + "<td>"+re.getAsuntoCita()+"</td>"
                + "<td>"+re.getFechaCita()+"</td>"
                + "<td>"+re.getHoraInicio()+"</td>"
                + "<td>"+re.getHoraFinal()+"</td>"
                + "</tr>";
        }
        reporte+="</table>";
        
        return reporte;
    }
    
    public static ArrayList<ReportEntry> obtenerArrayListReporte(List<Cita> citasAgenda, Date fechaDesde, Date fechaHasta){
        ArrayList<ReportEntry> report = new ArrayList<>();
         
        for(Cita c: citasAgenda){
            if(fechaEnRango(c.getFechaCreacion(), fechaDesde, fechaHasta)){
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                String fechaCita = DATE_FORMAT.format(c.getFecha());
                report.add(new ReportEntry("Se creó", c.getFechaCreacion(), c.getAsunto(), fechaCita, formatDateToStringHour(c.getHora()), formatDateToStringHour(c.getHoraFinal())));
            }
            
            if(fechaEnRango(c.getFecha(), fechaDesde, fechaHasta) && c.getCompletada()){
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
                String fechaCita = DATE_FORMAT.format(c.getFecha());
                report.add(new ReportEntry("Se realizó", c.getFecha(), c.getAsunto(), fechaCita, formatDateToStringHour(c.getHora()), formatDateToStringHour(c.getHoraFinal())));
            }
        }
        
        odernarListReporte(report, 0, ((report.size())-1));
         
        return report;
    }
    
    
    public static void odernarListReporte(ArrayList<ReportEntry> report, int start, int end){
 
        int partition = partition2(report, start, end);
 
        if(partition-1>start) {
            odernarListReporte(report, start, partition - 1);
        }
        if(partition+1<end) {
            odernarListReporte(report, partition + 1, end);
        }
    }
 
    public static int partition2(ArrayList<ReportEntry> report, int start, int end){
        ReportEntry pivot = report.get(end);
 
        for(int i=start; i<end; i++){
            LocalDate citaDate = dateToLocalDate(report.get(i).getFecha());
            
            LocalDate pivotDate = dateToLocalDate(pivot.getFecha());
            
            if(citaDate.isBefore(pivotDate)){
                ReportEntry temp= report.get(start);
                report.set(start, report.get(i));
                report.set(i, temp);
                start++;
            }
        }
 
        ReportEntry temp = report.get(start);
        report.set(start, pivot);
        report.set(end, temp);
 
        return start;
    }
    
    
    
    public static boolean fechaEnRango(Date testDate, Date startDate, Date endDate){
        LocalDate fechaInicio = dateToLocalDate(startDate);
        LocalDate fechaFinal = dateToLocalDate(endDate);
        LocalDate fecha = dateToLocalDate(testDate);
        if(fechaInicio.minusDays(1).isBefore(fecha) && fechaFinal.plusDays(1).isAfter(fecha)){
            return true;
        }
//        return !(testDate.before(startDate) || testDate.after(endDate));
        return false;
        
    }
    
    public static String formatDateToStringHour(Date hora){
        Instant instant = Instant.ofEpochMilli(hora.getTime());
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
        return time;
    }
    
    public static boolean actualizarAgenda(Agenda agenda){
         try{
            Agenda agendaExistente = getAgenda(agenda.getAgendaPK().getUsuario(), agenda.getAgendaPK().getId());
            agendaExistente.setDescripcion(agenda.getDescripcion());
            agendaExistente.setNombre(agenda.getNombre());
            agendaDAO.edit(agendaExistente);
            
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    public static boolean exiteAgendaNombre(String nombre, Usuario usuario){
        for(Agenda a: usuario.getAgendaList()){
            if(a.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean exiteCitaHora(Date hotaIni, Date horaFin, Agenda agenda){
        if(agenda.getAgendaCompartidaList().isEmpty()){
            return false;
        }else{
            for(Cita c: agenda.getCitaList()){
                if(horaEnRango(hotaIni, horaFin, c)){
                   return true; 
                }
            }
        }
        
        return false;
    }
    
    public static boolean horaEnRango(Date hotaIni, Date horaFin, Cita cita){
        LocalTime ini = dateToLocalTime(hotaIni);
        LocalTime fin = dateToLocalTime(horaFin);
        LocalTime citaIni = dateToLocalTime(cita.getHora());
        LocalTime citaFin = dateToLocalTime(cita.getHoraFinal());
        
        if(((citaIni.isAfter(ini) || citaIni.compareTo(ini)==0) && (citaIni.isBefore(fin) || citaIni.compareTo(fin)==0)) || 
               ((citaFin.isAfter(ini) || citaFin.compareTo(ini)==0) && (citaFin.isBefore(fin) || citaFin.compareTo(fin)==0)) ){
            return true;
            
        }
        
        return false;
    }
}
