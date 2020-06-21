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
    Agenda agendaObj = AgendaEjecutiva.getAgenda(user, agenda);
    String citas = agendaObj.listaCitasToHtmlFormat();
    System.out.println("-------------------------"+agenda);
    System.out.println("-------------------------"+agendaObj.getCitaList().size());
    request.getSession().setAttribute("user", user);
    request.getSession().setAttribute("agenda", agenda);
    request.getSession().setAttribute("agendas", agendas);
    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
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
                <%=agendas%>
            </ul>
            
        </nav>
      
            
        <div class="contedio" >
            
            <div class="floating-button-placeholder">
                <a href="vistaRegistroCita.jsp" id="button-plus" class="floating-button">
                    <i class="fas fa-plus"></i>
                </a>
                <span id="floating-lable">Crear Cita</span>
            </div>
            
            <ul class="lista-citas">
                <%out.print(citas);%>
            </ul>
            
        </div>
    </body>
</html>

