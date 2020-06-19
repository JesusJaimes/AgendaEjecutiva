/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.AgendaJpaController;
import DAO.CitaJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import Model.Agenda;
import Model.AgendaPK;
import Model.Cita;
import Model.CitaPK;
import Model.Usuario;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public static boolean insertarAgenda(Agenda agenda){
         try{
            agendaDAO.create(agenda);
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("false");
            return false;
        }
    }
    
    public static List<Cita> getCitasAgenda(Agenda agenda){
        return agenda.getCitaList();
    }
    
    public Agenda getAgenda(String usuario, String agenda){
        return agendaDAO.findAgenda(new AgendaPK(agenda, usuario));
    }
    
    
    public static boolean insertarCita(Cita cita){
         try{
            System.out.println("SE VA A INTENTAR INSERTAR CITA");
            citaDAO.create(cita);
            System.out.println("SE LOGRO INSERTAR CITA");
            System.out.println("true");
            return true;
            
        }catch(Exception e){
            System.out.println("SE FALLO INSERTAR CITA");
            System.out.println("false");
            return false;
        }
    }
    
    public static Date localDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
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
    
    public static boolean actualizarCita(Cita cita, CitaPK citaId){
        try{
            Cita citaExistente = getCita(citaId);
            citaExistente.setAsunto(cita.getAsunto());
            citaExistente.setFecha(cita.getFecha());
            citaExistente.setHora(cita.getHora());
            citaExistente.setDescripcion(cita.getDescripcion());
            citaDAO.edit(citaExistente);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
