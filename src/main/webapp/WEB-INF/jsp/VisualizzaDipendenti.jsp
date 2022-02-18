<%@ page import="it.unisa.agency_formation.autenticazione.domain.Dipendente" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.StatiDipendenti" %>
<%@ page import="it.unisa.agency_formation.team.domain.Team" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    ArrayList<Dipendente> dip = (ArrayList<Dipendente>) request.getAttribute("dipendenti");
    ArrayList<Team> teams = (ArrayList<Team>) request.getAttribute("teams");
%>

<html>
<head>
    <link rel="stylesheet" href="css/Common.css">
    <link rel="icon" type="image/png" href="img/Logo Team 4-5.png"/>
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Formazione.js"></script>
    <title>Dipendenti</title>
</head>
<body>
<c:import url="/static/Header.jsp"/>
<div class="footer-wrap">
    <h1>Lista Dipendenti</h1>

    <div class="content">
        <div class="lista">
            <c:choose>
                <c:when test="${dipendenti!=null}">
                    <div id="flex-head">Dipendente</div>
                    <div id="flex-head">Team</div>
                    <div id="flex-head">Competenze</div>
                    <div id="flex-head">Stato</div>
                    <c:set var="index" value="0"/>
                    <c:forEach var="dip" items="${dipendenti}">
                        <div id="flex">${dip.getName()} ${dip.getSurname()}</div>
                        <div id="flex">
                            <c:choose>
                                <c:when test="${dip.getTeam().getNomeTeam() != null}">
                                    ${dip.getTeam().getNomeTeam()}
                                </c:when>
                                <c:otherwise>
                                    Nessuno
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div id="flex">
                            <c:choose>
                                <c:when test="${dip.getSkills() != null}">
                                    <button onclick="viewSkill(${index})" class="dropdown">
                                        Mostra skill
                                    </button>
                                    <div name="drop" class="skills" style="display: none">
                                        <c:set var="indexSkill" value="0"/>
                                        <c:forEach var="skill" items="${dip.getSkills()}">
                                            ${dip.getSkills().get(indexSkill).getNomeSkill()}
                                            <c:set var="indexSkill" value="${indexSkill + 1}"/>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div name="drop">
                                        Non sono presenti Skills
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:choose>
                            <c:when test="${dip.getStato() == StatiDipendenti.OCCUPATO}">
                                <div id="flex">
                                    <div id="stato-dipendente">Occupato</div>
                                    <div class="occupato">.</div>
                                </div>
                            </c:when>
                            <c:when test="${dip.getStato() == StatiDipendenti.DISPONIBILE}">
                                <div id="flex">
                                    <div id="stato-dipendente">Disponibile</div>
                                    <div class="disponibile">.</div>
                                </div>
                            </c:when>
                        </c:choose>
                        <c:set var="index" value="${index + 1}" scope="page"/>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h1> Non ci sono dipendenti</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<c:import url="/static/Footer.jsp"/>
</body>
</html>