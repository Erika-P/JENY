
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page import="fr.eni.encheres.bo.Article" %>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>


<form class="m-1 mt-5 mb-5" method="post" action="j_security_check">
    <%--@elvariable id="mustBeLogged" type="boolean"--%>
    <c:if test="${mustBeLogged}">
        <p class="text-center text-danger">Vous devez être connecté pour accéder à cette page.</p>
    </c:if>
    <div class="form-group row">
        <label class="col-4 offset-md-3 col-md-2 col-form-label" for="user_login">Identifiant</label>
        <div class="col-8 col-md-3 ">
            <input type="text" class="form-control" name="j_username" id="user_login" placeholder="Identifiant">
        </div>
    </div>
    <div class="form-group row">
        <label class="col-4 offset-md-3 col-md-2 col-form-label" for="user_password">Mot&nbsp;de&nbsp;passe</label>
        <div class="col-8 col-md-3">
            <input type="password" class="form-control" name="j_password" id="user_password" placeholder="Mot de passe">
        </div>
    </div>
    <%--@elvariable id="login_error" type="boolean"--%>
    <c:if test="${login_error}">
            <p class="text-danger text-center">Identifiant ou mot de passe incorrect</p>
    </c:if>
    <div class="form-group d-flex justify-content-center">
        <button type="submit" class="btn btn-info col-4 col-lg-2 offset-sm-1 text-center offset-1">Connexion</button>
        <div class="form-group form-check d-flex flex-column col-8 offset-md-1 col-md-3 pt-2 ml-2">
            <input type="checkbox" class="form-check-input" id="remember_login">
            <label class="form-check-label" for="remember_login">Se souvenir de moi</label>
            <a href="${pageContext.request.contextPath}/login-forgot">Mot de passe oublié</a>
        </div>
    </div>
</form>
<div class="row">
    <a class="offset-2 col-8 offset-md-4 col-md-3" href="${pageContext.request.contextPath}/createLogin">
        <button class="btn btn-info btn-md btn-block">Créer un compte</button>
    </a>
</div>

</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>
