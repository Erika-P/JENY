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
		
	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
	       
		Utilisateur user = null;
		
		try {  
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


			List<String> allPseudos = UtilisateurManager.selectAllPseudos();

			
			if (pseudo.length()==0 || pseudo.isEmpty() ) {
				
				request.setAttribute("erreur", "Le pseudo n'a pas été renseigné, veuillez le saisir ...");			
				dispatcher.forward(request, response);
			}
			else if (allPseudos.contains(pseudo)) {
				request.setAttribute("erreur", "Ce pseudo est déjà utilisé, veuillez le saisir ...");
									
				dispatcher.forward(request, response);
			}
			else if(nom.length()==0 || nom.isEmpty() ) {
				request.setAttribute("erreur", "Le nom n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}	
			else if (prenom.length()==0 || prenom.isEmpty() ) {
				request.setAttribute("erreur", "Le pr�nom n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (email.length()==0 || email.isEmpty() ) {
				request.setAttribute("erreur", "L'email n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (telephone.length()==0 || telephone.isEmpty() ) {
				request.setAttribute("erreur", "Le mot de passe n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (rue.length()==0 || rue.isEmpty() ) {
				request.setAttribute("erreur", "La rue n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (codePostal.length()==0 || codePostal.isEmpty() ) {
				request.setAttribute("erreur", "Le code postal n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (ville.length()==0 || ville.isEmpty() ) {
				request.setAttribute("erreur", "La ville n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (mdp.length()==0 || mdp.isEmpty() ) {
				request.setAttribute("erreur", "Le mot de passe n'a pas été renseigné, veuillez le saisir ...");
				dispatcher.forward(request, response);
			}
			else if (mdpConf.equals(mdp)) {
			
				user = new Utilisateur(pseudo,nom,prenom,email,telephone,rue,codePostal,ville,mdp);
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
		}	
	}
}
