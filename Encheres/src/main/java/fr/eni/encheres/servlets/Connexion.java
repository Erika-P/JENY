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
	       
        PrintWriter out= response.getWriter();
		
		HttpSession session = request.getSession();
		
		String erreur = null;
		
		String identifiant = request.getParameter("id");
		String password = request.getParameter("mdp");
		
		
		if(identifiant.length()==0 || identifiant.isEmpty()){
				
			//création de l'erreur
			request.setAttribute("erreur", "pseudo non renseigné. Veuillez le saisir...");
			erreur = (String) session.getAttribute("erreur");
			out.println(erreur);
			
			//redirection vers la page de connexion pour saisir le login
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/register.jsp").forward(request, response);
			
			
		}else if(password.length()==0 || password.isEmpty()) {
				
			//cr�ation de l'erreur
			request.setAttribute("erreur", "mot de passe non renseigné. Veuillez le saisir...");
			erreur = (String) session.getAttribute("erreur");
			out.println(erreur);
			//redirection vers la page de connexion pour saisir le login
			this.getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
			
		}else {
			
			try {
		
				//Valider pseudo utilisateur, verification si il est bien dans la bdd
				Utilisateur user = UtilisateurManager.selectUserByPseudo(identifiant);
				//Si la connexion est reussie
				if(user!= null && password.equals(user.getMotDePasse())) {
					request.getSession().setAttribute("ConnectedUser", user);
					
					this.getServletContext().getRequestDispatcher("/Accueil").forward(request, response);
					
				} else {
					request.setAttribute("erreur", "pseudo et/ou mot de passe incorrect(s)! Veuillez ressaisir vos identifiants...");
					erreur = (String) session.getAttribute("erreur");
					out.println(erreur);
					this.getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
				}
			} catch (BusinessException e) {
				request.setAttribute("erreur", e);
				this.getServletContext().getRequestDispatcher("/ServletErreurPage").forward(request, response);
			}
		
		}
	}
}
