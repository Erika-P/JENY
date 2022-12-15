package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bll.UtilisateurManager;




/**
 * Servlet implementation class CreerCompte
 */
@WebServlet("/register")
public class CreerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
		request.setAttribute("page", "register");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String pseudo       = request.getParameter("pseudo");
	        String nom          = request.getParameter("nom");
	        String prenom       = request.getParameter("prenom");
	        String email        = request.getParameter("email");
	        String telephone    = request.getParameter("telephone");
	        String rue          = request.getParameter("rue");
	        String codePostal   = request.getParameter("codePostal");
	        String ville        = request.getParameter("ville");
	        String mdp          = request.getParameter("mdp");
	        String mdpConf      = request.getParameter("mdpConf");
	        String credit          = request.getParameter("credit");
	        String administration      = request.getParameter("mdpConf");


	        Utilisateur ajoutUtilisateur = new Utilisateur(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,mdp);
	        UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
	        Utilisateur utilisateur = null;
	        RequestDispatcher dispatcher = null;
	        String messageErreur = "" ;

	        if (!mdpConf.equals(mdp)){

	            request.setAttribute("ajoutUtilisateur", ajoutUtilisateur);
	            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
	            dispatcher.forward(request, response);}
	            else{
	                try {
	                    utilisateur = utilisateurManager.insert(ajoutUtilisateur);

	                } catch (Exception e) {
	                    // Je récupère le message de l'exception
	                    messageErreur = e.getMessage();
	                }

	                if (utilisateur == null) {
	                    request.setAttribute("Erreur", messageErreur);
	                    request.setAttribute("ajoutUtilisateur", ajoutUtilisateur);
	                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
	                    dispatcher.forward(request, response);
	                } else {
	                    HttpSession session = request.getSession();
	                    session.setAttribute("utilisateur", utilisateur);

	                    this.getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
	                }
	            }
	}
}
