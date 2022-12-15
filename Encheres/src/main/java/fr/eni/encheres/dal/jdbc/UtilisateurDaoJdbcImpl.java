package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.dao.CategorieDao;
import fr.eni.encheres.dal.dao.RetraitDao;
import fr.eni.encheres.dal.dao.UtilisateurDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDaoJdbcImpl implements UtilisateurDao {

	private static final String INSERT = "INSERT INTO UTILISATEURS VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL = "SELECT * FROM UTILISATEURS";
	private static final String GET_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur=?";
	private static final String UPDATE = "UPDATE UTILISATEURS SET nom=?, prenom=?,"
			+ "email=?, telephone=?, rue=?, code_postal=?, ville=?," + " mot_de_passe=?, credit=?, administrateur=? WHERE no_utilisateur=?";
	private static final String DELETE = "DELETE UTILISATEURS WHERE no_utilisateur=?";
	private static final String GET_ARTICLES_VENDUS = "select * from ARTICLES_VENDUS WHERE no_utilisateur=?";
	private static final String GET_BY_PSEUDO ="select * from UTILISATEURS WHERE pseudo=? ";
	private static final String GET_ALL_PSEUDOS = "SELECT pseudo FROM UTILISATEURS";


	private static CategorieDao categorieDao = new CategorieDaoJdbcImpl();
	private static RetraitDao retraitDao = new RetraitDaoJdbcImpl();


  @Override
  public Utilisateur getById(int id) throws SQLException, DalException {
    Utilisateur utilisateur = null;

    try(Connection cnx = ConnectionProvider.getConnection()) {
      PreparedStatement requete = cnx.prepareStatement(GET_BY_ID);
      requete.setInt(1, id);
      ResultSet resultSet = requete.executeQuery();

      if(resultSet.next()){
        utilisateur = utilisateurBuilder(resultSet);
      }
    }catch(Exception e){
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEURS_ECHEC);
      throw new DalException(e.getMessage(), e);
    }
    return utilisateur;
  }

  @Override
  public List<Utilisateur> getAllUtilisateur() throws SQLException, DalException {
    List<Utilisateur> listUtilisateur = new ArrayList<>();
    try(Connection cnx = ConnectionProvider.getConnection()) {
      PreparedStatement requete = cnx.prepareStatement(GET_ALL);
      ResultSet rs = requete.executeQuery();
      while (rs.next()){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurBuilder(rs);
        listUtilisateur.add(utilisateur);
      }
    }catch (Exception e){
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEURS_ECHEC);
      throw new DalException(e.getMessage(), e);
    }
    return listUtilisateur;
  }

  @Override
  public Utilisateur selectLogin(String pseudoOuEmail, String password) throws SQLException, DalException {
    Utilisateur utilisateur = null;

    try (Connection cnx = ConnectionProvider.getConnection()) {

      PreparedStatement preparedStatement = cnx.prepareStatement(GET_BY_PSEUDO);
      preparedStatement.setString(1, pseudoOuEmail);
      preparedStatement.setString(2, pseudoOuEmail);
      preparedStatement.setString(3, password);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        utilisateur = utilisateurBuilder(resultSet);
      }
    } catch (Exception e) {
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
      throw new DalException(e.getMessage(), e);
    }

    return utilisateur;
  }
  // méthode pour la récuperation de l'utilisateur demandant son mot de passe
  @Override
  public Utilisateur selectLogin(String pseudoOuEmail) throws SQLException, DalException {
    Utilisateur utilisateur = null;
    final String sqlLogin = "select * from UTILISATEURS where (email=? or pseudo=?)";

    try (Connection cnx = ConnectionProvider.getConnection()) {

      PreparedStatement preparedStatement = cnx.prepareStatement(sqlLogin);
      preparedStatement.setString(1, pseudoOuEmail);
      preparedStatement.setString(2, pseudoOuEmail);
      ResultSet resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        utilisateur = utilisateurBuilder(resultSet);
      }
    } catch (Exception e) {
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
      throw new DalException(e.getMessage(), e);
    }

    return utilisateur;
  }

  @Override
  public void update(Utilisateur utilisateur) throws SQLException, DalException {
    try(Connection cnx = ConnectionProvider.getConnection()) {
      PreparedStatement requete = cnx.prepareStatement(UPDATE);
      requete.setString(1, utilisateur.getPseudo());
      requete.setString(2, utilisateur.getNom());
      requete.setString(3, utilisateur.getPrenom());
      requete.setString(4, utilisateur.getEmail());
      //gestion du null
      if(utilisateur.getTelephone() == null){
        requete.setNull(5, Types.VARCHAR);
      }else {
        requete.setString(5, utilisateur.getTelephone());
      }
      requete.setString(6, utilisateur.getRue());
      requete.setString(7, utilisateur.getCodePostal());
      requete.setString(8, utilisateur.getVille());
      requete.setString(9, utilisateur.getMotDePasse());
      requete.setInt(10, utilisateur.getId());
      requete.executeUpdate();

    }catch(Exception e){
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
      throw  new DalException(e.getMessage(), e);
    }
  }
  

  @Override
  public Utilisateur insert(Utilisateur ajoutUtilisateur) throws SQLException, DalException {
	  
    if (ajoutUtilisateur == null) {
        BusinessException businessException = new BusinessException();
	businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
	throw businessException;
	}
	  
    try (Connection cnx = ConnectionProvider.getConnection()) {
      PreparedStatement preparedStatement = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, ajoutUtilisateur.getPseudo());
      preparedStatement.setString(2, ajoutUtilisateur.getNom());
      preparedStatement.setString(3, ajoutUtilisateur.getPrenom());
      preparedStatement.setString(4, ajoutUtilisateur.getEmail());
      preparedStatement.setString(5, ajoutUtilisateur.getTelephone());
      preparedStatement.setString(6, ajoutUtilisateur.getRue());
      preparedStatement.setString(7, ajoutUtilisateur.getCodePostal());
      preparedStatement.setString(8, ajoutUtilisateur.getVille());
      preparedStatement.setString(9, ajoutUtilisateur.getMotDePasse());
       preparedStatement.setInt(10, 0);
      preparedStatement.setBoolean(11, false);    
	    
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) {
        idAjout = resultSet.getInt(1);
       // logger.info("Nouvel utilisateur crée avec l'id : " + idAjout + " "+ajoutUtilisateur.toString())  ;
        utilisateurCree = getById(idAjout);
      }
    }
    catch(Exception e){
      e.printStackTrace();
      e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);

    }
    return utilisateurCree ;
  }

  @Override
  public void delete(int id) throws SQLException, DalException {

    try(Connection cnx = ConnectionProvider.getConnection()) {

      PreparedStatement requete = cnx.prepareStatement(DELETE);

      requete.setInt(1, id);
      requete.executeUpdate();

    }catch (Exception e){
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
      throw new DalException(e.getMessage(), e);
    }
  }

  @Override
  public boolean verifEmail(String email, int id) throws SQLException, DalException {
    boolean verifEmail = false;
    final String VERIF_EMAIL ="SELECT * FROM UTILISATEURS WHERE email = ?  AND id <> ?";
      try (Connection cnx = ConnectionProvider.getConnection()) {
      {
        PreparedStatement preparedStatement = cnx.prepareStatement(VERIF_EMAIL);
        preparedStatement.setString(1,email);
        preparedStatement.setInt(2,id);
        ResultSet rs = preparedStatement.executeQuery();
        verifEmail = rs.next();
        System.out.println(verifEmail);
      }
      }catch(Exception e)
    {
    	  e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
			throw new DalException(e.getMessage(), e);
    }

    return verifEmail;
  }

  @Override
  public boolean verifPseudo(String pseudo,int id) throws SQLException, DalException {
    boolean verifPseudo = false;
    final String VERIF_PSEUDO =  "SELECT * FROM UTILISATEURS WHERE pseudo = ? AND id <> ? ";
    try (Connection cnx = ConnectionProvider.getConnection()) {
    {
      PreparedStatement preparedStatement = cnx.prepareStatement(VERIF_PSEUDO);
      preparedStatement.setString(1,pseudo);
      preparedStatement.setInt(2,id);
      ResultSet rs = preparedStatement.executeQuery();
      verifPseudo = rs.next();
      System.out.println(verifPseudo);
    }
    }catch(Exception e)
    {
    	e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
		throw new DalException(e.getMessage(), e);
    }

    return verifPseudo;
  }

  private Utilisateur utilisateurBuilder(ResultSet rs) throws SQLException {
    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setId(rs.getInt("id"));
    utilisateur.setPseudo(rs.getString("pseudo"));
    utilisateur.setNom(rs.getString("nom"));
    utilisateur.setPrenom(rs.getString("prenom"));
    utilisateur.setEmail(rs.getString("email"));
    utilisateur.setTelephone(rs.getString("telephone"));
    utilisateur.setRue(rs.getString("rue"));
    utilisateur.setCodePostal(rs.getString("codePostal"));
    utilisateur.setVille(rs.getString("ville"));
    utilisateur.setMotDePasse(rs.getString("motDePasse"));
    utilisateur.setCredit(rs.getInt("credit"));
    utilisateur.setAdministration(rs.getBoolean("administrateur"));

    return utilisateur;
  }

  public void modifMotDePasse(String motDePasse, String cle) throws DalException, SQLException {
    char arg1 = cle.charAt(0);
    String arg2 = cle.substring(1, 4);
    String arg3 = cle.substring(cle.indexOf('A') + 1, cle.length() - 2);
    String arg4 = cle.substring(cle.length() - 2, cle.length()-1);
    String arg5 = cle.substring(4, cle.indexOf('A'));
    String arg6 = cle.substring(cle.length()-1);



    String SQL_UPDATE_MDP = "UPDATE UTILISATEURS SET motDePasse = '"+motDePasse+"'  where  nom like '_"+arg1+"%' and codePostal like '_"+arg2+"_' and credit = "+arg3+" and motDePasse like '_"+arg4+"%' and id="+arg5 +"and len(motDePasse) ="+arg6
    +" " ;

    try (Connection cnx = ConnectionProvider.getConnection()) {
    {
      PreparedStatement preparedStatement = cnx.prepareStatement(SQL_UPDATE_MDP,Statement.RETURN_GENERATED_KEYS);
      int rs = preparedStatement.executeUpdate();

      if(rs ==0){
        throw new DalException("Votre lien n'est plus valide, veuillez en redemandez un en cliquant sur créer un nouveau mot de passe");
      }
    }
  } catch (Exception e) {
      e.printStackTrace();
    }

  }


@Override
public Utilisateur getByPseudo(String pseudo) throws BusinessException {
	Utilisateur utilisateur = null;
	
	try (Connection cnx = ConnectionProvider.getConnection()) {
		PreparedStatement statement = cnx.prepareStatement(GET_BY_PSEUDO);
		statement.setString(1, pseudo);
		
		ResultSet rs = statement.executeQuery();
		
		if(rs.next()) {
			utilisateur= utilisateurBuilder(rs);
		}
	} catch (Exception e) {
		e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEURS_ECHEC);
		throw businessException;
	}
		
	return utilisateur;
}

@Override
public List<String> getAllPseudos() throws BusinessException {
	List<String> pseudos = new ArrayList<>();
	
	try(Connection cnx = ConnectionProvider.getConnection()) {
		PreparedStatement statement = cnx.prepareStatement(GET_ALL_PSEUDOS);
		
		ResultSet rs = statement.executeQuery();
		
		while (rs.next()) {
			pseudos.add(rs.getString("pseudo"));
		}
				
	} catch (Exception e) {
		e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_UTILISATEURS_ECHEC);
		throw businessException;
	}
	return pseudos;
}

@Override
public List<Article> getAllArticle(Utilisateur utilisateur) throws BusinessException {
	List<Article> listeArticle = new ArrayList<Article>();

	try (Connection cnx = ConnectionProvider.getConnection()) {
		PreparedStatement statement = cnx.prepareStatement(GET_ARTICLES_VENDUS);
		statement.setInt(1, utilisateur.getId());
		
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Article article = new Article();
			article.setNoArticle(rs.getInt("no_article"));
			article.setNomArticle(rs.getString("nom_article"));
			article.setDescription(rs.getString("description"));
			article.setDateDebutEncheres((rs.getDate("date_debut_encheres").toLocalDate()));
			article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
			article.setPrixInitial(rs.getInt("prix_initial"));
			article.setPrixVente(rs.getInt("prix_vente"));
			article.setNoUtilisateur(utilisateur);
			article.setNoCategorie(categorieDao.getCategorieByID(rs.getInt("no_categorie")));
			article.setRetrait(retraitDao.getRetraitById(rs.getInt("no_retrait")));
			listeArticle.add(article);
		}
	} catch (Exception e) {
		e.printStackTrace();
		BusinessException businessException = new BusinessException();
		businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
		throw businessException;
	}
	return listeArticle;
}

@Override
public Utilisateur updateCredit(int noUtilisateur, int newCredit) throws DalException {
	 
     try (Connection cnx = ConnectionProvider.getConnection()) {
         String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
         PreparedStatement stmt = cnx.prepareStatement(UPDATE_CREDIT);
         stmt.setInt(1, newCredit);
         stmt.setInt(2, noUtilisateur);
         stmt.executeUpdate();
         cnx.close();
         
     } catch (Exception e) {
         e.printStackTrace();
         BusinessException businessException = new BusinessException();
		 businessException.ajouterErreur(CodesResultatDAL.LECTURE_ARTICLES_ECHEC);
		 throw new DalException(e.getMessage(), e);
     }
	return updateCredit(noUtilisateur, newCredit);
	}
}


