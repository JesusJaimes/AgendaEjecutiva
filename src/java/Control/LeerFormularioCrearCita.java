/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Agenda;
import Model.Cita;
import Model.CitaPK;
import Negocio.AgendaEjecutiva;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class LeerFormularioCrearCita extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = (String)request.getSession().getAttribute("user");
        String agenda = (String)request.getSession().getAttribute("agenda");
        String agendas = (String)request.getSession().getAttribute("agendas");
        String asunto = request.getParameter("asunto-cita");
        String fechaString = request.getParameter("fecha-cita");
        String horaString = request.getParameter("hora-cita");
        String anotacion = request.getParameter("anotacion");
        
        Agenda agendaObj = (new AgendaEjecutiva()).getAgenda(user, agenda);
        Cita cita = crearCita(fechaString, horaString, asunto, anotacion, user, agendaObj);
       
        
        if(AgendaEjecutiva.insertarCita(cita)){
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("agenda", agenda);
            request.getSession().setAttribute("agendas", agendas);
//            agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
//            request.getSession().setAttribute("citas", agendaObj.listaCitasToHtmlFormat());
            request.getRequestDispatcher("vistaPrincipal.jsp").forward(request, response);

//            response.sendRedirect("vistaPrincipal.jsp");
        }
        
    }
    
    public long crearIdCita(){
        LocalDate idDate = LocalDate.now();
        LocalTime idTime = LocalTime.now();
        long id = Long.parseLong(idTime.getSecond()+""+idTime.getMinute()+""+idTime.getHour()
                +""+idDate.getDayOfMonth()+""+idDate.getMonthValue()+""+idDate.getYear());
        return id;
    }
    
    public Cita crearCita(String fechaString, String horaString, String asunto, String anotacion, String user, Agenda agenda){
        
        Date fecha = AgendaEjecutiva.localDateToDate(LocalDate.parse(fechaString));
        Date hora = AgendaEjecutiva.localTimeToDate(LocalTime.parse(horaString));
        
        CitaPK citaPK = new CitaPK(user, agenda.getAgendaPK().getNombre(), crearIdCita());
        Cita cita = new Cita(citaPK, asunto, anotacion, hora, fecha, false);
        return cita;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
