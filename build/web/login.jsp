<%-- 
    Document   : login
    Created on : May 23, 2020, 8:16:47 AM
    Author     : Romario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="css/login_style.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    </head>
    <body>
        <div class="contenido">
            <form action="leer_formulario_login.do" method="POST" id="form-iniciar-sesion">
                <h2 style="font-weight: bolder;">Iniciar sesión</h2>
                <h2 style="font-weight: bolder;">Agenda Ejecutiva</h2>
                <input type="text" placeholder="Ingresa Usuario" name="user" id="nombre" value="romariojaimes@gmail.com" required/>
                <br>
                <br>
                <input type="password" placeholder="Ingresa Contraseña" name="password" id="Contraseña" value="12345" required/>
                <br>
                <br>
                <button type="submit" id="button-login">Iniciar Sesión</button>
            </form>
            <br>
            <form action="ir_recuperar_contraseña.do" method="POST" id="form-ir-recuperar-contraseña">
                <button type="submit" id="button-ir-recuperar-contraseña">Olvide mi contraseña</button>
            </form>
            <p class="message">No estas registrado? <a href="vistaRegistroNuevoUsuario.jsp">Crear cuenta</a></p>
        </div>
    </body>
</html>
