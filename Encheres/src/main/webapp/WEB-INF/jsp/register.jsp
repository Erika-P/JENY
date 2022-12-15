<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">
<title>Formulaire d'Inscription</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>

         <h2 class="mb-md-5 text-center">Mon Profil</h2>
         
<section class="register-form">
<form class="register" action="<%=request.getContextPath() %>/register" method="post">
    <div class="inscription-container">
                <div class="form-group row" id="name">
        <label class="col-form-label col-md-1" for="pseudo">Pseudo : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="pseudo" id="pseudo" placeholder="Pseudo (Caractères alphanumériques)" required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="nom">Nom : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="nom" id="nom" placeholder="Nom" required>
        </div>
     </div>

     <div class="form-group row" id="first_name">
        <label class="col-form-label col-md-1" for="prenom">Prénom : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="prenom" id="prenom" placeholder="Prenom"  required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="email">Email : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="email" id="email" placeholder="Email" required>
        </div>
     </div>
        
     <div class="form-group row" id="house">
        <label class="col-form-label col-md-1" for="telephone">Téléphone : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="telephone" id="telephone" placeholder="Téléphone (format : 0699999999)" maxlength= 10  minlength= 10 required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="rue">Rue : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="rue" id="rue" placeholder="Rue" required>
        </div>
     </div>
        
 <div class="form-group row" id="ville">
        <label class="col-form-label col-md-1" for="codePostal">Code Postal : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="codePostal" id="codePostal" placeholder="Code Postal" required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="ville">Ville : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="ville" id="ville" placeholder="Ville" required>
        </div>
</div>
 <div class="form-group row" id="new_password_block">
        <label class="col-form-label col-md-1" for="mdp">Mot de passe</label>
        <div class="col-md-4">
            <input type="password" class="form-control" name="mdp" id="mdp" placeholder="Mot de passe" required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="mdpConf">Confirmation</label>
        <div class="col-md-4">
            <input type="password" class="form-control" name="mdpConf" id="mdpConf" placeholder="Répétez le mot de passe" required>
        </div>
    </div>
  </div>    
  <br>
        
      <div class="row mt-3 mt-md-5">
      <button class="offset-1 col-10 offset-md-2 col-md-3 btn btn-success btn-block mb-2" type="submit">Créer</button>
        <a href="${pageContext.request.contextPath}/AccueilCo" class="offset-1 col-10 offset-md-1 col-md-3 p-0">
            <button type="button" class="btn btn-danger btn-block">Annuler</button>
            <a href ="<%=request.getContextPath() %>/Accueil">
        </a>
      </div>
      
</form>
 </section>


</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>