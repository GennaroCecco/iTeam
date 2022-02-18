<%@ page import="it.unisa.agency_formation.team.domain.Team" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.Dipendente" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <script type="text/javascript" src="js/Ottimizza.js"></script>
    <title>Lista Team</title>
</head>
<body>
<div class="loading" id="logo-loader" style="display: none;"><img src="img/loading.gif"></div>
<c:import url="/static/Header.jsp"/>
<div class="footer-wrap">
    <h1>Team</h1>
    <c:choose>
        <c:when test="${listTeam!=null}">
            <c:set var="indexSkill" value="0" scope="page"/>
            <c:forEach var="team" items="${listTeam}">
                <div class="team">
                    <div class="team-inf">
                        <div id="flex-team"><h2>Team: ${team.getNomeTeam()}</h2></div>
                        <div id="flex-team"><h3>Progetto: ${team.getNomeProgetto()}</h3></div>
                        <div id="flex-team">Numero massimo dipendenti: ${team.getNumeroDipendenti()}</div>
                        <div id="flex-team-button" name="flexButton" style="display: inline-flex">
                            <c:set var="index" value="0" scope="page"/>
                            <c:forEach var="dip" items="${listDip}">
                                <c:if test="${dip.getTeam().getIdTeam() == team.getIdTeam()}">
                                    <c:set var="index" value="${index + 1}" scope="page"/>
                                </c:if>
                            </c:forEach>
                            <div name="drop-aggiungi">
                                <c:if test="${index < team.getNumeroDipendenti()}">
                                    <a href="AggiuntaDipendente?idTeam=${team.getIdTeam()}">
                                        <button> Aggiungi Dipendenti
                                        </button>
                                    </a>
                                    <br>
                                </c:if>
                            </div>
                            <div>
                                <button name="drop-sciogli"
                                        onclick="scioglimentoTeam(${indexSkill})">
                                    Sciogli Team
                                </button>
                            </div>
                        </div>
                        <div class="ottimizza">
                        <a href="OttimizzaTeam?idTeam=${team.getIdTeam()}">
                            <button onclick="ottimizza()">Ottimizza con la nostra AI</button>
                        </a>
                        </div>
                        <div class="confermaScioglimento" name="conferma-scioglimento" style="display: none">
                            <h3>Sicuro di voler sciogliere il team selezionato?</h3>
                            <button class="nega" onclick="scioglimentoTeam(${indexSkill})">No</button>
                            <a href="ScioglimentoTeamControl?idTeam=${team.getIdTeam()}">
                                <button>Si</button>
                            </a>
                        </div>
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
                                    <div id="flex-team">${dip.getName()} ${dip.getSurname()}
                                        <a href="RemoveTeamControl?idDip=${dip.getIdDipendente()}"><img
                                                src="img/Delete.png"></a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="team-button">
                        <h3>Competenze</h3>
                        <div id="flex-team-competenze">
                            <c:if test="${team.getCompetenza()==null}">
                                <button onclick="viewSpecifySkills(${indexSkill})" class="dropdown">Specifica Competenze
                                </button>
                            </c:if>
                            <div name="drop" style="display:none">
                                <form action="SpecificaCompetenzeControl" method="post"
                                      id="specificaCompetenze">
                                    <input type="hidden" name="action" value="competenze">
                                    <div class="skills-check">
                                        <div class="checkbox1">
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="HTML">HTML<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="C#">C#<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="C++">C++<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="C">C<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="Ruby">Ruby<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="Node">Node.js<br>
                                            <input type="checkbox" class="skill-checkbox" name="skill" value="React">React<br>
                                        </div>
                                        <div class="checkbox2">
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="Android">Android<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="javascript">JavaScript<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="Python">Python<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="CSS">CSS<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="Java">Java<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="PHP">PHP<br>
                                            <input type="checkbox" class="skill-checkbox1" name="skill" value="SQL">SQL<br>
                                        </div>
                                    </div>
                                    <input type="hidden" name="idTeam" value="${team.getIdTeam()}">
                                    <input type="submit" name="specifica" value="Salva" id="specifica"><br>
                                    <span id="specifyCompetence"></span>
                                </form>
                            </div>
                            <c:choose>
                                <c:when test="${team.getCompetenza() != null && team.getDocumento().getMaterialeDiFormazione() == null}">
                                    <div class="stato">
                                        <p>Hai specificato le competenze necessarie</p>
                                        <br>
                                        <b>${team.getCompetenza()}</b>
                                    </div>
                                </c:when>
                                <c:when test="${team.getCompetenza() != null && team.getDocumento().getMaterialeDiFormazione() != null}">
                                    <div class="stato">
                                        <p>L'HR ha caricato il materiale di formazione</p>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <c:set var="indexSkill" value="${indexSkill + 1}" scope="page"/>
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

<script>
    var limit = 4;
    var temp;
    $('input.skill-checkbox').on('change', function(evt) {
        if($('input.skill-checkbox').siblings(':checked').length +$('input.skill-checkbox1').siblings(':checked').length  >= limit) {
            this.checked = false;
        }
    });
    $('input.skill-checkbox1').on('change', function(evt) {
        if($('input.skill-checkbox').siblings(':checked').length +$('input.skill-checkbox1').siblings(':checked').length  >= limit) {
            this.checked = false;
        }
    });
</script>
</html>
