<%@ page import="it.unisa.agency_formation.FIA.DipendenteRefactor" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="it.unisa.agency_formation.FIA.TeamRefactor" %>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    DecimalFormat df = new DecimalFormat("###.##");
    TeamRefactor team =(TeamRefactor) request.getAttribute("team");
%>
<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/FIAntastici4.png"/>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Ottimizza.js"></script>
    <title>TeamAI</title>
</head>
<body>
<c:import url="/static/Header.jsp"/>
<div class="footer-wrap" id="body">
    <h1>Il miglior team elaborato dall'AI per: <b>${teamDB.getNomeTeam()}</b></h1>
    <div class="lista-dipendenti">
        <div class="team teamAI">
            <div class="team-inf">
                <div id="flex-team"><h2>Progetto: ${teamDB.getNomeProgetto()}</h2></div>
                <h4>La valutazione totale è:<br><%=df.format(team.getValoreTeam())%> su 5<br>
                    Team realizzato in:<br>${tempoEsecuzione} secondi</h4>
                <div id="ottimizza">
                <div class="ottimizza">
                    <a href="SalvaTeam?idTeam=${teamDB.getIdTeam()}&dip=${team.getDipendenti().get(0).getId()}&dip=${team.getDipendenti().get(1).getId()}&dip=${team.getDipendenti().get(2).getId()}&dip=${team.getDipendenti().get(3).getId()}">
                        <button onclick="loading()">Conferma il team</button>
                    </a>
                </div>
                <div class="ottimizza">
                    <h5>Non sei soddisfatto del team?</h5>
                    <a href="OttimizzaTeam?idTeam=${teamDB.getIdTeam()}">
                        <button onclick="ottimizza()">Riavvia l'AI</button>
                    </a>
                </div>
                </div>
                    <div id="logo-loader" style="display: none;"><img src="img/dots.gif"></div>

            </div>
            <div class="team-descr">
                <h2>Descrizione</h2>
                <div><h4>${teamDB.getDescrizione()}</h4></div>
            </div>
            <div class="team-dip">
                <h2>Dipendenti</h2>
                <div id="flex-team-dip">
                    <c:forEach var="dip" items="${team.getDipendenti()}">
                        <h4>ID: ${dip.getId()} ${dip.getNome()} ${dip.getCognome()}</h4>
                    </c:forEach>
                </div>
            </div>
            <div class="team-button">
                <h2>Competenze</h2>
                <c:forEach var="dip" items="${team.getDipendenti()}">
                    <div>
                    <h4>
                    <c:forEach var="skill" items="${dip.getSkills()}">
                        ${skill.getKey()}: ${skill.getValue()}
                    </c:forEach>
                    </h4>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<c:import url="/static/Footer.jsp"/>
</body>
</html>