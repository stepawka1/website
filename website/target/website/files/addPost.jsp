<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
         "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*,catalog.*,javax.servlet.*" %>
<html>
  <head>
<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="styles/reset.css">
        <link rel="stylesheet" href="styles/source.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/login.js"></script>

      <title>Новый товар</title>
  </head>
  <body>
    <form action="addproduct" method="post" class="contact_form" enctype="multipart/form-data">
      <ul>
          <li>
               <h2>Добавить товар</h2>
          </li>
          <li>
              <label   required>Название товара:</label>
              <input type="text" name="name" required />
          </li>
          <li>
              <label for="email">Количество:</label>
              <input type="text" name="quanity" required pattern="^[ 0-9]+$" />
          </li>
          <li>
              <label for="website">Цена:</label>
              <input type="text" name="price" required pattern="^[ 0-9]+$"/>
              
          </li>
          <li>
              <label for="message">Описание: </label>
              <textarea name="decript" cols="40" rows="6"  required > </textarea>
          </li>
          <li>
              <label for="message">Тэги: </label>
              <textarea name="tags" cols="40" rows="3"  required > </textarea>
          </li>
          <li>
            <label>Картинка:</label>
            <!-- id= "form_picture" -->
            <input name="picture" type="file" />
          </li>
          <li>
              <button class="submit" type="submit">Добавить</button>
          </li>

      </ul>
    </form>

  </body>
</html>