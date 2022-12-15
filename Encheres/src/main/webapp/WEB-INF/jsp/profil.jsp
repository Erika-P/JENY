<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html lang="fr">
<%@include file="/WEB-INF/jsp/head.jspf"%>
<head>
<meta charset="UTF-8">

<title>Profil</title>
</head>
<body class="m-2">

<%@include file="/WEB-INF/jsp/nav.jsp"%>
<%--@elvariable id="page" type="string"--%>

<%if(session.getAttribute("ConnectedUser") instanceof Utilisateur && session.getAttribute("connectedUser") != null){
		Utilisateur connectedUser = (Utilisateur) session.getAttribute("ConnectedUser");	
	%>
	
<table class="table table-borderless">
    <tbody>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2" scope="row">Pseudo</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Nom</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Prénom</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Email</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Téléphone</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Rue</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Code Postal</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
        <tr class="row">
            <th class="offset-1 col-3 offset-md-4 col-md-2">Ville</th>
            <td class="offset-1 col-6 col-md-3"><%= connectedUser.getCodePostal() %></td>
        </tr>
    </tbody>
</table>
<br>
<a class="offset-2 col-8 offset-md-4 col-md-3" href="${pageContext.request.contextPath}/updateUser">
    <button class="btn btn-info btn-md btn-block">Modifier</button>
</a>
<%}%>

</body>
<footer>
<%@include file="/WEB-INF/jsp/footer.jsp"%>
</footer>
</html> 
