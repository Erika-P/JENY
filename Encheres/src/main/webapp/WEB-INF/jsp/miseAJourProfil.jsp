<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">
<title>ENI Enchères/Mon profil</title>
</head>
<body  class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>

    <h2 class="mb-md-5 text-center">Mon Profil</h2>
    
    <br>
    
   <%  Utilisateur connectedUser = (Utilisateur) session.getAttribute("connectedUser"); %>
   
    <section  class="form-login">

      <form action="<%=request.getContextPath() %>/updateProfil?pseudo=" method="post">

     <div class="form-group row" id="name">
        <label class="col-form-label col-md-1" for="pseudo">Pseudo : <%= connectedUser.getPseudo()%></label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="pseudo" id="pseudo" placeholder="Pseudo (Caractères alphanumériques)" >
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="name">Nom : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="name" id="name" placeholder="Nom" value="<%= connectedUser.getNom() %>" required>
        </div>
     </div>

     <div class="form-group row" id="first_name">
        <label class="col-form-label col-md-1" for="first_name">Prénom : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="first_name" id="first_name" placeholder="Prenom" value="<%= connectedUser.getPrenom() %>" required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="mail">Email : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="mail" id="mail" placeholder="Email" value="<%= connectedUser.getEmail() %>" required>
        </div>
     </div>
        
     <div class="form-group row" id="house">
        <label class="col-form-label col-md-1" for="phone">Téléphone : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="phone" id="phone" placeholder="Téléphone (format : 0699999999)" value="<%= connectedUser.getTelephone() %>"  required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="street">Rue : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="street" id="street" placeholder="Rue" value="<%= connectedUser.getRue() %>" required>
        </div>
     </div>
        
 <div class="form-group row" id="ville">
        <label class="col-form-label col-md-1" for="post_code">Code Postal : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="post_code" id="post_code" placeholder="Code Postal" value="<%= connectedUser.getCodePostal() %>" required>
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="city">Ville : </label>
        <div class="col-md-4">
            <input type="text" class="form-control" name="city" id="city" placeholder="Ville" value="<%= connectedUser.getVille() %>" required>
        </div>
     </div>
        
        <div class="form-group row" id="new_password_block">
        <label class="col-form-label col-md-1" for="current_password">Mot de passe actuel</label>
        <div class="col-md-4">
            <input required type="password" class="form-control" name="current_password" id="current_password" placeholder="Mot de passe" value="<%= connectedUser.getMotDePasse() %>" required>
        </div>
    </div>
    <div class="form-group row" id="new_password_block">
        <label class="col-form-label col-md-1" for="new_password">Nouveau mot de passe</label>
        <div class="col-md-4">
            <input type="password" class="form-control" name="new_password" id="new_password" placeholder="Mot de passe">
        </div>
        <label class="col-form-label offset-md-1 col-md-1" for="confirm_new_password">Confirmation</label>
        <div class="col-md-4">
            <input type="password" class="form-control" name="confirm_new_password" id="confirm_new_password" placeholder="Répétez le mot de passe">
        </div>
    </div>
        
            <div class="row mt-md-5">
        <div class="col-3 col-md-1">Crédits</div>
          <div class="col-3 col-md-1">
         <input type="text" value="${utilisateurSession.credit}">
          </div>
        </div>
        
        <br>
        
        <div class="row mt-3 mt-md-5">
        <input type="submit" value="Enregistrer" class="offset-1 col-10 offset-md-2 col-md-3 btn btn-success btn-block mb-2">
        <a href="${pageContext.request.contextPath}/deleteProfile" class="offset-1 col-10 offset-md-1 col-md-3 p-0">
            <button type="button" class="btn btn-danger btn-block">Supprimer mon compte</button>
        </a>
    </div>

      </form>
    </section>
</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html>