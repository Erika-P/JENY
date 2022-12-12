package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.ArticleDao;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleManager {

    private static ArticleManager instance;
    private ArticleDao articleDao;
	

    //Private constractor
    private ArticleManager() { articleDao = FactoryDao.getArticleDao(); }

    //Only one instance
    public static ArticleManager getInstance() {
        if (instance == null) {
            return new ArticleManager();
        }
        return instance;
    }

    public List<Article> getAllArticles() throws BllException {
        List<Article> articleList = new ArrayList<>();
        try {
            articleList = articleDao.getAllArticles();
        } catch(Exception e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException("Error getAllArticles ArticleManager " + e.getMessage());
        }
        return articleList;
    }

    public Article getArticleById(int articleId) throws BllException {
        Article article = new Article();
        try {
            article = articleDao.getById(articleId);
        } catch(Exception e){
        	 BllException bllException = new BllException();
             bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException("Error getArticleById ArticleManager " + e.getMessage());
        }
        return article;
    }

    public Article addNewArticle(Article newArticle) throws Exception {
        Article addedArticle = null;
        if (newArticle.getNoUtilisateur() == null) {
            throw new BllException("L'article doit avoir un utilisatuer");
        } else if (newArticle.getNoCategorie() == null) {
            throw new BllException("L'article doit avoir une categorie");
        } else if (newArticle.getRetrait() == null) {
            throw new BllException("L'article doit avoir un point de retrait");
        } else if (newArticle.getNomArticle().equals("")) {
            throw new BllException("L'article doit avoir un nom");
        } else if (newArticle.getDescription().equals("")) {
            throw new BllException("L'article doit avoir une description");
        } else if (newArticle.getDateDebutEncheres() == null) {
            throw new BllException("L'article doit avoir une date de debut d'enchere");
        } else if (newArticle.getDateFinEncheres() == null) {
            throw new BllException("L'article doit avoir une date de fin d'enchere");
        } else if (newArticle.getPrixInitial() == 0) {
            throw new BllException("L'article doit avoir une prix initial");
        } else {
            controleDateEnchere(newArticle);
            addedArticle = articleDao.insertArticle(newArticle);
        }
        return addedArticle;
    }

    public void updateArticle(Article updateArticle) throws Exception {
    	 BllException bllException = validateArticle(updateArticle);
         if (bllException.hasErrors()) {
             throw bllException;
         } else {
             articleDao.updateArticle(updateArticle);
         }
    }
    /**
     * Méthode controleDateEnchere
     * Utilisé sur updateArticle,addNewArticle
     * Contrôle date enchère,
     *              *vérifie que la date fin enchère est supérieur à la date début enchère
     *              *Vérifie que la date de début d'enchère est supérieure à la date du jour
     * @param article
     */
    private void controleDateEnchere(Article article) throws Exception {
        if (article.getDateFinEncheres().isBefore(article.getDateDebutEncheres()))
        throw new BllException("La date de début d'enchère ne peut pas être située après la date de fin enchère");
        if (article.getDateDebutEncheres().isBefore(LocalDate.from(Instant.now()))) {
            if (article.getDateDebutEncheres().isAfter(LocalDate.from(Instant.now()))) {
                throw new BllException("La date et l'heure de début ne peuvent pas être inférieur à l'heure du jour");
            } else if (article.getDateDebutEncheres().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).equals(Timestamp.from(Instant.now()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))) {
                throw new BllException("Vous avez saisie la même date et heure sur les deux dates");
            }
        }}

        public void deleteArticle(Article articleSuppression) throws DalException{
        	 articleDao.deleteArticle(articleSuppression);
        }

        private BllException validateArticle(Article article) {
            BllException bllException = new BllException();
            if (article.getNomArticle().length() > 30) {
                bllException.addError(ErrorCodesBLL.ERROR_LENGTH_NOM_ARTICLE);
            }
            if (article.getDescription().length() > 300) {
                bllException.addError(ErrorCodesBLL.ERROR_LENGTH_DESCRIPTION_ARTICLE);
            }
            String[] acceptedValuesEtatVente = {"PC", "EC", "AN", "VE"};
            if (!Arrays.asList(acceptedValuesEtatVente).contains(article.getEtatVente())) {
                bllException.addError(ErrorCodesBLL.ERROR_VALUE_STATUT_VENTE_ARTICLE);
            }
            if (article.getDateDebutEncheres().isAfter(article.getDateFinEncheres())) {
                bllException.addError(ErrorCodesBLL.ERROR_START_DATE_AFTER_END_DATE);
            }
            if (article.getDateDebutEncheres().isBefore(article.getDateFinEncheres())) {
                bllException.addError(ErrorCodesBLL.ERROR_DATE_BEFORE_TODAY);
            }
            return bllException;
        }
}
















