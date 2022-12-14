package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class MiseAJourProfil
 */
@WebServlet("/updateUser")
public class MiseAJourProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/miseAJourProfil.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur) session.getAttribute("connectedUser");

		try {
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String rue = request.getParameter("rue");
			String codePostal = request.getParameter("codepostale");
			String ville = request.getParameter("ville");

			connectedUser.setNom(nom);
			connectedUser.setPrenom(prenom);
			connectedUser.setEmail(email);
			connectedUser.setTelephone(telephone);
			connectedUser.setRue(rue);
			connectedUser.setCodePostal(codePostal);
			connectedUser.setVille(ville);

			UtilisateurManager utilisateurManager = new UtilisateurManager();		
			utilisateurManager.update(connectedUser);
			session.setAttribute("ConnectedUser", connectedUser);

			this.getServletContext().getRequestDispatcher("/Profil").forward(request, response);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
