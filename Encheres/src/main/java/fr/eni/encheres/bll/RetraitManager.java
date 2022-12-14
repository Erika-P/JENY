package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.RetraitDao;

import java.sql.SQLException;

public class RetraitManager {

    private static RetraitManager instance;
    private static RetraitDao retraitDao;


    //Private constractor
    private RetraitManager() { retraitDao = FactoryDao.getRetraitDao(); }

    //Only one instance
    public static RetraitManager getInstance() {
        if (instance == null) {
            return new RetraitManager();
        }
        return instance;
    }

    public Retrait getRetraitById(int retraitId) throws SQLException, BllException {
        Retrait retrait = new Retrait();
        try {
            retrait = retraitDao.getRetraitById(retraitId);
        } catch(SQLException | DalException e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException("Error getRetraitById RetraitManager " + e.getMessage());
        }
        return retrait;
    }

    public Retrait addNewRetrait(Retrait newRetrait) throws Exception {
        Retrait addedRetrait = null;
        if (newRetrait.getRue().equals("")) {
            throw new Exception("Le point de retrait doit avoir un rue");
        } else if (newRetrait.getCodePostal().equals("")) {
            throw new Exception("Le point de retrait doit avoir un code postal");
        } else if (newRetrait.getVille().equals("")) {
            throw new Exception("Le point de retrait doit avoir une ville");
        } else {
            addedRetrait = retraitDao.insertRetrait(newRetrait);
        }
        return addedRetrait;
    }

    public void updateRetrait(Retrait modifRetrait) throws DalException {
        retraitDao.updateRetrait(modifRetrait);
    }
    
    public void deleteRetrait(int deleteRetrait) throws DalException {
        retraitDao.deleteRetrait(deleteRetrait);
    }

    public boolean validerRetrait(int numeroRetrait) throws DalException, SQLException{
        System.out.println("entré dans la bll");
        boolean retraitValider = false;
        retraitValider = retraitDao.validerRetrait(numeroRetrait);
        return retraitValider;
    }




}





















