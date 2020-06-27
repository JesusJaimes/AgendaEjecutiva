/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Agenda;
import Model.AgendaPK;
import Model.Cita;
import Model.Usuario;
import Negocio.AgendaEjecutiva;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class LeerFormularioCrearAgenda extends HttpServlet {

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
        String nombre = request.getParameter("nombre");
        Usuario usuario = AgendaEjecutiva.getUsuario(user);
        AgendaPK agendaPK = new AgendaPK(user);
        String descripcion = request.getParameter("descripcion");
        Date fecha = new Date(System.currentTimeMillis());
        int id = crearIdAgenda(usuario);
        Agenda agenda = new Agenda(nombre, descripcion, fecha, usuario, id);
        
        if(AgendaEjecutiva.exiteAgendaNombre(nombre, usuario)){
             response.sendRedirect("vistaCrearAgenda.jsp");
        }else if(AgendaEjecutiva.insertarAgenda(agenda)){
            usuario.getAgendaList().add(agenda);
            AgendaEjecutiva.actualizarUsuario(usuario);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("agenda", agenda.getAgendaPK().getId());
            request.getSession().setAttribute("agendas", usuario.agendasToHtmlFormat(agenda.getAgendaPK().getId()));
            response.sendRedirect("vistaPrincipal.jsp");
        }
    }
    
    public int crearIdAgenda(Usuario u){
        if(u.getAgendaList().isEmpty()){
            return 0;
        }else{
            return (u.getAgendaList().get((u.getAgendaList().size())-1).getAgendaPK().getId())+1;
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
