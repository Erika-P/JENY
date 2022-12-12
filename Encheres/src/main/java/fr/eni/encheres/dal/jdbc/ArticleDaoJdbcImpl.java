package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.ArticleDao;
import fr.eni.encheres.dal.dao.CategorieDao;
import fr.eni.encheres.dal.dao.RetraitDao;
import fr.eni.encheres.dal.dao.UtilisateurDao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoJdbcImpl implements ArticleDao {

	private static final String INSERT = "insert into ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres,prix_initial,no_utilisateur,no_categorie,no_retrait) VALUES (?,?,?,?,?,?,?,?)";
	private static final String GET_BY_ID = "select * from ARTICLES_VENDUS where no_article= ?";
	private static final String GET_ALL = "select * from ARTICLES_VENDUS";
	private static final String GET_BY_VENDEUR = "select * from ARTICLES_VENDUS where no_utilisateur= ?";
	private static final String UPDATE = "update ARTICLES_VENDUS set nom_article = ?, description = ?,"
			+ "							 date_debut_encheres=?, date_fin_encheres= ?, prix_initial= ?, prix_vente= ?, "
			+ "							 no_utilisateur= ?, no_categorie=?, no_retrait=? where no_article= ? ";
	private static final String DELETE = "delete ARTICLES_VENDUS where no_article = ?";
	private static final String GET_BY_RETRAIT = "select * from ARTICLES_VENDUS where no_retrait=?";

	private static RetraitDao retraitDAO = new RetraitDaoJdbcImpl();

    @Override
    public Article getById(int articleId) throws SQLException, DalException {
        Article article = null;

        try (Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement pstmt = cnx.prepareStatement(GET_BY_ID);
            pstmt.setInt(1, articleId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                article = articleBuilder(rs);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
			throw new DalException(e.getMessage(), e);
        }

        return article;
    }


    @Override
    public List<Article> getAllArticles() throws DalException {
        List<Article> articles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement statement = cnx.prepareStatement(GET_ALL);
            ResultSet rs = statement.executeQuery();
            Article article = null;
            
            while (rs.next()) {
                articles.add(articleBuilder(rs));
                articles.add(article);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return articles;
    }

    @Override
    public List<Article> getAllArticlesByUtilisateur(Utilisateur utilisateur) throws SQLException, DalException {
        List<Article> returnedArticles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()){
            PreparedStatement preparedStatement = cnx.prepareStatement(GET_BY_VENDEUR);
            preparedStatement.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet resultSet = preparedStatement.executeQuery();
            Article article = null;
            
            while (resultSet.next()) {
            	article = articleBuilder(resultSet);
            	returnedArticles.add(article);
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return returnedArticles;
    }


    @Override
    public Article insertArticle(Article ajoutArticle) throws SQLException, DalException, BusinessException {
//        Article articleCree = null;
//        Retrait retraitCree = null;
//        Retrait retraitReturne = null;
//        final String SQL_INSERT = "insert into ARTICLES (idUtilisateur, idCategorie, idRetrait," +
//                "nom, description, dateDebutEncheres," +
//                "dateFinEncheres, prixInitial, prixVente) values (?,?,?,?,?,?,?,?,?)";
//        int idArticleAjout = 0;
//        int idRetraitAjout = 0;
//        String rueUtilisateur = ajoutArticle.getRetrait().getRue();
//        String codePostalUtilisateur = ajoutArticle.getRetrait().getCodePostal();
//        String villeUtilisateur = ajoutArticle.getRetrait().getVille();

    	if (ajoutArticle == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
    	
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(4, ajoutArticle.getNomArticle());
            preparedStatement.setString(5, ajoutArticle.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(ajoutArticle.getDateDebutEncheres()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(ajoutArticle.getDateFinEncheres()));
            preparedStatement.setInt(8, ajoutArticle.getPrixInitial());
            preparedStatement.setInt(8, ajoutArticle.getRetrait().getNoArticle());
            if (ajoutArticle.getPrixVente() != 0) {
                preparedStatement.setInt(9, ajoutArticle.getPrixVente());
            } else {
                preparedStatement.setNull(9, Types.INTEGER);
            }
//            retrait = new Retrait(rueUtilisateur, codePostalUtilisateur, villeUtilisateur);
//            retraitReturne = FactoryDao.getRetraitDao().insertRetrait(retraitCree);
//            idRetraitAjout = retraitReturne.getId();
//            preparedStatement.setInt(3, idRetraitAjout);
//            preparedStatement.setInt(2, ajoutArticle.getCategorie().getId());
//            preparedStatement.setInt(1, ajoutArticle.getUtilisateur().getId());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
            	ajoutArticle.setNoArticle(resultSet.getInt(1));
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return ajoutArticle;
    }

    @Override
    public void updateArticle(Article updateArticle) throws SQLException, DalException {

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(UPDATE);
            requete.setInt(1, updateArticle.getNoUtilisateur().getNoUtilisateur());
            requete.setInt(2, updateArticle.getNoCategorie().getNoCategorie());
            requete.setString(4, updateArticle.getNomArticle());
            requete.setString(5, updateArticle.getDescription());
            requete.setDate(6, java.sql.Date.valueOf(updateArticle.getDateDebutEncheres()));
            requete.setDate(7, java.sql.Date.valueOf(updateArticle.getDateFinEncheres()));
            requete.setInt(8, updateArticle.getPrixInitial());
            requete.setInt(9, updateArticle.getPrixVente());
            requete.setInt(10, updateArticle.getNoArticle());
            if (updateArticle.getRetrait() != null) {
				requete.setInt(3, updateArticle.getRetrait().getNoArticle());
			} else {
				requete.setNull(3, Types.INTEGER);
			}
			requete.setInt(11, updateArticle.getNoArticle());
            requete.executeUpdate();
            
            updateArticle = FactoryDao.getArticleDao().getById(updateArticle.getNoArticle());
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
    }

    private Article articleBuilder(ResultSet rs) throws SQLException, DalException {
    	
        Categorie articleCategorie = this.getArticleCategorie(rs.getInt("noCategorie"));
        Retrait articleRetrait = this.getArticleRetrait(rs.getInt("noRetrait"));
        Utilisateur articleUtilisateur = this.getArticleUtilisateur(rs.getInt("noUtilisateur"));

        Article article = new Article();
        
        article.setNoArticle(rs.getInt("id"));
        article.setNoCategorie(articleCategorie);
        article.setRetrait(articleRetrait);
        article.setNoUtilisateur(articleUtilisateur);
        article.setNomArticle(rs.getString("nom"));
        article.setDescription(rs.getString("description"));
        article.setDateDebutEncheres((rs.getDate("date_debut_encheres").toLocalDate()));
        article.setDateFinEncheres((rs.getDate("date_fin_encheres").toLocalDate()));
        article.setPrixInitial(rs.getInt("prixInitial"));
        article.setPrixVente(rs.getInt("prixVente"));

        return article;
    }

    private Categorie getArticleCategorie(int categorieId) throws SQLException, DalException {
        Categorie articleCategorie = null;
        CategorieDao categorieDao = FactoryDao.getCategorieDao();
        try {
            articleCategorie = categorieDao.getCategorieByID(categorieId);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return articleCategorie;
    }

    private Retrait getArticleRetrait(int retraitId) throws SQLException, DalException {
        Retrait articleRetrait = null;
        RetraitDao retraitDao = FactoryDao.getRetraitDao();
        try {
            articleRetrait = retraitDao.getRetraitById(retraitId);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return articleRetrait;
    }

    private Utilisateur getArticleUtilisateur(int utilisateurId) throws SQLException, DalException {
        Utilisateur articleUtilisateur = null;
        UtilisateurDao utilisateurDao = FactoryDao.getUtilisateurDao();
        try {
            articleUtilisateur = utilisateurDao.getById(utilisateurId);
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        return articleUtilisateur;
    }

    /**
     * @param articleSuppression Récupère un boolean si l'execution du delete est ok
     * @return
     * @throws DalException
     */
    public void deleteArticle(int articleSuppression) throws SQLException, DalException {

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(DELETE);
            stmt.setInt(1, articleSuppression);
            
         // Gestion des dépendances
         			Article article = this.getById(articleSuppression);
         			article.setNoUtilisateur(null);
          			article.setNoCategorie(null);
                   	        article.setRetrait(null);
         			retraitDAO.deleteRetrait(article.getRetrait().getNoArticle());

         			// Supprimer l'article
         			stmt.executeUpdate();

        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return;
    }


	@Override
	public List<Article> getByRetrait(Retrait retrait) throws BusinessException {
		List<Article> listeArticleVendu = new ArrayList<Article>();

		try (Connection cnx = ConnectionProvider.getConnection()) {

			Article articleVendu = null;

			PreparedStatement statement = cnx.prepareStatement(GET_BY_RETRAIT);
			statement.setBoolean(1, retrait.getRetrait());

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				articleVendu = articleBuilder(rs);
				listeArticleVendu.add(articleVendu);
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_RETRAIT_ECHEC);
			throw businessException;

		}
		return listeArticleVendu;
	}


	@Override
	public void deleteArticle(Article articleSuppression) throws DalException {
		// TODO Auto-generated method stub
		
	}
}





















