<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="fr.eni.encheres.bll.CategorieManager"%>
<%@page import="fr.eni.encheres.bll.ArticleManager"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@page import="fr.eni.encheres.bo.Utilisateur"%>

<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">
<title>Accueil</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>

<h1 class="mb-5 text-center">Nouvelle vente</h1>

 <section class="vente-form">
 
      <form class="vente" action="<%=request.getContextPath() %>/postAuction" method="post">
      
<div class="form-row">
    <label for="name" class="col-2 offset-md-3 col-md-2">Article</label>
    <input required name="nom" type="text" class="form-control offset-1 col-8 col-md-3" id="name">

</div>
<div class="form-row mt-2">
    <label for="description" class="form col-2 offset-md-3 col-md-2">Description</label>
    <textarea name="description" id="description" cols="30" rows="10" class="form-control offset-1 col-8 col-md-3" placeholder="Entrez une description de l'article"></textarea>
</div>
<div class="form-row mt-2">
    <label class="col-2 offset-md-3 col-md-2" for="categorie">Catégorie</label>
    <select name="categorie_filter" id="categorie" class="form-control offset-1 col-8 col-md-3">
    <option value="all">Toutes</option>    
          <% for(Categorie categorie : CategorieManager.selectAllCategorie()) { %>
            <option name="categorie" value ="<%=categorie.getNoCategorie()%>"><%=categorie.getLibelle()%>></option>
            <% } %>
    </select>
</div>
<div class="form-row mt-2">
    <label for="picture" class="col-4 offset-md-3 col-md-2">Photo de l'article</label>
    <input type="file" class="offset-1 col-7 col-md-3 form-control-file" id="picture">
</div>
<div class="form-row mt-2">
    <label for="miseAPrix" class="col-4 offset-md-3 col-md-2">Mise à prix</label>
    <input required type="number" name="miseAPrix" id="miseAPrix" class="offset-1 col-6 col-md-3 form-control" min="1" value="1" Step="1">
</div>
<div class="form-row mt-3">
    <label for="dateDebutEncheres" class="col-4 offset-md-3 col-md-2">Début de l'enchère</label>
    <div class="offset-1 col-6 col-md-3">
        <div class="form-group">
            <div class="input-group date" id="dateDebutEncheres" data-target-input="nearest">
                <input required id="dateDebutEncheres" name="dateDebutEncheres" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker1"/>
                <div class="input-group-append" data-target="#dateDebutEncheres" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="form-row">
    <label for="dateFinEncheres" class="col-4 offset-md-3 col-md-2">Fin de l'enchère</label>
    <div class="offset-1 col-6 col-md-3">
        <div class="form-group">
            <div class="input-group date" id="dateFinEncheres" data-target-input="nearest">
                <input required id="dateFinEncheres" name="dateFinEncheres" type="text" class="form-control datetimepicker-input" data-target="#datetimepicker2"/>
                <div class="input-group-append" data-target="#dateFinEncheres" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                </div>
            </div>
        </div>
    </div>
</div>
<fieldset class="form-group">
    <div class="form-row">
        <legend class="col-form offset-md-3 col-md-1">Retrait</legend>
        <div class="form-group offset-md-1 col-md-7">
            <div class="form-row">
                <label class="col-3" for="rue">Rue</label>
                <input required type="text" id="rue" name="rue" class="form-control d-inline offset-1 col-6 col-md-3" value="<%=connectedUser.getRue() %>" required>

            </div>
            <div class="form-row mt-2">
                <label class="col-3" for="codePostal">Code postal</label>
                <input required type="text" id="codePostal" name="codePostal" class="form-control d-inline offset-1 col-6 col-md-3" value="<%=connectedUser.getCodePostal() %>" required>
            </div>
            <div class="form-row mt-2">
                <label class="col-3" for="ville">Ville</label>
                <input required type="text" id="ville" name="ville" class="form-control d-inline offset-1 col-6 col-md-3" value="<%=connectedUser.getVille() %>" required>
            </div>
        </div>
    </div>
</fieldset>
<div class="form-row">
 <button class="offset-1 col-10 offset-md-2 col-md-3 btn btn-success btn-block mb-2" type="submit">Enregistrer</button>
    <a href="${pageContext.request.contextPath}/Accueil" class="offset-1 col-4 offset-md-1 col-md-2"></a>
        <button type="button" class="btn btn-lg btn-block btn-danger">Annuler</button>
          <a href="${pageContext.request.contextPath}/Accueil" class="offset-1 col-4 offset-md-1 col-md-2">
    </a>
</div>

 </form>
    </section>
    
</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>