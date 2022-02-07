<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String error = request.getParameter("error");
%>
<c:set var="error" value="<%=error%>"/>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="../js/ConvalidazioneCampi.js"></script>
    <link rel="icon" type="image/png" href="../img/Logo Team 4-5.png"/>
    <link rel="stylesheet" href="../css/HomePage.css">
    <meta charset="ISO-8859-1">
    <title>Login</title>
</head>
<body class="background-login">
<div class="logo">
    <img src="../img/Logo Team 4-5.png">
</div>
<div class="login-block content">
    <h2>Login</h2>
    <form action="../LoginControl" method="post" id="formLogin">
        <div class="components">
            <input type="text" id="email" name="email" placeholder="Email" onkeyup="checkEmail()"><br>
            <span id="rsEmail"></span><br>
            <input type="password" id="password" name="password" placeholder="Password" onkeyup="checkPassword()"><br>
            <span id="rsPassword"></span>
        </div>
        <br>
        <c:if test="${error != null}">
            <span style="color: red">${error}</span>
        </c:if>
        <br>
        <div class="components-button">
            <input type="submit" value="Accedi" id="Accedi" onclick="checkEmail();checkPassword()">
        </div>
    </form>
    <div class="components">
        <a href="Error.jsp">Recupero Password</a>
    </div>
    <div class="back"><a href="http://localhost:8080/AgencyFormation/">Torna alla Home</a></div>
</div>
</body>
</html>