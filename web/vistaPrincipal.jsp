<%-- 
    Document   : vistaPrincipal
    Created on : May 15, 2020, 3:13:14 PM
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TODO supply a title</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <!--my css-->
        <link href="css/vista_principal_style.css" rel="stylesheet" type="text/css"/>
        <!--my javascirpt-->
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
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
                    <a href="vista_perfil_usuario_normal.jsp" class="nav-link">
                        <svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="user-circle" class="svg-inline--fa fa-user-circle fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 496 512"><path fill="currentColor" d="M248 8C111 8 0 119 0 256s111 248 248 248 248-111 248-248S385 8 248 8zm0 96c48.6 0 88 39.4 88 88s-39.4 88-88 88-88-39.4-88-88 39.4-88 88-88zm0 344c-58.7 0-111.3-26.6-146.5-68.2 18.8-35.4 55.6-59.8 98.5-59.8 2.4 0 4.8.4 7.1 1.1 13 4.2 26.6 6.9 40.9 6.9 14.3 0 28-2.7 40.9-6.9 2.3-.7 4.7-1.1 7.1-1.1 42.9 0 79.7 24.4 98.5 59.8C359.3 421.4 306.7 448 248 448z"></path></svg> 
                    </a>
                </li>


                <li id="salir-button">
                    <a href="salir.do" class="nav-link">
                        <svg aria-hidden="true" focusable="false" data-prefix="fas" data-icon="sign-out-alt" class="svg-inline--fa fa-sign-out-alt fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path fill="currentColor" d="M497 273L329 441c-15 15-41 4.5-41-17v-96H152c-13.3 0-24-10.7-24-24v-96c0-13.3 10.7-24 24-24h136V88c0-21.4 25.9-32 41-17l168 168c9.3 9.4 9.3 24.6 0 34zM192 436v-40c0-6.6-5.4-12-12-12H96c-17.7 0-32-14.3-32-32V160c0-17.7 14.3-32 32-32h84c6.6 0 12-5.4 12-12V76c0-6.6-5.4-12-12-12H96c-53 0-96 43-96 96v192c0 53 43 96 96 96h84c6.6 0 12-5.4 12-12z"></path></svg>
                    </a>
                </li>
            </ul>
            
            <ul class="agendas">
                <li>
                    <a href="#"><span><i style="size: 5px"class="fas fa-book"></i>Agenda 1</span></a>
                </li>
                
                <li>
                    <a href="#"><span>Agenda 1</span></a>
                </li>
                
                <li>
                    <a href="#"><span>Agenda 1</span></a>
                </li>
                
                <li>
                    <a href="#"><span>Agenda 1</span></a>
                </li>
            </ul>
            
        </nav>
      
            
        <div class="contedio" >
            
            <div class="floating-button-placeholder">
                <a href="vistaRegistroCita.jsp" id="button-back" class="floating-button">
                    <i class="fas fa-plus"></i>
                </a>
            </div>
            
            <ul class="lista-citas">
                <li class="cita">
                    <div class="datos-cita">
                        <p><b>Fecha:</b> 20/05/2020 <b>Hora:</b> 3:30pm</p>
                        <p><b>Reunion con directivos</b></p>
                    </div>
                </li>
            </ul>
            
        </div>
    </body>
</html>

