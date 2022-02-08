<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/HomePage.css">
    <link rel="icon" type="image/png" href="img/Logo Team 4-5.png"/>
    <title>HomePage</title>
</head>
<body>
<%@include file="/static/Header.jsp" %>
<div class="content-home">
    <h1>BENVENUTO IN AF CONSULTING</h1>
    <div class="wrap">
        <div class="text-block">
            AF Consulting ricerca nuovo personale!
            L'azienda fornisce ai propri dipendenti una formazione ed un orientamento di qualità,
            rendendo più facile per il nuovo dipendente adattarsi alle esigenze dell'azienda.<br>
            <a href="static/Registrazione.jsp">
                <button>Lavora con noi</button>
            </a>
        </div>
        <div class="image"></div>
    </div>
</div>
<%@include file="/static/Footer.jsp" %>
</body>
</html>