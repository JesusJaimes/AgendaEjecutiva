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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class EliminarAgenda extends HttpServlet {

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
        Usuario usuario = AgendaEjecutiva.getUsuario(user);
        Agenda agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
        if(usuario.getAgendaList().size()>1 && AgendaEjecutiva.eliminarAgenda(agendaObj)){
//            Agenda agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
            usuario.getAgendaList().remove(agendaObj);
            AgendaEjecutiva.actualizarUsuario(usuario);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("agenda", usuario.getAgendaList().get(0).getAgendaPK().getId());
            request.getSession().setAttribute("agendas", usuario.agendasToHtmlFormat(usuario.getAgendaList().get(0).getAgendaPK().getId()));
//            request.getSession().setAttribute("citas", agendaObj.listaCitasToHtmlFormat());
            response.sendRedirect("vistaPrincipal.jsp");
        }else{
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
