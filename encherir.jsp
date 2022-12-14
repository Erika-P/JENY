<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Locale"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="fr.eni.encheres.bo.Enchere"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page import="fr.eni.encheres.bo.Article" %>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html  lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">
<title>Détail vente</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>

      <div class="head">
      <h1 class="text-center mb-2">Détail Vente</h1>
      </div>
    <% Utilisateur connectedUser = (Utilisateur) session.getAttribute("ConnectedUser"); %>
	<% Article article = (Article)request.getAttribute("ArticleAffiche"); %>
    <div class="container-vente">
        <div class="card-img-container">
            <img src="img/tournevis.jpeg" alt="">
        </div>
<c:choose>
    <c:otherwise>
        <c:choose>
            <c:when test="${utilisateurSession.pseudo == user_best_offer}">
                <h3 class="text-center mb-2">Vous avez remporté la vente !</h3>
            </c:when>
            <c:otherwise>
                <h3 class="text-center mb-2">${user_best_offer} a remporté l'enchère</h3>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<h4 class="text-center m-4"><jsp:getProperty name="auction" property="nomArticle"/></h4>
<div class="row">
    <table class="table offset-1 col-10 offset-md-4 col-md-4">
        <tr>
            <th scope="row">Description&nbsp;:</th>
            <td><jsp:getProperty name="auction" property="description"/></td>
        </tr>
        <tr>
            <th scope="row">Catégorie&nbsp;: </th>
            <td>${category.libelle}</td>
        </tr>
        <c:if test="${!empty(user_best_offer)}">
            <tr>
                <th scope="row">Meilleure offre&nbsp;:</th>
                <td>${auction.prixVente} par ${user_best_offer}</td>
            </tr>
        </c:if>
        <tr>
            <th scope="row">Mise à prix&nbsp;:</th>
            <td><jsp:getProperty name="auction" property="prixInitial"/></td>
        </tr>
        <tr>
            <th scope="row">Fin de l'enchère&nbsp;:</th>
            <td><fmt:formatDate value="${auction.dateFinEncheres}" type="date" pattern="dd/MM/YYYY"/></td>
        </tr>
        <tr>
            <th scope="row">Retrait&nbsp;:</th>
            <td>${withdrawal.rue}<br/>${withdrawal.codePostal} ${withdrawal.ville}</td>
        </tr>
        <tr>
            <th scope="row">Vendeur&nbsp;:</th>
            <td>${seller.pseudo}</td>
        </tr>
        <c:if test="${auction.etatVente == 'EC' && utilisateurSession.noUtilisateur != auction.noUtilisateur}">
            <tr>
                <th scope="row" class="align-middle"><label for="myAuction">Ma&nbsp;proposition&nbsp;:</label></th>
                <td>
                    <form class="form-inline row" method="post" action="${pageContext.request.contextPath}/raiseAuction?id=${auction.noArticle}">
                        <input  required class="form-control offset-1 col-4" id="myAuction" type="number" value="${auction.prixVente + 1}" min="${auction.prixVente + 1}" step="1" name="myAuction">
                        <input type="submit" class="col-5 btn btn-success ml-3" value="Enchérir">
                    </form>
                </td>
            </tr>
        </c:if>
    </table>
</div>
<p class="text-center">
    <c:if test="${utilisateurSession.noUtilisateur == auction.noUtilisateur && auction.etatVente == 'PC'}">
        <a href="updateAuction?id=${auction.noArticle}" ><button class="btn btn-primary">Modifier ma vente</button></a>
        <a href="deleteAuction?id=${auction.noArticle}" ><button class="btn btn-danger">Supprimer ma vente</button></a>
    </c:if>
</p>

</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>