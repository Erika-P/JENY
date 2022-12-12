package fr.eni.encheres.dal.dao;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface UtilisateurDao {

	public Utilisateur getById(int noUtilisateur) throws SQLException, DalException;
	
	public Utilisateur getByPseudo (String pseudo) throws BusinessException;
	
	public Utilisateur insert(Utilisateur utilisateur) throws SQLException, DalException;

	public List<Utilisateur> getAllUtilisateur() throws SQLException, DalException;

	public Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException;

    public Utilisateur selectLogin(String pseudoOuEmail) throws SQLException, DalException;

    public void update(Utilisateur utilisateur) throws SQLException, DalException;

    public void delete(int noUtilisateur) throws SQLException, DalException;
    
    public List<Article> getAllArticle(Utilisateur utilisateur) throws BusinessException;

	public List<String> getAllPseudos() throws BusinessException;
    
    public Utilisateur updateCredit(int noUtilisateur, int newCredit) throws DalException;

    public boolean verifEmail(String email, int id) throws SQLException,DalException;

    public boolean verifPseudo(String pseudo, int id) throws  SQLException,DalException;

    public void modifMotDePasse(String motDePasse, String cle) throws DalException, SQLException;
}
