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
<title>Accueil</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>

<h1 class="mb-5 text-center">Liste des enchères</h1>

<form class="row" action="${pageContext.request.contextPath}/" method="post" id="filter_auctions">
    <div class="offset-lg-3 col-lg-6 d-flex flex-column">
        <p class="m-0">Filtres&nbsp;:</p>
        <%--@elvariable id="stringFilter" type="java.lang.String"--%>
        <input name="string_filter" type="text" class="form-control m-2" placeholder="Le nom de l'article contient" value="${stringFilter}">
        <p class="d-flex flex-row">
        
            <label class="col-lg-3 pt-3" for="categories">Catégorie&nbsp;:</label>
            <select name="category_filter" id="categories" class="col-lg-9 mt-2 form-control">
                <option value="all">Toutes</option>
                 <% for(Categorie categorie : CategorieManager.selectAllCategorie()) { %>
                 <option name="categorie" value ="<%=categorie.getNoCategorie()%>"><%=categorie.getLibelle()%></option>
                 <% } %>
            </select>
        </p>
        <c:if test="${isConnected}">
            <div class="form-check form-check-inline ml-3 ml-lg-0 row">
                <div class="offset-1 offset-lg-0 col-10 col-lg-6 d-flex flex-column text-left ml-0">
                    <div>
                        <input checked class="form-check-input guiFilterChoice" type="radio" id="currentBuying" name="checboxesChoice">
                        <label for="currentBuying" class="form-check-label">Achats</label>
                    </div>
                    <div class="d-flex flex-column text-left guiFilterChoiceRadios">
                        <div class="form-check">
                            <input type="radio" class="form-check-input radioFilterChoice" id="allCurrentAuctions" name="filterChoice" value="currentAuctions">
                            <label for="allCurrentAuctions" class="form-check-label">Enchères ouvertes</label>
                        </div>
                        <div class="form-check">
                            <input <c:if test="${filterByMyCurrentAuctions}">checked</c:if> type="radio" class="form-check-input radioFilterChoice" id="myCurrentAuctions" name="filterChoice" value="myCurrentAuctions">
                            <label for="myCurrentAuctions" class="form-check-label">Mes enchères en cours</label>
                        </div>
                        <div class="form-check">
                            <input <c:if test="${filterByWonAuctions}">checked</c:if> type="radio" class="form-check-input radioFilterChoice" id="auctionsIWon" name="filterChoice" value="myWonAuctions">
                            <label for="auctionsIWon" class="form-check-label">Mes enchères remportées</label>
                        </div>
                    </div>
                </div>
                <div class="offset-1 offset-lg-0 col-10 col-lg-6 d-flex flex-column text-left ml-0">
                    <div>
                        <input type="radio" id="currentSelling" class="form-check-input guiFilterChoice" name="checboxesChoice">
                        <label for="currentSelling" class="form-check-label">Mes ventes</label>
                    </div>
                    <div class="d-flex flex-column text-left guiFilterChoiceRadios">
                        <div class="form-check">
                            <input <c:if test="${filterByMyCurrentSales}">checked</c:if> disabled type="radio" class="form-check-input radioFilterChoice" id="myCurrentSales" name="filterChoice" value="myCurrentSales">
                            <label for="myCurrentSales" class="form-check-label">Mes ventes en cours</label>
                        </div>
                        <div class="form-check">
                            <input <c:if test="${filterByMyNotStartedSales}">checked</c:if> disabled type="radio" class="form-check-input radioFilterChoice" id="nonStartedSales"  name="filterChoice" value="myNotStartedSales">
                            <label for="nonStartedSales" class="form-check-label">Ventes non débutées</label>
                        </div>
                        <div class="form-check">
                            <input <c:if test="${filterByMyEndedSales}">checked</c:if> disabled type="radio" class="form-check-input radioFilterChoice" id="endedSales" name="filterChoice" value="myEndedSales">
                            <label for="endedSales" class="form-check-label">Ventes terminées</label>
                        </div>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                var guiSelectChoice = $(".guiFilterChoice");
                var guiFilterChoiceRadios = $(".guiFilterChoiceRadios");
                var filterAuctions = $("#filter_auctions");
                guiSelectChoice.each(function (i) {
                    $(this).on("click", function () {
                        filterAuctions.find(".radioFilterChoice").prop("disabled", true);
                        filterAuctions.find(".radioFilterChoice").prop("checked", false);
                        guiFilterChoiceRadios.eq(i).find(".radioFilterChoice").prop("disabled", false);
                    });
                });
            </script>
        </c:if>
        <input type="submit" class="btn btn-info btn-lg btn-block mt-2" id="#searchButton" value="Rechercher"/>
    </div>
</form>

<div class="container">
    <div class="row d-flex justify-content-between">
        <c:forEach items="${current_auctions}" var="article">
            <div class="d-flex flex-column col-md-5 border border-dark mt-3 mb-3">
                <p><u>${articleVendu.nomArticle}</u></p>
                <p>Prix : ${article.prixVente}</p>
                <p>
                    Statut de l'enchère :
                    <c:if test="${article.etatVente == 'EC'}">
                        En cours
                    </c:if>
                    <c:if test="${article.etatVente == 'PC'}">
                        Pas encore commencée
                    </c:if>
                    <c:if test="${article.etatVente == 'VE'}">
                        Finie
                    </c:if>
                    <c:if test="${article.etatVente == 'AN'}">
                        Annulée
                    </c:if>
                </p>
                <p>Fin de l'enchère : <fmt:formatDate value="${article.dateFinEncheres}" type="date" pattern="dd/MM/YYYY"/> </p>
                <p>Vendeur : ${pseudos[article.noArticle]}</p>
                <a class="text-center" href="${pageContext.request.contextPath}/auction?id=${article.noArticle}"><button class="btn btn-info mb-3">Voir l'enchère</button></a>
            </div>
        </c:forEach>
    </div>
</div>

</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>