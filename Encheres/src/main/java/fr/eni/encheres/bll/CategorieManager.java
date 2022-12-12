package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.CategorieDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieManager {

    private static CategorieManager instance;
    private static CategorieDao categorieDao;


    //Constructeur privé => PATTERN SINGLETON
    private CategorieManager (){categorieDao = FactoryDao.getCategorieDao(); }

    //Permet de récupérer l'instance (créee une seule fois)
    public static CategorieManager getInstance() {
        if (instance == null) {
            return new CategorieManager();
        }
        return instance;
    }

    public static List<Categorie> selectAllCategorie() throws SQLException, BllException, BusinessException {
    	return categorieDao.getAllCategorie();
    }

    public Categorie selectCategorieByID(int categorieId) throws SQLException, BllException {
        Categorie categorie = new Categorie();
        try{
            categorie = categorieDao.getCategorieByID(categorieId);
        }catch(SQLException | DalException e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException("Error selectById CategorieManager bll" + e.getMessage());
        }
        return categorie;
    }

    public Categorie selectByName(String nomCategorie) throws SQLException, BllException{
        Categorie categorie = new Categorie();
        try{
            categorie = categorieDao.selectByName(nomCategorie);
        }catch(SQLException | DalException e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException("Error selectByName CategorieManager bll" + e.getMessage());
        }
        return categorie;
    }

    public Categorie insert(Categorie categorie) throws SQLException, BllException, DalException {
        Categorie categorieRetourner = null;
        categorieRetourner = categorieDao.insert(categorie);
        return  categorieRetourner;
    }

    public void update(Categorie categorie) throws SQLException, BllException, DalException {
          categorieDao.update(categorie);
    }

    public void delete(int id) throws SQLException, BllException, DalException {
        categorieDao.delete(id);
    }
}
