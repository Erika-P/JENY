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

    <article class="enchere-article">
        <div>
            <img src="<%=request.getContextPath()%>/upload/images/?imageName=${enchere.article.id}" alt="${enchere.article.nom}">
        </div>

    </article>
        <c:choose>
            <c:when test="${utilisateurSession.pseudo == user_best_offer}">
                <h3 class="text-center mb-2">Vous avez remporté la vente !</h3>
            </c:when>
            <c:otherwise>
                <h3 class="text-center mb-2">${user_best_offer} a remporté l'enchère</h3>
            </c:otherwise>
        </c:choose>
  
<h4 class="text-center m-4">${enchere.article.nom}</h4>
<div class="row">
    <table class="table offset-1 col-10 offset-md-4 col-md-4">
        <tr>
            <th scope="row">Description&nbsp;:</th>
            <td>${enchere.article.description}</td>
        </tr>
        <c:if test="${!empty(user_best_offer)}">
            <tr>
                <th scope="row">Meilleure offre&nbsp;:</th>
                <td>${auction.prixVente} par ${user_best_offer} </td>
            </tr>
        </c:if>
        <tr>
            <th scope="row">Mise à prix&nbsp;:</th>
            <td>${enchere.article.prixInitial} pts</td>
        </tr>
       <c:if test="${utilisateurSession.pseudo != user_best_offer}">
        <tr >
            <th scope="row">Fin de l'enchère&nbsp;:</th>
            <td><fmt:formatDate value="${auction.dateFinEncheres}" type="date" pattern="dd/MM/YYYY"/></td>
        </tr>
       </c:if>
        <tr>
            <th scope="row">Retrait&nbsp;:</th>
            <td>${withdrawal.rue}<br/>${withdrawal.codePostal} ${withdrawal.ville}</td>
        </tr>
        <tr>
            <th scope="row">Vendeur&nbsp;:</th>
            <td>${seller.pseudo}</td>
        </tr>
        <c:if test="${utilisateurSession.pseudo == user_best_offer}">
        <tr>
          <th scope="row">Téléphone&nbsp;:</th>
            <td ><a href="aquisition?id=${auction.noArticle}">${seller.phone}</a></td>     
        </tr>
        </c:if>
    </table>
</div>
<p class="text-center">
<c:if test="${utilisateurSession.noUtilisateur == auction.noUtilisateur}">
 <c:choose>
   <c:when test="${utilisateurSession.pseudo == user_best_offer}">
      <a href="${pageContext.request.contextPath}/Accueil"><button class="btn btn-primary">Retour</button></a>
   </c:when> 
   <c:otherwise>
        <a href="${pageContext.request.contextPath}/Accueil=${retrait.retrait.noArticle}"><button class="btn btn-danger">Retrait effectué</button></a>
        
   </c:otherwise>
  </c:choose>
</c:if>
</p>

</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>
