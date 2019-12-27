<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
         "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*, login.Login" %>

<html>
  <head>

        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="styles/reset.css">
        <link rel="stylesheet" href="styles/source.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/login.js"></script>

      <title>singup</title>
  </head>
  <body>
    <div id= "singup_header">
       <div id = "links">
            <a href = 'http://localhost:8080/website/catalog'>Каталог</a>
            <a href = "http://localhost:8080/website/login">Войти</a>
            <a href = 'http://localhost:8080/website/singup'>Регистрация</a>
        </div>
    <div id = "singup_main" class = "contact_form">
      <form>
        <!-- Скрит для проверки введенных данных -->
        <ul>
          <h1> Регистрация </h1>
          <li> <label> Введите имя:</label>
          <input type="text" id="singup_name" onchange="greenBorder('singup_name')" required oninvalid="this.setCustomValidity('Необходимо ввести имя!')" oninput="setCustomValidity('')">
          </li>
          <li>
            <label>Введите фамилию</label>
            <input type="text" id="singup_surname" onchange="greenBorder('singup_surname')" required oninvalid="this.setCustomValidity('Необходимо ввести фамилию!')" oninput="setCustomValidity('')">
          </li>
          <li>
            <label> Введите логин:</label>
            <input type="text" id="singup_login" onchange="checkLogin()" required oninvalid="this.setCustomValidity('Необходимо ввести пользоветельское имя!')" oninput="setCustomValidity('')">
          </li>

          <li><!-- 
            <label id = "singup_error_log" style="display: none; color:red">Данный логин уже занят</label> -->
            <label>Введите пароль:</label>
            <input type="password" id="singup_password" onchange="checkPas()" minlength="5" required> <!-- 
            <label id = "singup_error_pas1" style="display: none; color:red"> пароли должны быть разными<br> </label>  -->
          </li>
          <li>
            <label>Повторите введеный пароль: </label>
            <input type="password" id="singup_password2" onchange="checkPas()" minlength="5" required><br>
          </li>
          <li>
            <label><input type="checkbox" id="singup_isSeller"> зарегистрироваться как продавец </label><br>
          </li>
          <li>
            <button class = "submit" onclick="loadDoc()">Зарегестрироваться</button>
          </li>      
            <label id = "singup_error_pas2" style="display: none; color:red"> введите пароль<br> </label>
        </ul>
      </form>
    </div>
  </body>
</html>