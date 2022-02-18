<%@ page import="it.unisa.agency_formation.FIA.DipendenteRefactor" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo%20Team%204-5.png"/>
    <title>TeamAI</title>
</head>
<body>
<c:import url="/static/Header.jsp"/>
<c:forEach var="dip" items="${team.getDipendenti()}">
    <c:set var="sum" value="0" scope="page"/>
    <c:forEach var="skill" items="${dip.getSkills()}">
        <c:set var="sum" value="${sum += sum}" scope="page"/>
    </c:forEach>
</c:forEach>
<div class="footer-wrap">
    <h1>Il miglior team elaborato dall'AI per: <b>${teamDB.getNomeTeam()}</b></h1>
    <div class="lista-dipendenti">
        <div class="team">
            <div class="team-inf">
                <div id="flex-team"><h2>Progetto: ${teamDB.getNomeProgetto()}</h2></div>
                <h4>La valutazione totale è:<br>${team.getValoreTeam()}<br><br>
                    Realizzata in un tempo totale di:<br>${tempoEsecuzione} secondi</h4>
                <div class="ottimizza"><a href=""><button>Conferma il team</button></a></div>
                <div class="ottimizza">
                    <a href="OttimizzaTeam?idTeam=${teamDB.getIdTeam()}">
                        <button>Non ti piace il team? Riavvia l'AI</button>
                    </a>
                </div>
            </div>
            <div class="team-descr">
                <h2>Descrizione</h2>
                <div><h4>${teamDB.getDescrizione()}</h4></div>
            </div>
            <div class="team-dip">
                <h2>Dipendenti</h2>
                <div id="flex-team-dip">
                    <c:forEach var="dip" items="${team.getDipendenti()}">
                        <h4>${dip.getId()} ${dip.getNome()} ${dip.getCognome()}</h4>
                    </c:forEach>
                </div>
            </div>
            <div class="team-button">
                <h2>Competenze</h2>
                <c:forEach var="dip" items="${team.getDipendenti()}">
                    <c:forEach var="skill" items="${dip.getSkills()}">
                        <h4>${skill.getKey()}: ${skill.getValue()}</h4>
                    </c:forEach>
                    <br>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<c:import url="/static/Footer.jsp"/>
</body>
</html>