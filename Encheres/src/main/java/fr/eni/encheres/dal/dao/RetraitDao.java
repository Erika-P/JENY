package fr.eni.encheres.dal.dao;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface RetraitDao {

	public Retrait getRetraitById(int retraitId)throws SQLException, DalException;
	
	public List<Retrait> getAll() throws BusinessException;

	public Retrait insertRetrait(Retrait retrait) throws SQLException, DalException;

	public void updateRetrait(Retrait modifRetrait) throws  DalException;

	public void deleteRetrait(int deleteRetrait) throws DalException;

	public boolean validerRetrait(int numeroRetrait) throws DalException, SQLException;
}
