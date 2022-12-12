package fr.eni.encheres.dal.dao;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface ArticleDao {

    Article getById(int articleId) throws SQLException, DalException;

    List<Article> getAllArticles() throws DalException;

    List<Article> getAllArticlesByUtilisateur(Utilisateur utilisateur) throws SQLException, DalException;

    Article insertArticle(Article article) throws SQLException, DalException, BusinessException;

    public void updateArticle(Article updateArticle) throws  SQLException, DalException;

    public void deleteArticle(Article articleSuppression) throws DalException;
                          
    public List<Article> getByRetrait(Retrait retrait) throws BusinessException;
}
