/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Agenda;
import Model.Cita;
import Negocio.AgendaEjecutiva;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class LeerFormularioGenerarReporte extends HttpServlet {

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
        String agendas = (String)request.getSession().getAttribute("agendas");
        int agenda = (int)request.getSession().getAttribute("agenda");
        
        Agenda agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
        
        String fechaDesdeString = request.getParameter("fecha-desde");
        String fechaHastaString = request.getParameter("fecha-hasta");
        
        Date fechaDesde = AgendaEjecutiva.localDateToDate(LocalDate.parse(fechaDesdeString));
        Date fechaHasta = AgendaEjecutiva.localDateToDate(LocalDate.parse(fechaHastaString));
        
        String reporte = AgendaEjecutiva.obtenerTablaHtmlReporte(agendaObj.getCitaList(), fechaDesde, fechaHasta);
        
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("agenda", agenda);
        request.getSession().setAttribute("agendas", agendas);
        request.getSession().setAttribute("reporte", reporte);
        response.sendRedirect("vistaReporte.jsp");
        
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
