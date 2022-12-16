package fr.eni.encheres.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;

public class SessionManagement {

    /**
     * Get the session and record a session attribute for 5 minutes
     * @param request The request instance
     */
    public static void setSessionConnected (HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(300);
        session.setAttribute("isConnected", "true");
    }

    /**
     * Destroy the session, bye bye user !
     * @param request The request instance
     */
    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    /**
     *Set a session JavaBean from the pseudo the connected user
     * @param request The request instance
     * @param pseudo_utilisateur String
     * @throws BusinessException 
     * @throws DALException If there were any SQL issue into the DAL
     */
    public static void setUtilisateurSessionBean(HttpServletRequest request, String pseudo_utilisateur) throws DalException, BusinessException {
        UtilisateurManager um = new UtilisateurManager();
        HttpSession session = request.getSession();
        Utilisateur utilisateurToBean = UtilisateurManager.selectUserByPseudo(pseudo_utilisateur);
        session.setAttribute("utilisateurSession", utilisateurToBean);
    }
}
