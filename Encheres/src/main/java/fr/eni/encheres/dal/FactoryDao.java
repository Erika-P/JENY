package fr.eni.encheres.dal;

import fr.eni.encheres.dal.dao.*;
import fr.eni.encheres.dal.jdbc.*;

public class FactoryDao {

    public static UtilisateurDao getUtilisateurDao() {
        return new UtilisateurDaoJdbcImpl();}

    public static CategorieDao getCategorieDao() { 
    	return new CategorieDaoJdbcImpl();}

    public static RetraitDao getRetraitDao() { 
    	return  new RetraitDaoJdbcImpl(); }

    public static ArticleDao getArticleDao() { 
    	return new ArticleDaoJdbcImpl(); }

    public static EnchereDao getEnchereDao(){ 
    	return  new EnchereDaoJdbcImpl(); }
    }
