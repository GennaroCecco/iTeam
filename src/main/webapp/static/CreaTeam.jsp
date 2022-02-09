<%@ page import="it.unisa.agency_formation.autenticazione.domain.Dipendente" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <script type="text/javascript" src="../js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="../js/ConvalidazioneCampi.js"></script>
    <link rel="stylesheet" href="../css/Common.css">
    <link rel="icon" type="image/png" href="../img/Logo Team 4-5.png"/>
    <title>Creazione Team</title>
</head>
<body>
<jsp:include page="/static/Header.jsp">
    <jsp:param value="true" name="sameLocation"/>
</jsp:include>
<div class="footer-wrap">
    <h1>Creazione Team</h1>
    <div class="content-createTeam">
        <div class="content">
            <div class="form">
                <form action="../CreateTeamControl" method="post" id="formTeam">
                    <label for="formTeam">Nome del Team</label><br>
                    <input type="text" id="fname" name="fname" placeholder="Team" onblur="checkTeam()"
                           onkeyup="checkTeam()"><br>
                    <span id="rsTeam"></span><br>
                    <label for="formTeam">Nome del Progetto</label><br>
                    <input type="text" id="lname" name="lname" placeholder="Progetto" onblur="checkProject()"
                           onkeyup="checkProject()"><br>
                    <span id="rsProject"></span><br>
                    <label for="quantity">Numero Dipendenti</label><br>
                    <input type="number" id="quantity" name="quantity" min="1" max="20" onblur="checkMaxDip()"
                           onkeyup="checkMaxDip()"><br>
                    <span id="rsMaxDip"></span><br>
                    <label for="teamDescr">Descrizione</label><br>
                    <textarea id="teamDescr" name="teamDescr" placeholder="Descrizione del Team"
                              onblur="checkTeamDescr()" onkeyup="checkTeamDescr()"></textarea><br>
                    <span id="rsDescr"></span><br>
                    <input type="button" name="crea" value="Crea" id="crea"
                           onclick="checkTeamSubmit()"><br>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/static/Footer.jsp">
    <jsp:param value="true" name="sameLocation"/>
</jsp:include>
</body>
</html>