<%-- 
    Document   : vistaPrincipal
    Created on : May 15, 2020, 3:13:14 PM
    Author     : Romario
--%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.Instant"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="Model.Cita"%>
<%@page import="Model.Agenda"%>
<%@page import="Model.Usuario"%>
<%@page import="Negocio.AgendaEjecutiva"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%
    String user = (String)request.getSession().getAttribute("user");
    String agenda = (String)request.getSession().getAttribute("agenda");
    String agendas = (String)request.getSession().getAttribute("agendas");
    int idAgenda = (int)request.getSession().getAttribute("idAgenda");
    Agenda agendaObj = AgendaEjecutiva.getAgenda(user, idAgenda);
    String[] citasArray = agendaObj.listaCitasToHtmlFormat();
    String citas = citasArray[0];
    String citasRealizadas = citasArray[1];
    String citasPendientes = citasArray[2];
    //System.out.println("-------------------------"+agenda);
    //System.out.println("-------------------------"+agendaObj.getCitaList().size());
    request.getSession().setAttribute("user", user);
    request.getSession().setAttribute("agenda", agenda);
    request.getSession().setAttribute("agendas", agendas);
    request.getSession().setAttribute("idAgenda", idAgenda);
    
%>

<html>
    <head>
        <title>TODO supply a title</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/vista_principal_style.css" rel="stylesheet" type="text/css"/>
        <!--my javascirpt-->
        <script type="text/JavaScript" src="js/funcionesJS.js"></script>
        <!--google icons-->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <!--awesome icons-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.5.0/css/all.css' integrity='sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU' crossorigin='anonymous'>
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    
    <body>
        <nav class="navbar">
            <ul class="navbar-nav">
                
                <li>
                    <a href="#" class="nav-link">
                        <i class="fas fa-user-circle"></i>
                    </a>
                </li>


                <li id="salir-button">
                    <a href="salir.do" class="nav-link">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </li>
            </ul>
            
            <ul class="agendas">
                <div id="crear-agenda-div">
                   <a href="vistaCrearAgenda.jsp" id="crear-agenda-link"><button type="submit">Crear Agenda</button></a> 
                </div>
                <%=agendas%>
            </ul>
            
        </nav>  
      
        <div class="contedio" >
            
            <div class="floating-button-placeholder">
               
                <a id="button-menu" class="floating-button">
                    <i class="fas fa-bars"></i>
                </a>
             
                <div class="floating-options">

                    <div class="floating-option">
                        <a href="vistaRegistroCita.jsp" id="button-crear" class="floating-button">
                            <i class="fas fa-plus"></i>
                        </a>
                        <span class="fab-label">Crear cita</span>
                    </div>

                    <div class="floating-option">
                        <a href="vistaGenerarReporte.jsp" id="button-reporte" class="floating-button">
                             <i class="far fa-file-alt"></i>
                        </a>
                        <span class="fab-label">Generar reporte</span>
                    </div>
                    
                    <div class="floating-option">
                        <a href="ir_vista_editar_agenda.do" id="button-editar" class="floating-button">
                             <i class="fas fa-pen"></i>
                        </a>
                        <span class="fab-label">Editar esta agenda</span>
                    </div>

                    <div class="floating-option">
                        <a href="#" id="button-eliminar" class="floating-button">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                        <span class="fab-label">Eliminar esta agenda</span>
                    </div>

                </div>
            </div> 

            <div class="tab-citas">
                <button class="tablinks active" onclick="openSection(event, 'citas-futuras')">Proximas Citas</button>
                <button class="tablinks" onclick="openSection(event, 'citas-realizadas')">Citas Realizadas</button>
                <button class="tablinks" onclick="openSection(event, 'citas-pendientes')">Citas Pendientes</button>
            </div>
            
            <ul id="citas-futuras" class="lista-citas" style="display: flex">
                <%out.print(citas);%>
            </ul>
            
            <ul id="citas-realizadas" class="lista-citas" style="display: none">
                <%out.print(citasRealizadas);%>
            </ul>
            
            <ul id="citas-pendientes" class="lista-citas" style="display: none">
                <%out.print(citasPendientes);%>
            </ul>
            
            
            
        </div>
    </body>
</html>

