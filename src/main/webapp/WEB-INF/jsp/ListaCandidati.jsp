<%@ page import="it.unisa.agency_formation.autenticazione.domain.Utente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    ArrayList<Utente> candidati = (ArrayList<Utente>) request.getAttribute("candidati");
%>
<html>
<head>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Candidature.js"></script>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo Team 4-5.png"/>
    <title>Candidati</title>
</head>
<body>
<c:import url="/static/Header.jsp"/>
<div class="footer-wrap">
    <h1>Lista Candidati</h1>
    <div class="content">
        <div class="liste">
            <c:choose>
                <c:when test="${candidati!=null}">
                    <div id="flex-head">ID</div>
                    <div id="flex-head">Nome</div>
                    <div id="flex-head">Cognome</div>
                    <div id="flex-head">Azione</div>
                    <c:set var="index" value="0" scope="page"/>
                    <c:forEach var="cand" items="${candidati}">
                        <div id="flex">${cand.getId()}</div>
                        <div id="flex">${cand.getName()}</div>
                        <div id="flex">${cand.getSurname()}</div>
                        <div id="flex">
                            <button onclick="checkFile(${index});viewFile(${cand.getId()},${index})">Mostra file
                            </button>
                            <div class="dropdown">
                                <button id="apriColloquio" onclick="openColloquio(${index})">Accetta</button>
                            </div>
                            <div class="colloquio" name="colloquio" style="display: none">
                                <h5>Inserisci la data e l'ora del colloquio</h5>
                                <input type="date" id="data1" name="data1"><br>
                                <input type="time" name="time" id="time"><br>
                                <button type="button" id="chiudiColloquio" class="buttonChiudi"
                                        onclick="closeColloquio(${index})">
                                    Chiudi
                                </button>
                                <button onclick="acceptCandidature(${cand.getId()},${index})">Accetta</button>
                                <br><span name="errore"></span><br>
                            </div>
                            <button onclick="rejectCandidature(${cand.getId()})">Rifiuta</button>
                        </div>
                        <br>

                        <div name="drop" class="dropdown-content" style="display:none">
                            <a href="DownloadControl?toDownload=curriculum&idCandidato=${cand.getId()}"
                               name="hrefCurriculum">
                                <img src="img/Curriculum.png">
                                <p>Curriculum</p>
                            </a>
                            <a href="DownloadControl?toDownload=documenti&idCandidato=${cand.getId()}"
                               name="hrefDocumenti">
                                <img src="img/Documenti.png">
                                <p>Documenti</p>
                            </a>
                        </div>
                        <hr>
                        <c:set var="index" value="${index + 1}" scope="page"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h1>Non ci sono candidati</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="/static/Footer.jsp"/>
</body>
</html>