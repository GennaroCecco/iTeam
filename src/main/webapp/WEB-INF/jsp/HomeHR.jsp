<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo%20Team%204-5.png"/>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Reclutamento.js"></script>
    <script type="text/javascript" src="js/Formazione.js"></script>
    <title>Home</title>
</head>
<body>
<jsp:include page="/static/Header.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
<div class="footer-wrap">
    <h1>Bentornato ${user.getName()}</h1>
    <div class="content-home">
        <div class="flex">
            <div id="home">
                <h2> Lista Candidati </h2>
                <span id="noCandidati"></span>
                <p>Visualizza tutti i candidati che hanno proposto una candidatura.</p>
                <a href="ListaCandidati">
                    <button>Accedi all'area</button>
                </a>
            </div>
            <div id="home">
                <h2> Lista Teams </h2>
                <p>Visualizza tutti i team presenti nell'azienda.</p>
                <a href="ListaTeam">
                    <button>Accedi all'area</button>
                </a>
            </div>
            <div id="home">
                <h2> Lista Colloqui </h2>
                <p>Visualizza tutti i candidati che hanno svolto il colloquio.</p>
                <a href="ListaColloqui">
                    <button>Accedi all'area</button>
                </a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/static/Footer.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
</body>
</html>
