<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page import="it.unisa.agency_formation.reclutamento.domain.Candidatura" %>
<%@ page import="it.unisa.agency_formation.reclutamento.domain.StatiCandidatura" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Candidatura candidatura = (Candidatura) request.getAttribute("candidatura");
%>
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
            <c:choose>
                <c:when test="${candidatura.getStato() != StatiCandidatura.Accettata && candidatura.getStato() != StatiCandidatura.Assunzione}">
                    <c:if test="${candidatura==null||candidatura.getDocumentiAggiuntivi()==null}">
                        <div id="home">
                            <h2> Caricamento Documenti </h2>
                            <p> Carica il tuo curriculum e in seguito anche i documenti, per proporre la tua
                                candidatura </p>
                            <a href="UploadCandidatureControl">
                                <button>Accedi all'area</button>
                            </a>
                        </div>
                    </c:if>
                </c:when>
                <c:when test="${candidatura.getStato() == StatiCandidatura.Accettata}">
                    <div id="home">
                        <h2> Data e ora colloquio:</h2>
                        <p> ${candidatura.getDataOraColloquio()} </p>
                    </div>
                </c:when>
            </c:choose>
            <c:if test="${candidatura!=null && candidatura.getCurriculum()!=null}">
                <div id="stato">
                    <c:choose>
                        <c:when test="${candidatura.getStato() == StatiCandidatura.Accettata}">
                            <h2>Stato della tua candidatura:</h2>
                            <p id="statoCandidatura">Accettata</p>
                            <div class="disponibile">.</div>
                        </c:when>
                        <c:when test="${candidatura.getStato() == StatiCandidatura.Rifiutata}">
                            <h2>Stato della tua candidatura:</h2>
                            <p id="statoCandidatura">Rifiutata</p>
                            <div class="occupato">.</div>
                            <div id="home">
                                <h2> Ricandidatura </h2>
                                <p>Ricandidati caricando il tuo curriculum e in seguito anche i documenti</p>
                                <a href="RicandidaturaControl">
                                    <button>Accedi all'area</button>
                                </a>
                            </div>
                        </c:when>
                        <c:when test="${candidatura.getStato() == StatiCandidatura.NonRevisionato}">
                            <div id="home">
                                <h2>I tuoi documenti saranno revisionati a breve, controlla qui lo stato della tua
                                    candiadtura:</h2>
                                <p id="statoCandidatura">Non Revisionato</p>
                                <div class="revisione">.</div>
                                <div class="controlloCaricamento">
                                    <h3>Curriculum caricato</h3>
                                    <c:choose>
                                        <c:when test="${candidatura.getDocumentiAggiuntivi()!=null}">
                                            <h3>Documenti e Certificazioni caricati</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Ora hai la possibilità di caricare Documenti aggiuntivi</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:when>
                        <c:when test="${candidatura.getStato() == StatiCandidatura.Assunzione}">
                            <h1>Compila questi campi per completare la tua assunzione</h1>
                            <div class="form">
                                <form method="post" id="formDipendente" action="CandidatoAssuntoControl">
                                    <input type="hidden" name="action" value="crea">
                                    <label for="formDipendente">Anno di nascita:</label><br>
                                    <input type="number" min="1950" max="2004" id="annoDipendente" name="annoDipendente"
                                           placeholder="Anno" required><br>
                                    <label for="formDipendente">Residenza:</label><br>
                                    <input type="text" id="residenzaDipendente" name="residenzaDipendente"
                                           placeholder="Residenza" required><br>
                                    <label for="formDipendente">Recapito Telefonico:</label><br>
                                    <input type="number" id="telefonoDipendente" name="telefonoDipendente"
                                           placeholder="Telefono" required
                                           pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}}"><br><br>
                                    <input type="submit" name="invia" value="Invia" id="invia">
                                </form>
                            </div>
                        </c:when>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/static/Footer.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
</body>
</html>