<%-- 
    Document   : vistaEditarAgenda
    Created on : 26-jun-2020, 20:21:14
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Negocio.AgendaEjecutiva"%>
<%@page import="Model.Agenda"%>

<%
    String user = (String)request.getSession().getAttribute("user");
    String agenda = (String)request.getSession().getAttribute("agenda");
    String agendas = (String)request.getSession().getAttribute("agendas");
    Agenda agendaObj = (Agenda)request.getSession().getAttribute("agendaObj");
    request.getSession().setAttribute("user", user);
    request.getSession().setAttribute("agenda", agenda);
    request.getSession().setAttribute("agendas", agendas);
    
    String nombre = agendaObj.getAgendaPK().getNombre();
    String descripcion = agendaObj.getDescripcion();
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/vista_crear_agenda_style.css" rel="stylesheet" type="text/css"/>
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
                <a href="vistaPrincipal.jsp" id="button-back" class="floating-button">
                    <i class="fas fa-arrow-left"></i>
                </a>
                <span id="floating-lable">Volver</span>
            </div>
            
            <div class="card">
                <h2 style="font-weight: bolder;">Edite los datos de la Agenda</h2>
                <form  id='form-registrar-agenda' action='leer_formulario_editar_agenda.do' method='POST'>
                <label>Nombre</label>
                <input type='text' name='nombre' placeholder='Nombre' value='<%=nombre%>' required/>
                <label>Descripcion</label>
                <textarea name='descripcion' rows='4' cols='50' placeholder='Descripcion de la Agenda'><%=descripcion%></textarea>
                <button type='submit' id="button-registrar">Editar</button>
                </form>
            </div>
            
        </div>
    </body>
</html>
