<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<meta charset="UTF-8">

<title>Profil</title>
</head>
<body>

  <jsp:useBean id="utilisateur"
             scope="request"
             class="fr.eni.encheres.bo.Utilisateur">
</jsp:useBean>
<table class="table table-borderless">
    <tbody>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Pseudo</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="pseudo"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Nom</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="nom"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Prénom</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="prenom"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Email</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="email"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Téléphone</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="telephone"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Rue</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="rue"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Code Postal</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="codePostal"/></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-5 col-md-2">Ville</th>
            <td class="offset-1 col-7 col-md-3"><jsp:getProperty name="utilisateur" property="ville"/></td>
        </tr>
    </tbody>
</table>

<a class="" href="${pageContext.request.contextPath}/updateUser">
    <button class="btn">Modifier</button>
</a>
</body>
</html> 