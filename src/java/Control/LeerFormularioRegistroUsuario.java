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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Romario
 */
public class LeerFormularioRegistroUsuario extends HttpServlet {

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
        String nombre = request.getParameter("nombre");
        String cargo = request.getParameter("cargo");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Date fechaRegistro = new Date(System.currentTimeMillis());
        Usuario usuario = new Usuario(nombre, email, password, cargo, fechaRegistro);
        AgendaPK agendaPK = new AgendaPK(email);
        String descripcion = "Agenda generada automaticamente";
        Date fecha = new Date(System.currentTimeMillis());
        int id = crearIdAgenda();
        Agenda agenda = new Agenda("Mi agenda", descripcion, fecha, usuario, id);

        if(!email.endsWith("@ufps.edu.co")){
            response.sendRedirect("vistaCreacionUsuarioCorreoInvalido.jsp");
        }else if(AgendaEjecutiva.insertarUsuario(usuario) && AgendaEjecutiva.insertarAgenda(agenda)){
            response.sendRedirect("vistaCreacionUsuarioExitosa.jsp");
        }else{
            request.getRequestDispatcher("registroError.html").forward(request, response);
        }
    }
    
     public int crearIdAgenda(){
        if(AgendaEjecutiva.getAgendas().isEmpty()){
            return 0;
        }else{
            return (AgendaEjecutiva.getAgendas().get((AgendaEjecutiva.getAgendas().size())-1).getAgendaPK().getId())+1;
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
