package fr.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class SuppressionProfil
 */
@WebServlet("/deleteProfile")
public class SuppressionProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur) session.getAttribute("connectedUser");

		if (connectedUser != null) {
			try {
				UtilisateurManager.delete(connectedUser.getId());
				session.removeAttribute("connectedUser");
				session.invalidate();
				request.getRequestDispatcher("/WEB-INF/jsp/Accueil.jsp").forward(request, response);
			} catch (BusinessException e) {
				request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
