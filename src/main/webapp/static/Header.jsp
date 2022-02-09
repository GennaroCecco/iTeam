<%@ page import="it.unisa.agency_formation.autenticazione.domain.Utente" %>
<%@ page import="it.unisa.agency_formation.autenticazione.domain.RuoliUtenti" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Utente user = (Utente) request.getSession().getAttribute("user");
%>
<html>
<head>
    <c:choose>
        <c:when test="${param.sameLocation==true}">
            <link rel="stylesheet" href="../css/Header.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" href="css/Header.css">
        </c:otherwise>
    </c:choose>
    <title>Header</title>
</head>
<body>
<div class="header">
    <div class="logo">
        <c:choose>
            <c:when test="${param.sameLocation==true}">
                <img src="../img/Logo Team 4-5.png">
            </c:when>
            <c:otherwise>
                <img src="img/Logo Team 4-5.png">
            </c:otherwise>
        </c:choose>
    </div>
    <ul>
        <c:if test="${user == null}">
            <li><a href="">Home</a></li>
        </c:if>
        <c:if test="${(user!=null && user.getRole()==RuoliUtenti.CANDIDATO || user.getRole()==RuoliUtenti.DIPENDENTE ||
        user.getRole()==RuoliUtenti.TM || user.getRole()==RuoliUtenti.HR)}">
            <li> <c:choose>
            <c:when test="${param.sameLocation==true}">
                <a href="../LoginControl">Home</a></li>
            </c:when>
            <c:otherwise>
                <a href="./LoginControl">Home</a></li>
            </c:otherwise>
        </c:choose>
        </c:if>
    </ul>
    <div class="header-right">
        <ul>
            <c:if test="${user == null}">
                <li><a href="./static/Login.jsp">Accedi</a></li>
                <li><a href="./static/Registrazione.jsp">Registrati</a></li>
            </c:if>
            <c:choose>
                <c:when test="${user.getRole() == RuoliUtenti.CANDIDATO}">
                    <li>Candidato</li>
                </c:when>
                <c:when test="${user.getRole() == RuoliUtenti.DIPENDENTE}">
                    <li>Dipendente</li>
                </c:when>
                <c:when test="${user.getRole() == RuoliUtenti.TM}">
                    <li>TM</li>
                </c:when>
                <c:when test="${user.getRole() == RuoliUtenti.HR}">
                    <li>HR</li>
                </c:when>
            </c:choose>

            <c:if test="${user != null}">
                <c:choose>
                    <c:when test="${param.sameLocation==true}">
                        <li><a href="../LogoutControl">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="./LogoutControl">Logout</a></li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </ul>
    </div>
</div>
</body>
</html>