package fr.eni.encheres.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.BllException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;

/**
 * Servlet implementation class Accueil
 */
@WebServlet("/Accueil")
public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	EnchereManager enchereManager = null;
    CategorieManager categorieManager = null;
       
    
    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = EnchereManager.getInstance();
        categorieManager = CategorieManager.getInstance();
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 List<Enchere> enchereList = new ArrayList<>();
	        List<Categorie> categorieList = new ArrayList<>();
	        try {
	            enchereList = enchereManager.selectAllEnchere();
	            categorieList = CategorieManager.selectAllCategorie();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (BllException e) {
	            e.printStackTrace();
	        } catch (BusinessException e) {
				e.printStackTrace();
			}
	        request.setAttribute("listCategorie", categorieList);
	        request.setAttribute("enchereListe", enchereList);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp");
	        dispatcher.forward(request, response);
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
