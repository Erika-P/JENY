package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.ArticleDao;
import fr.eni.encheres.dal.dao.EnchereDao;
import fr.eni.encheres.dal.dao.UtilisateurDao;

public class EnchereDaoJdbcImpl implements EnchereDao {

	private static final String INSERT = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_article, no_utilisateur, remporte) VALUES(?,?,?,?,?)";
	private static final String GET_ALL = "SELECT * FROM ENCHERES";
	private static final String GET_BY_ID = "SELECT * FROM ENCHERES WHERE no_enchere=?";
	private static final String GET_BY_ENCHERISSEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur=?";
	private static final String GET_REMPORTES_PAR_ENCHERISSEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur=? AND remporte=?";
	private static final String UPDATE = "UPDATE ENCHERES SET date_enchere=?, montant_enchere=?,"  +
											"no_article=?, no_utilisateur=?, remporte=? WHERE no_enchere=?";
	private static final String DELETE = "DELETE ENCHERES WHERE no_enchere=?";
	private static final String GET_ALL_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE no_article=?";

	
    @Override
    public List<Enchere> getAllEnchere() throws SQLException, DalException {
        List<Enchere> enchereList = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_ALL);
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                enchereList.add(enchere);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);

        }
        return enchereList;
    }

    /**
     * @param
     * @return
     * @throws SQLException
     * @throws DalException
     * @apiNote récupérer la liste d'enchêre en cours ou l'utilisateur a participé ( au moins une enchère)
     * avec filtre en entrée pour ajouter ou non le controle par catégorie et saisie nom
     */
    @Override
    public List<Enchere> getEnchereByUtilisateur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {

        String stringFiltreCategorie = "";
        List<Enchere> enchereList = new ArrayList<>();
        if (!filtreNom.equals("0")) {
            filtreNom = "and a.nom like '%" + filtreNom + "%' ";
        } else {
            filtreNom = "";
        }
        if (!(filtreCategorie == 0)) {
            stringFiltreCategorie = "and a.idCategorie =" + Integer.toString(filtreCategorie) + " ";
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_REMPORTES_PAR_ENCHERISSEUR + filtreNom + stringFiltreCategorie);
            requete.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                enchereList.add(enchere);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return enchereList;
    }
    

    @Override
    public List<Enchere> selectEnchereVendeur(Utilisateur utilisateur, String filtreNom, int filtreCategorie) throws SQLException, DalException {

        String stringFiltreCategorie = "";
        List<Enchere> enchereList = new ArrayList<>();
        if (!filtreNom.equals("0")) {
            filtreNom = "and a.nom like '%" + filtreNom + "%' ";
        } else {
            filtreNom = "";
        }
        if (!(filtreCategorie == 0)) {
            stringFiltreCategorie = "and a.idCategorie =" + Integer.toString(filtreCategorie) + " ";
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_BY_ENCHERISSEUR + filtreNom + stringFiltreCategorie);
            requete.setInt(1, utilisateur.getNoUtilisateur());
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                enchereList.add(enchere);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return enchereList;
    }


    @Override
    public List<Enchere> getEnchereByArticle(Article article) throws SQLException, DalException {
        return null;
    }

    @Override
    public Enchere getEnchereByIdArticle(int idArticle) throws SQLException, DalException {
        Enchere enchereRetourner = new Enchere();
        //selectionne l'article avec sont enchere maximum dans une ligne

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_BY_ID);
            requete.setInt(1, idArticle);
            ResultSet rs = requete.executeQuery();
            if (rs.next()) {
                enchereRetourner = enchereBuilder(rs);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return enchereRetourner;
    }

    @Override
    public Enchere selectById(int id) throws SQLException, DalException {
        Enchere enchere = null;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_BY_ID);
            requete.setInt(1, id);
            ResultSet rs = requete.executeQuery();

            //JE RECUPERE TOUTES LES INFORMATION DE LA TABLE ENCHERE
            if (rs.next()) {
                Enchere enchereNew = new Enchere();
                Article article = this.getEnchereArticle(rs.getInt("idArticle"));
                enchereNew.setNoArticle(article);
                Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("idUtilisateur"));
                enchereNew.setNoUtilisateur(utilisateur);
                enchereNew.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
                enchereNew.setMontantEnchere(rs.getInt("montantEnchere"));
                enchere = enchereNew;
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return enchere;
    }

    @Override
    public Enchere addNewEnchere(Utilisateur acheteur, int idArticle, int montantEnchere) throws SQLException, DalException {
        //enchere de retour
        Enchere enchereAdd = new Enchere();
        Enchere enchere = FactoryDao.getEnchereDao().getEnchereByIdArticle(idArticle);
        //varaible pour recupere l'id de la nouvelle enchere
        int idAjout = 0;
        //varaiable pour initialiser la date du jour
        //java.sql.Date now = new java.sql.Date( new java.util.Date().getTime() ); ----> avant
        java.sql.Timestamp now = new java.sql.Timestamp(new java.util.Date().getTime()); // ----> apres

        //prix de vente est soit = au prix initial ou soit supperieur
        if ((enchere.getNoArticle().getPrixInitial() <= enchere.getNoArticle().getPrixVente()) || (enchere.getNoArticle().getPrixVente() == 0)) {
            //compare le montantEnchere avec le prix article
            if (enchere.getNoArticle().getPrixVente() < montantEnchere) {
                //controle pour savoir si l'acheteur a deja fais la derniere enchere
                if ((enchere.getNoUtilisateur() == null) || (acheteur.getNoUtilisateur() != enchere.getNoUtilisateur().getNoUtilisateur())) {
                    //controle pour savoir si le credit de l'utilisateur est superrieur au prix de vente
                    if (acheteur.getCredit() >= enchere.getNoArticle().getPrixVente()) {
                        try (Connection cnx = ConnectionProvider.getConnection()) {
                            PreparedStatement requete = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
                            requete.setInt(1, idArticle);
                            requete.setInt(2, acheteur.getNoUtilisateur());
                            //requete.setDate(3, now); // ----> avant
                            requete.setObject(3, now); // ----> apres
                            requete.setInt(4, montantEnchere);
                            requete.executeUpdate();
                            ResultSet rs = requete.getGeneratedKeys();
                            if (rs.next()) {
                                idAjout = rs.getInt(1);
                            }
                        } catch (Exception e) {
                    		e.printStackTrace();
                			BusinessException businessException = new BusinessException();
                			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
                            throw new DalException(e.getMessage(), e);
                        }
                        //mise a jour de l'article apres enchere
                        updateArticleApresEnchere(enchere.getNoArticle(), montantEnchere);

                        if (enchere.getMontantEnchere() > 0) {
                            //ajout du montant de l'enchere a l'ancien encherisseur
                            addCredit(enchere.getNoUtilisateur().getCredit(), enchere.getMontantEnchere(), enchere.getNoUtilisateur());
                        }
                        // débit du montant de l'enchere du nouvel encherisseur
                        deleteCredit(acheteur.getCredit(), montantEnchere, acheteur);
                        //récuperation de l'enchere creer
                        enchereAdd = FactoryDao.getEnchereDao().selectById(idAjout);
                    } else {
                        throw new DalException("credit Acheteur inferieur au montant de l'enchere");
                    }
                } else {
                    throw new DalException("Vous etes deja le dernier encherisseur");
                }
            } else {
                throw new DalException("prix de vente supperieur au montant de l'enchere");
            }
        } else {
            throw new DalException("le prix initial est supperieur aux prix de vente ");
        }
        return enchereAdd;
    }


    private Utilisateur getEnchereUtilisateur(int utilisateurId) throws SQLException, DalException {
        Utilisateur enchereUtilisateur = null;
        UtilisateurDao utilisateurDao = FactoryDao.getUtilisateurDao();
        try {
            enchereUtilisateur = utilisateurDao.getById(utilisateurId);
        } catch (SQLException e) {
    		e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }

        return enchereUtilisateur;
    }

    private Article getEnchereArticle(int articleId) throws SQLException, DalException {
        Article enchereArticle = null;
        ArticleDao articleDao = FactoryDao.getArticleDao();
        try {
            enchereArticle = articleDao.getById(articleId);
        } catch (SQLException e) {
    		e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return enchereArticle;
    }

    private Enchere enchereBuilder(ResultSet rs) throws SQLException, DalException {
        Enchere enchere = new Enchere();
        enchere.setId(rs.getInt("ench_id"));
        Article article = this.getEnchereArticle(rs.getInt("art_id"));
        enchere.setNoArticle(article);
        Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("ench_idUtilisateur"));
        enchere.setNoUtilisateur(utilisateur);
        //enchere.setDateEnchere(rs.getDate("dateEnchere")); ------> avant
        enchere.setDateEnchere((rs.getDate("date_debut_encheres").toLocalDate())); // ------> apres
        enchere.setMontantEnchere(rs.getInt("montantEnchere"));

        return enchere;
    }

    private void deleteCredit(int creditAcheteur, int montantEnchere, Utilisateur acheteur) throws SQLException, DalException {
        Utilisateur newUtilisateur = new Utilisateur();
        FactoryDao.getUtilisateurDao().update(newUtilisateur);

    }

    private void addCredit(int creditUtilisateur, int montantEnchere, Utilisateur acheteur) throws SQLException, DalException {
        Utilisateur UtilisateurACredite = new Utilisateur();
        FactoryDao.getUtilisateurDao().update(UtilisateurACredite);
    }

    private void updateArticleApresEnchere(Article article, int montantEnchere) throws SQLException, DalException {
        Article newArticle = new Article();
        FactoryDao.getArticleDao().updateArticle(newArticle);
    }

    /* NOUVELLE METHODE POUR AFFICHER LES REQUETES UTILISATEURS
     * */
    @Override
    public List<Enchere> afficherRequete(String condition) throws SQLException, DalException {
        List<Enchere> listeEnchere = new ArrayList<>();
        String addCondition = "";

        if (!condition.isEmpty()) {
            addCondition = condition;
        } else {
            addCondition = " ";
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_ALL_BY_ARTICLE + addCondition);
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                listeEnchere.add(enchere);
            }
        } catch (Exception e) {
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return listeEnchere;
    }

    @Override
    public List<Enchere> selectAllEncheresVendeur(int idUtilisateur, List<String> conditions, int idCategorie, String nomTitreArticle) throws DalException {
        List<Enchere> listeEnchere = new ArrayList<>();

        final String venteNonDebute = " (a.dateDebutEncheres > now())";

        final String venteEnCours = "(a.dateFinEncheres> now() and a.dateDebutEncheres<= now())";

        final String venteTermine = "(a.dateFinEncheres <= now())";

        StringBuilder requete = new StringBuilder();
        requete.append(GET_ALL_BY_ARTICLE);

        for (String condition : conditions) {

            if (conditions.indexOf(condition) > 0) {
                requete.append(" or ");
            } else {
                requete.append(" and ( ");
            }
            if (condition.equals("venteNonDebute")) {
                requete.append(venteNonDebute);
            }
            if (condition.equals("venteEnCours")) {
                requete.append(venteEnCours);
            }
            if (condition.equals("venteTermine")) {
                requete.append(venteTermine);
            }
        }
        if (conditions.size() > 0) {
            requete.append(")");
        }

        if (!nomTitreArticle.isEmpty()) {
            requete.append(" and a.nom like '%" + nomTitreArticle + "%' ");
        }

        if (idCategorie > 0) {

            requete.append(" and a.idCategorie = " + idCategorie);
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmtRequete = cnx.prepareStatement(requete.toString());
            pstmtRequete.setInt(1, idUtilisateur);
            ResultSet rs = pstmtRequete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                enchere = enchereBuilder(rs);
                listeEnchere.add(enchere);
            }
        } catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return listeEnchere;
    }

    @Override
    public List<Enchere> selectEnchereByMontantMax(int idArticle) throws SQLException, DalException {
        List<Enchere> listEnchereMax = new ArrayList<>();
        final String SELECT_ENCHERE_MONTANT_MAX = "select e.idUtilisateur as id_utilisateur,max(e.montantEnchere) as montant_Enchere from ENCHERES e inner join UTILISATEURS u on e.idUtilisateur = u.id where e.idArticle=? GROUP BY e.idUtilisateur, e.idArticle ORDER BY max(e.montantEnchere) desc";

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ENCHERE_MONTANT_MAX);
            requete.setInt(1, idArticle);
            ResultSet rs = requete.executeQuery();
            while (rs.next()) {
                Enchere enchere = new Enchere();
                Utilisateur utilisateur = this.getEnchereUtilisateur(rs.getInt("id_utilisateur"));
                enchere.setNoUtilisateur(utilisateur);
                enchere.setMontantEnchere(rs.getInt("montant_Enchere"));
                listEnchereMax.add(enchere);
            }
        } catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_ENCHERES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return listEnchereMax;
    }

    

	@Override
	public Enchere insert(Enchere enchere) throws BusinessException {
		if (enchere == null) {
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement statement = cnx.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(enchere.getDateEnchere()));
            statement.setInt(2, enchere.getMontantEnchere());
            statement.setInt(3, enchere.getNoArticle().getNoArticle());
            statement.setInt(4, enchere.getNoUtilisateur().getId());
           
            statement.executeUpdate();
            
            ResultSet rs = statement.getGeneratedKeys();
            
            if (rs.next()) {
				enchere.setId(rs.getInt(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		return enchere;	}

	@Override
	public void update(Enchere enchere) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement statement = cnx.prepareStatement(UPDATE);
			statement.setDate(1, Date.valueOf(enchere.getDateEnchere()));
			statement.setInt(2, enchere.getMontantEnchere());
			statement.setInt(3, enchere.getNoArticle().getNoArticle());
			statement.setInt(4, enchere.getNoUtilisateur().getId());
			statement.setInt(6, enchere.getId());
            
			statement.executeUpdate();
           
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
			throw businessException;
		}
	}

	@Override
	public void delete(int id) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement statement = cnx.prepareStatement(DELETE);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw businessException;
		}
	}
}
