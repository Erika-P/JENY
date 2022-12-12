package fr.eni.encheres.dal.dao;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface CategorieDao {

    /* METHODE POUR L ADMINISTRATION */
    public Categorie insert(Categorie categorie) throws SQLException, DalException;
    
    public void update(Categorie categorie) throws SQLException, DalException;
    
   public  void delete(int noCategorie) throws SQLException, DalException;

    /* METHODE IMPORTANTE */
    public  List<Categorie> getAllCategorie() throws BusinessException ;
    
    public Categorie getCategorieByID(int CategorieId) throws SQLException, DalException;
    
    public Categorie selectByName(String nomCategorie) throws SQLException, DalException;

}
