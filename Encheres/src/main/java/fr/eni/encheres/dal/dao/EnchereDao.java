package fr.eni.encheres.dal.dao;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface EnchereDao {
	
   //GetAll
	public List<Enchere> getAllEnchere() throws SQLException, DalException;

   
    //GetEnchere
    public List<Enchere> getEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom , int filtreCategorie ) throws SQLException, DalException;

    public List<Enchere> getEnchereByArticle(Article article) throws SQLException, DalException;
    
    
    public Enchere insert(Enchere enchere) throws BusinessException;
    
    public void update(Enchere enchere) throws BusinessException;
	
	public void delete(int id) throws BusinessException;

    public Enchere getEnchereByIdArticle(int idArticle) throws SQLException, DalException;

    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, DalException;

    public Enchere selectById(int id) throws SQLException, DalException;

    public List<Enchere> selectEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException;


    /*requete pour afficher les choix d'affichage d'un future acheteur*/
    public List<Enchere> afficherRequete(String condition) throws SQLException, DalException;

    public List<Enchere> selectAllEncheresVendeur(int idUtilisateur, List<String> conditions, int idCategorie, String nomTitreArticle) throws DalException;

    public List<Enchere> selectEnchereByMontantMax(int idArticle) throws SQLException, DalException;
}
