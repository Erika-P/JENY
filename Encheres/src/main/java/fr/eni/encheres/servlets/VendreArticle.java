package fr.eni.encheres.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.RetraitManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletVendreArticle
 */
@WebServlet("/postAuction")
public class VendreArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendreArticle() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher("/WEB-INF/jsp/ajoutArticle.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Article article = new Article();
		
		try {

			Categorie categorie = CategorieManager.selectCategorieByID(Integer.parseInt(request.getParameter("categorie")));
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("connectedUser");
			Retrait retrait = new Retrait();
			
			retrait = new Retrait();
			retrait.setRue(request.getParameter("rue"));
			retrait.setCodePostal(request.getParameter("codePostal"));
			retrait.setVille(request.getParameter("ville"));

			RetraitManager retraitManager = RetraitManager.getInstance();
			RetraitManager.addNewRetrait(retrait);				

			article.setNomArticle(request.getParameter("article"));
			article.setDescription(request.getParameter("description"));
			article.setDateDebutEncheres(LocalDate.parse(request.getParameter("dateDebutEncheres")));
			article.setDateFinEncheres(LocalDate.parse(request.getParameter("dateFinEncheres")));
			article.setPrixInitial(Integer.parseInt(request.getParameter("prixInitial")));
			article.setNoCategorie(categorie);
			article.setRetrait(retrait);
			article.setNoUtilisateur(utilisateur);

			ArticleManager.nouvelleVente(article);

			request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp").forward(request, response);

		} catch (Exception e) {
			request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp").forward(request, response);
		}
	}
}
