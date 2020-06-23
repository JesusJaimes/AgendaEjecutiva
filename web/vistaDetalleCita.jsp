<%-- 
    Document   : vistaDetalleCita
    Created on : 25-may-2020, 3:36:39
    Author     : Romario
--%>

<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Instant"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.Cita"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String user = (String)request.getSession().getAttribute("user");
    String agenda = (String)request.getSession().getAttribute("agenda");
    String agendas = (String)request.getSession().getAttribute("agendas");
    Cita cita = (Cita)request.getSession().getAttribute("cita");
    request.getSession().setAttribute("user", user);
    request.getSession().setAttribute("agenda", agenda);
    request.getSession().setAttribute("agendas", agendas);
    request.getSession().setAttribute("cita", cita);
    String asunto = cita.getAsunto();
    
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    String date = DATE_FORMAT.format(cita.getFecha());
    Instant instant = Instant.ofEpochMilli(cita.getHora().getTime());
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
    
    String descripcion = cita.getDescripcion();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/vista_detalle_cita_style.css" rel="stylesheet" type="text/css"/>
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
                    <a href="index.html" class="nav-link">
                        <i class="fas fa-sign-out-alt"></i>
                    </a>
                </li>
            </ul>
            
            <ul class="agendas">
                <!--div id="crear-agenda-div">
                   <a href="vistaCrearAgenda.jsp" id="crear-agenda-link"><button type="submit">Crear Agenda</button></a> 
                </div-->
                <%=agendas%>
            </ul>
            
        </nav>
        
            
        <div class="contedio" >
            
            <div class="floating-button-placeholder">
                <a href="vistaPrincipal.jsp" id="button-back" class="floating-button">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <span id="floating-lable">Volver</span>
            </div>
            
            <div class="card">
                <h2 style='word-wrap: break-word;'><%=asunto%></h2>
                <p>Fecha: <%=date%></p>
                <p>Hora: <%=time%></p>
                <p style='word-wrap: break-word;'><%=descripcion%></p>
                <%if(!cita.getCompletada()){%>
                    <form action='ir_editar_cita.do' method='POST'>
                        <button type="submit" id="button-editar">Editar</button>
                    </form>
                <%}%>
                
                
            </div>
            </ul>
            
        </div>
    </body>
</html>

