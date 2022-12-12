package fr.eni.encheres.dal.jdbc;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.CodesResultatDAL;
import fr.eni.encheres.dal.ConnectionProvider;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.dao.ArticleDao;
import fr.eni.encheres.dal.dao.RetraitDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetraitDaoJdbcImpl implements RetraitDao {

	private static final String INSERT = "insert into RETRAITS (rue, code_postal, ville) values (?,?,?)";
	private static final String GET_BY_ID = "SELECT * FROM RETRAITS WHERE no_retrait = ?";
	private static final String GET_ALL = "SELECT * FROM RETRAITS";
	private static final String UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_retrait=?";
	private static final String DELETE = "DELETE RETRAITS WHERE no_retrait = ?";

	private static ArticleDao articleDao = new ArticleDaoJdbcImpl();
	
	
    @Override
    public Retrait getRetraitById(int retraitId) throws SQLException, DalException {
        Retrait retrait = null;
        final String SELECT_BY_ID = "select * from RETRAITS where id = ?";

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, retraitId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                retrait = retraitBuilder(resultSet);
            }
        } catch(Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_RETRAIT_ECHEC);
            throw new DalException(e.getMessage(), e);
        }

        return retrait;
    }

    @Override
    public Retrait insertRetrait(Retrait ajoutRetrait) throws SQLException, DalException {
        Retrait retraitCree = null;

        int idAjout = 0;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ajoutRetrait.getRue());
            preparedStatement.setString(2, ajoutRetrait.getCodePostal());
            preparedStatement.setString(3, ajoutRetrait.getVille());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                idAjout = resultSet.getInt(1);
                retraitCree = getRetraitById(idAjout);
            }
        } catch(Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
        return retraitCree;
    }

    public void updateRetrait(Retrait modifRetrait) throws  DalException {

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement preparedStatement = cnx.prepareStatement(UPDATE);
            preparedStatement.setString(1, modifRetrait.getRue());
            preparedStatement.setString(2, modifRetrait.getCodePostal());
            preparedStatement.setString(3, modifRetrait.getVille());
            preparedStatement.setInt(4,modifRetrait.getNoArticle());
            preparedStatement.executeUpdate();


        } catch(Exception e){
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.UPDATE_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
    }

    /**
     *
     * @param deleteRetrait
     * Récupère un boolean si l'execution du delete est ok
     * @return
     * @throws DalException
     */
    public void deleteRetrait(int deleteRetrait) throws  DalException {

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement  stmt= cnx.prepareStatement(DELETE);
            stmt.setInt(1,deleteRetrait);
            Retrait retrait =getRetraitById(deleteRetrait);
			List<Article> listeArticle = articleDao.getByRetrait(retrait);
			for (Article articleVendu : listeArticle) {
				articleVendu.setRetrait(null);
			}
			
			stmt.executeUpdate();

        } catch(Exception e){
        	e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DELETE_OBJET_ECHEC);
            throw new DalException(e.getMessage(), e);
        }
    }

    public boolean validerRetrait(int numeroRetrait) throws DalException, SQLException {
        boolean retraitValider = false;
        System.out.println("entre dans la dao");

        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = cnx.prepareStatement(GET_BY_ID);
            stmt.setInt(1,numeroRetrait);
            retraitValider = (!(stmt.executeUpdate() == 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  retraitValider;
    }


    private Retrait retraitBuilder(ResultSet rs) throws SQLException {
        Retrait retrait = new Retrait();
        retrait.setNoArticle(rs.getInt("id"));
        retrait.setRue(rs.getString("rue"));
        retrait.setCodePostal(rs.getString("codePostal"));
        retrait.setVille(rs.getString("ville"));
        retrait.setRetrait(rs.getBoolean("retrait"));

        return retrait;
    }

	@Override
	public List<Retrait> getAll() throws BusinessException {
		List<Retrait> retraits = new ArrayList<>();

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement statement = cnx.prepareStatement(GET_ALL);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Retrait retrait = new Retrait();
				retrait.setNoArticle(rs.getInt("no_retrait"));
				retrait.setRue(rs.getString("rue"));
				retrait.setCodePostal(rs.getString("code_postal"));
				retrait.setVille(rs.getString("ville"));
				retraits.add(retrait);
			}

		} catch (Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_RETRAIT_ECHEC);
			throw businessException;

		}
		return retraits;	
	}

}
