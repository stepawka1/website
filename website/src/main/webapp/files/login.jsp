<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
         "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*,com.example.*" %>
<html>
  <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="styles/reset.css">
        <link rel="stylesheet" href="styles/source.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/login.js"></script>

      <title>login</title>
  </head>
  <body>

    <div id= "login_header">
        <div id = "links">
            <a href = 'http://localhost:8080/website/catalog'>Каталог</a>
            <a href = "http://localhost:8080/website/login">Войти</a>
            <a href = 'http://localhost:8080/website/singup'>Регистрация</a>
        </div>
    <div id = "login_main">
        <h2> Login: </h2>
        <input type="text" id="login_name"><br>
        <h2> Password: </h2>
        <input type="password" id="login_password"><br>
        <h4 id = "login_error" style="display: none; color:red">логин или пароль не верны <br></h4>
        <button type="button" onclick="loginSystem()">войти</button>
    </div>
  </body>
</html>