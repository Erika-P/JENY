
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<nav class="d-flex flex-column justify-content-between flex-md-row justify-content-md-between p-3">
    <div class="d-flex flex-row d-md-inline-block">
        <span class="pl-2 col-lg-2 pt-5 pt-md-0">Enchères</span>
    <c:choose>
        <%--@elvariable id="isConnected" type="boolean"--%>
        <c:when test="${isConnected}">
            <jsp:useBean id="utilisateurSession"
                         scope="session"
                         class="fr.eni.encheres.bo.Utilisateur">
            </jsp:useBean>
                <a href="${pageContext.request.contextPath}/Accueil">
                <img class="d-block" src="img/auction_logo.png" height="100" width="100">
                </a>
    </div>
            <div class="d-flex flex-column d-md-block">
                <a class="ml-md-5 mr-md-5" href="${pageContext.request.contextPath}/postAuction">Vendre un article </a>
                <a class="ml-md-5 mr-md-5" href="${pageContext.request.contextPath}/profil=<jsp:getProperty name="utilisateurSession" property="noUtilisateur"/>">Mon profil </a>
                <a class="ml-md-5 mr-md-5" href="${pageContext.request.contextPath}/logout">Déconnexion </a>
            </div>
        </c:when>
        <c:otherwise>
                <a href="${pageContext.request.contextPath}/Accueil"><img class="d-block" src="images/image.png" height="100" width="100"></a>
            </div>
            <%--@elvariable id="page" type="string"--%>
            <c:if test="${page != 'login'}">
            <div class="">
            <ul>
                <a href="<%=request.getContextPath()%>/register"> Créer Un Compte </i></a>&nbsp;
                 <a href="<%=request.getContextPath()%>/login"> Se Connecter  <i class="fas fa-sign-in-alt"></i></a>
            </ul>
        </div>
               
            </c:if>
        </c:otherwise>
    </c:choose>
</nav>
