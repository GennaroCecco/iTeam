<%@ page import="it.unisa.agency_formation.reclutamento.domain.Candidatura" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Candidatura candidatura = (Candidatura) request.getAttribute("candidatura");

%>
<html>
<head>
    <link rel="icon" type="image/png" href="img/Logo Team 4-5.png"/>
    <link rel="stylesheet" href="css/Common.css">
    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="js/Reclutamento.js"></script>
    <meta charset="ISO-8859-1">
    <title>Caricamento</title>
</head>
<body>
<jsp:include page="/static/Header.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
<div class="footer-wrap">

    <h1>Caricamento Documenti</h1>
    <div class="content content-upload">
        <div class="text-block">
            Carica qui il tuo Curriculum, i tuoi attestati o le tue certificazioni, per farli revisionare da un HR.
            <br>
            <br>
            <c:if test="${candidatura==null}">
                <form action="UploadCandidatureControl" id="curriculum" method="post" enctype="multipart/form-data">
                    Curriculum<br><br>
                    <input type="file" id="fileCurriculum" name="curriculum" size="10" accept="application/pdf"><br>
                    <input type="hidden" id="sceltaCurriculum" name="sceltaUpload" value="1">
                    <input type="button" value="Carica" id="uploadCurriculum" onclick="checkFileCurriculum()">
                    <span id="curriculumNotUpload"></span>
                </form>
            </c:if>
            <c:if test="${candidatura.getCurriculum()!=null}">
                <form action="UploadCandidatureControl" id="documenti" method="post" enctype="multipart/form-data">
                    Documenti aggiuntivi<br><br>
                    <input type="file" id="fileDocumenti" name="documenti" size="10" accept="application/pdf"><br>
                    <input type="hidden" id="sceltaDocumenti" name="sceltaUpload" value="2">
                    <input type="button" value="Carica" id="uploadDocumenti" onclick="checkFileDocumenti()">
                    <span id="documentNotUpload"></span>
                </form>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/static/Footer.jsp">
    <jsp:param value="false" name="sameLocation"/>
</jsp:include>
</body>
</html>