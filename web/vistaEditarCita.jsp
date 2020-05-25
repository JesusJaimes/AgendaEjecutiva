<%-- 
    Document   : vistaEditarCita
    Created on : 25-may-2020, 4:15:14
    Author     : Romario
--%>

<%@page import="java.util.Date"%>
<%@page import="Model.Cita"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.LocalTime"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.Instant"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Negocio.AgendaEjecutiva"%>
<%@page import="java.time.LocalDate"%>
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
    
    String asunto = cita.getAsunto();
    
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
    
    String minDate = "'"+AgendaEjecutiva.getFechaStringYearMonthDay()+"'";
    String minHour = "'"+AgendaEjecutiva.getTimeStringHourMinute()+"'";
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/vista_registro_cita_style.css" rel="stylesheet" type="text/css"/>
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
                <h2 style="font-weight: bolder;">Modifique los datos la cita</h2>
                <form  id='form-registrar-cita' action='editar_cita.do' method='POST'>
                
                    <input type='text' name='asunto-cita' placeholder='Asunto' value='<%=asunto%>' required/>
                    <input type='date' name='fecha-cita' placeholder='Fecha de Fertilizante' value='<%=date%>'  required/>
                    <input type='time' name='hora-cita' placeholder='Hora' value='<%=time%>' required/>
                    <textarea name='anotacion' rows='4' cols='50' placeholder='Informacion adicional'><%=descripcion%></textarea>
                    <button type='submit' id="button-registrar">Editar</button>
                </form>
            </div>
            </ul>
            
        </div>
    </body>
</html>

