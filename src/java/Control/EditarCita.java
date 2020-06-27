/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Agenda;
import Model.Cita;
import Model.CitaPK;
import Model.Usuario;
import Negocio.AgendaEjecutiva;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class EditarCita extends HttpServlet {

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
        int agenda = (int)request.getSession().getAttribute("agenda");
        Cita cita = (Cita)request.getSession().getAttribute("cita");
        Usuario usuario = AgendaEjecutiva.getUsuario(user);
        
        String asunto = request.getParameter("asunto-cita");
        String fechaString = request.getParameter("fecha-cita");
        String horaString = request.getParameter("hora-cita");
        String horaFinalString = request.getParameter("hora-final");
        String anotacion = request.getParameter("anotacion");
        
        cita.setAsunto(asunto);
        cita.setFecha(AgendaEjecutiva.localDateToDate(LocalDate.parse(fechaString)));
        cita.setHora(AgendaEjecutiva.localTimeToDate(LocalTime.parse(horaString)));
        cita.setHoraFinal(AgendaEjecutiva.localTimeToDate(LocalTime.parse(horaFinalString)));
        cita.setDescripcion(anotacion);
        
        if(AgendaEjecutiva.actualizarCita(cita, cita.getCitaPK())){
//            Agenda agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("agenda", agenda);
            request.getSession().setAttribute("agendas", usuario.agendasToHtmlFormat(agenda));
//            request.getSession().setAttribute("citas", agendaObj.listaCitasToHtmlFormat());
            response.sendRedirect("vistaPrincipal.jsp");
        }
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
