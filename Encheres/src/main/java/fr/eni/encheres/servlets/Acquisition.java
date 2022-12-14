package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class Acquisition
 */
@WebServlet("/Acquisition")
public class Acquisition extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 UtilisateurManager utilisateurManager = null;

	    @Override
	    public void init() throws ServletException {
	        super.init();
	        utilisateurManager = UtilisateurManager.getInstance();
	    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.getRequestDispatcher("WEB-INF/jsp/acquisition.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur) session.getAttribute("ConnectedUser");
		int currentCredit = connectedUser.getCredit();
		
		int prixEnchere = Integer.parseInt(request.getParameter("mPrix"));
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		
		Article articleAffiche = null;
		try {
			articleAffiche = ArticleManager.getArticleById(idArticle);
		} catch (BllException e) {
			e.printStackTrace();
		}
		 
		
		if (articleAffiche != null && prixEnchere > articleAffiche.getPrixVente()
				&& connectedUser.getCredit() >= prixEnchere) 
				{
			Enchere enchere = new Enchere();
			articleAffiche.setPrixVente(prixEnchere);
			
			
			try {
				EnchereManager.addNewEnchere(connectedUser, idArticle, prixEnchere);
				request.setAttribute("mPrix", prixEnchere);
				request.setAttribute("meilleureEnchere", enchere);
				ArticleManager.updateArticle(articleAffiche);
				connectedUser.setCredit(currentCredit - prixEnchere);
				UtilisateurManager utilisateurManager = new UtilisateurManager();
				utilisateurManager.update(connectedUser);
			} catch (BusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp").forward(request, response);
		}
		
	}

}
