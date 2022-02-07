<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:choose>
        <c:when test="${param.sameLocation==true}">
            <link rel="stylesheet" href="../css/Footer.css">
        </c:when>
        <c:otherwise>
            <link rel="stylesheet" href="css/Footer.css">
        </c:otherwise>
    </c:choose>
    <meta charset="UTF-8">
    <title>Footer</title>
</head>
<body>
<div class="footer">
    <div class="footer-text">
        <h4>Progetto di <i>Ingegneria del Software</i> - Prof.ssa F. Ferrucci - Prof. F. Palomba</h4>
    </div>
    <div class="footer-icons">
        <a href="https://github.com/SystemFormation/AgencyFormation/tree/master">
            <c:choose>
                <c:when test="${param.sameLocation==true}">
                    <img src="../img/Github.png">
                </c:when>
                <c:otherwise>
                    <img src="img/Github.png">
                </c:otherwise>
            </c:choose>
        </a>
        <a href="https://www.instagram.com/agencyformation/">
            <c:choose>
                <c:when test="${param.sameLocation==true}">
                    <img src="../img/Instagram.png">
                </c:when>
                <c:otherwise>
                    <img src="img/Instagram.png">
                </c:otherwise>
            </c:choose>
        </a>
    </div>
</div>
</body>
</html>
