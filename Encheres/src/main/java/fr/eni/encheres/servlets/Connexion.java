package fr.eni.encheres.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/login")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	       
		Utilisateur user = null;
		
		try {
			
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String pseudo = request.getParameter("pseudo");
			String email = request.getParameter("email");
			String password = request.getParameter("mdp");
			String confirmation = request.getParameter("mdpConf");
			String telephone = request.getParameter("tel");
			String rue = request.getParameter("rue");
			String ville = request.getParameter("ville");
			String codePostal = request.getParameter("cp");
			String dateDeNaissance = request.getParameter("datedn");
			
			List<Utilisateur> allPseudos = UtilisateurManager.selectAllUtilisateur();
			
			if(nom.length()==0 || nom.isEmpty() ) {
				request.setAttribute("erreur", "Le nom n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}	
			else if (prenom.length()==0 || prenom.isEmpty() ) {
				request.setAttribute("erreur", "Le pr�nom n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (pseudo.length()==0 || pseudo.isEmpty() ) {
				
				request.setAttribute("erreur", "Le pseudo n'a pas été renseigné, veuillez le saisir ...");			
				dispatcher.forward(request, response);
			}
			else if (allPseudos.contains(pseudo)) {
				request.setAttribute("erreur", "Ce pseudo est déjà utilisé, veuillez le saisir ...");
									
				dispatcher.forward(request, response);
			}
			else if (email.length()==0 || email.isEmpty() ) {
				request.setAttribute("erreur", "L'email n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (password.length()==0 || password.isEmpty() ) {
				request.setAttribute("erreur", "Le mot de passe n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (rue.length()==0 || rue.isEmpty() ) {
				request.setAttribute("erreur", "La rue n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (ville.length()==0 || ville.isEmpty() ) {
				request.setAttribute("erreur", "La ville n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (codePostal.length()==0 || codePostal.isEmpty() ) {
				request.setAttribute("erreur", "Le code postal n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (confirmation.equals(password)) {
			
				user = new Utilisateur();
				user = UtilisateurManager.inscriptionUtilisateur(user);
				
				if (user != null) {
					HttpSession session = request.getSession();
					session.setAttribute("ConnectedUser", user);
					
					this.getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
				} 
				else 
				{
					request.setAttribute("erreur", "Aucun utilisateur");
					dispatcher.forward(request, response);
				}
				
			}
			
		}catch (BusinessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DalException e) {
			e.printStackTrace();
		} catch (BllException e) {
			e.printStackTrace();
		} 
	}
}
