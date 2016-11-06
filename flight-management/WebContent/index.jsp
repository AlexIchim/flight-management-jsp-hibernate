<%--
  Created by IntelliJ IDEA.
  User: Alex PC
  Date: 18/10/2016
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%
    String loginError = (String)  session.getAttribute("login_message");
    session.removeAttribute("user");
    session.removeAttribute("login_message");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="style.css">
    <title>Login Page</title>
</head>
<body>
<div class="login-page">
  <div class="form">
    <p class="error">
        <%
            if (loginError != null) { %>
               <%= loginError %>
           <% }
        %>
    </p>
    <div id="wrapper">
        <h1>Login</h1>
        <form class="login-form" id="loginForm" action="LoginServlet" method="post" >
            <label for="username">
                Login:
            </label>
            <input type="text" name="username" id="username" placeholder="username" required />
            <br><br>

            <label for="password">
                Password:
            </label>
            <input type="password" id="password" name="password" placeholder="password" required />
            <br><br>

            <div id="buttons">
                <input type="submit" value="Login" class="button">
                <input type="reset" value="Clear" class="button">
            </div>
        </form>
    </div>
    </div>
    </div>
</body>
</html>