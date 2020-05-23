<%-- 
    Document   : vistaRegistroNuevoUsuario
    Created on : May 20, 2020, 9:40:21 PM
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script type="text/JavaScript" src="js/funcionalidad.js"></script>
        <link href="css/estilo_registro.css" rel="stylesheet" type="text/css">
        <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'>
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_registro.do" method="POST" id="form-registro-usuario">
                <h3>Formulario de registro</h3>
                <input type="text" placeholder="Nombre" name="nombre" required/>
                <br>
                <br>
                <input type="email" placeholder="Email" name="email" required/>
                <br>
                <br>
                <input type="password" placeholder="Contraseña" name="password" id="password" required/>
                <br>
                <br>
                <input type="password"  oninput="validarContraseña()" placeholder="Repetir Contraseña" name="password-repetida" id="password-repetida"  required/>
                <br>
                <br>
                <button type="submit" id="button-regitrar">Registrarme</button>
            </form>
            <br>
            <a href="index.html" id="link-ir-login"><button id="button-regitrar">Volver al Login</button></a>
        </div>
        
    </body>
</html>
