<%@ page import="it.unisa.agency_formation.team.domain.Team" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.Dipendente" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page import="it.unisa.agency_formation.formazione.domain.Documento" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    ArrayList<Team> listTeam = (ArrayList<Team>) request.getAttribute("listTeam");
    ArrayList<Dipendente> listDip = (ArrayList<Dipendente>) request.getAttribute("listDip");
%>

<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo%20Team%204-5.png"/>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Formazione.js"></script>
    <title>Lista Team</title>
</head>
<body>
<c:import url="/static/Header.jsp"/>
<div class="footer-wrap">
    <h1>Team</h1>
    <c:choose>
        <c:when test="${listTeam!=null}">
            <c:set var="index" value="0"/>
            <c:forEach var="team" items="${listTeam}">
                <div class="team">
                    <div class="team-inf">
                        <div id="flex-team"><h2>Team: ${team.getNomeTeam()}</h2></div>
                        <div id="flex-team"><h3>Progetto: ${team.getNomeProgetto()}</h3></div>
                        <div id="flex-team">Numero massimo dipendenti: ${team.getNumeroDipendenti()}</div>
                    </div>
                    <div class="team-descr">
                        <h3>Descrizione</h3>
                        <div id="flex-team">${team.getDescrizione()}</div>
                    </div>
                    <div class="team-dip">
                        <h3>Dipendenti</h3>
                        <div id="flex-team-dip">
                            <c:forEach var="dip" items="${listDip}">
                                <c:if test="${dip.getTeam().getIdTeam() == team.getIdTeam()}">
                                    <div>${dip.getName()} ${dip.getSurname()}</div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="team-button">
                        <h3>Competenze</h3>
                        <div id="flex-team-competenze">
                            <c:if test="${team.getCompetenza() != null}">
                                <button name="drop-button" style="display: inline" onclick="viewCompetenze(${index})"
                                        class="dropdown">Visualizza Competenze
                                </button>
                                <div name="drop" class="skills" style=" display: none">
                                        ${team.getCompetenza()}
                                    <br>
                                </div>
                                <br>
                                <button name="dropButton" onclick="viewCaricamento(${index})">
                                    Carica Materiale
                                </button>
                                <c:choose>
                                    <c:when test="${team.getDocumento().getMaterialeDiFormazione() == null}">
                                        <div name="drop-carica" style="display: none">
                                            <form action="UploadMaterialeControl" id="materiale" method="post"
                                                  name="formUpload"
                                                  enctype="multipart/form-data">
                                                <p class="par">Materiale di Formazione</p><br>
                                                <input type="file" id="fileMateriale" name="materiale" size="50"><br>
                                                <input type="hidden" id="sceltaDocumenti" name="idTeam"
                                                       value="${team.getIdTeam()}">
                                                <input type="button" value="Carica" name="uploadMateriale"
                                                       onclick="checkFileMateriale(${index})">
                                                <span name="materialeNotUpload"></span>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div name="drop-carica" style="display: none">
                                            <div name="materiale">
                                                <div name="uploadMateriale">
                                                    <p>Hai già caricato il materiale di formazione</p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </div>
                    </div>
                </div>
                <c:set var="index" value="${index + 1}" scope="page"/>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="content">
                <h1>Non ci sono Team</h1>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<c:import url="/static/Footer.jsp"/>
</body>
</html>