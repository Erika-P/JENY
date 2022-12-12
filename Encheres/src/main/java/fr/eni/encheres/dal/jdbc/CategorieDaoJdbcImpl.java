package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.dao.CategorieDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDaoJdbcImpl implements CategorieDao {

	private static final String INSERT = "insert into CATEGORIES values (?)";
	private static final String GET_BY_ID = "select * from CATEGORIES where no_categorie=?";
	private static final String GET_ALL = "select * from CATEGORIES";
	private static final String UPDATE = "update CATEGORIES set libelle = ? where no_categorie=?";
	private static final String DELETE = "delete CATEGORIES where no_categorie=?";
	private static final String GET_BY_LIBELLE="select * from CATEGORIES where libelle=?";


    @Override
    public List<Categorie> getAllCategorie() throws BusinessException {
        List<Categorie> listCategorie = new ArrayList<>();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_ALL);
            ResultSet rs = requete.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie();
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("libelle"));
                listCategorie.add(categorie);
            }
        }catch (Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_ECHEC);
            throw businessException;
        }
        return listCategorie;
    }

    @Override
    public Categorie getCategorieByID(int CategorieId) throws SQLException, DalException {
        Categorie categorie = null;
      
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement(GET_BY_ID);
            preparedStatement.setInt(1, CategorieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categorie = categorieBuilder(resultSet);
            }
        } catch(Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return categorie;
    }

    @Override
    public Categorie selectByName(String nomCategorie) throws SQLException, DalException {
        Categorie categorie = null;
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(GET_BY_LIBELLE);
            requete.setString(1, nomCategorie);
            ResultSet rs = requete.executeQuery();
            if (rs.next()){
                categorie = categorieBuilder(rs);
            }
        }catch (Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_CATEGORIES_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return categorie;
    }

    private Categorie categorieBuilder(ResultSet rs) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setNoCategorie(rs.getInt("no_categorie"));
        categorie.setLibelle(rs.getString("libelle"));
        return categorie;
    }



    /* METHODE POUR L ADMINISTRATION */
    @Override
    public Categorie insert(Categorie categorie) throws SQLException, DalException {

        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            requete.setString(1, categorie.getLibelle());
            requete.executeUpdate();
            ResultSet rs = requete.getGeneratedKeys();
            if(rs.next()){
            	categorie.setNoCategorie(rs.getInt(1));
            }
        }catch (Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.ERROR_SQL_INSERT);
            throw new DalException(e.getMessage(), e);
        }
		return categorie;
    }
    
    
    @Override
    public void update(Categorie categorie) throws SQLException, DalException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(UPDATE);
            requete.setString(1, categorie.getLibelle());
            requete.setInt(2, categorie.getNoCategorie());
            requete.executeUpdate();
           
        }catch (Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
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

}























