package fr.eni.encheres.bll;

import fr.eni.encheres.BusinessException;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DalException;
import fr.eni.encheres.dal.FactoryDao;
import fr.eni.encheres.dal.dao.UtilisateurDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilisateurManager {

    private static UtilisateurManager instance;
    private static UtilisateurDao utilisateurDao;
    
    private static BusinessException businessException = new BusinessException();

    //Constructeur privé => PATTERN SINGLETON
    public UtilisateurManager() {
        utilisateurDao = FactoryDao.getUtilisateurDao();
    }

    //Permet de récupérer l'instance (créee une seule fois)
    public static UtilisateurManager getInstance() {
        if (instance == null) {
            return new UtilisateurManager();
        }
        return instance;
    }

    public Utilisateur getUtilisateurLogin(String pseudoOuEmail, String password) throws SQLException, BllException {
        Utilisateur utilisateur = null;

        try {
            utilisateur = utilisateurDao.selectLogin(pseudoOuEmail, password);
        } catch (SQLException | DalException  e) {
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException(e.getMessage(), e);
        }
        return utilisateur;
    }

    /**
     *
     * @param pseudoOuEmail
     * @return utilisateurExist
     * Vérifier l'existence de l'utilisatuer en base
     * @throws SQLException
     * @throws BllException
     */
    public boolean verifUtilisateurLogin(String pseudoOuEmail) throws SQLException, BllException {
        boolean utilisateurExist= false;

        try {
            boolean verifEmail = utilisateurDao.verifEmail(pseudoOuEmail, 0);
            boolean verifPseudo = utilisateurDao.verifPseudo(pseudoOuEmail, 0);
            if((verifEmail & !verifPseudo)||(!verifEmail & verifPseudo)){
                utilisateurExist= true;
            }

        } catch (DalException e) {
            e.printStackTrace();
        }
        return utilisateurExist;
    }

    public Utilisateur selectById(int id) throws SQLException, BllException {
        Utilisateur utilisateur = null;
        try {
            utilisateur = utilisateurDao.getById(id);
        } catch (SQLException | DalException e) {
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException(e.getMessage(), e);
        }
        return utilisateur;
    }

    public Utilisateur update(Utilisateur utilisateur) throws Exception {
        Utilisateur utilisateurRetourne = null;
        formatEmail(utilisateur);
        formatPseudo(utilisateur);
        //VERIFIER PSEUDO ET EMAIL
        boolean verifEmail = utilisateurDao.verifEmail(utilisateur.getEmail(), utilisateur.getId());
        boolean verifPseudo = utilisateurDao.verifPseudo(utilisateur.getPseudo(), utilisateur.getId());
        if ((verifEmail) & (verifPseudo)) {
            throw new Exception("L'email et le pseudo sont déjà présent en base");
        } else if ((verifEmail) & (!verifPseudo)) {
            throw new Exception("L'email saisi est déjà utilisé");
        } else if ((!verifEmail) & (verifPseudo)) {
            throw new Exception("Le pseudo est déjà pris");
        } else {
            try {
                utilisateurDao.update(utilisateur);
            } catch (SQLException | DalException e) {
            	BllException bllException = new BllException();
                bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
                throw new BllException(e.getMessage(), e);
            }
        }
        return utilisateurRetourne;
    }

    public Utilisateur insert(Utilisateur ajoutUtilisateur) throws Exception {
        Utilisateur utilisateur = null;
        formatEmail(ajoutUtilisateur);
        formatPseudo(ajoutUtilisateur);
        boolean verifEmail = utilisateurDao.verifEmail ( ajoutUtilisateur.getEmail(), ajoutUtilisateur.getId());
        boolean verifPseudo = utilisateurDao.verifPseudo ( ajoutUtilisateur.getPseudo(), ajoutUtilisateur.getId());

        if ((verifEmail) & (verifPseudo)) {
            throw new Exception("L'email et le pseudo sont déjà présent en base");
        } else if ((verifEmail) & (!verifPseudo)) {
            throw new Exception("L'email saisi est déjà utilisé");
        } else if ((!verifEmail) & (verifPseudo)) {
            throw new Exception("Le pseudo est déjà pris");
        } else {
            utilisateur = utilisateurDao.insert(ajoutUtilisateur);
        }
        return utilisateur;
    }

    public void delete(int id) throws Exception {
        try{
          utilisateurDao.delete(id);
        }catch(SQLException | DalException e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new Exception("Impossible du supprimer le membre");
        }
    }

    public void formatEmail(Utilisateur utilisateur) throws Exception {
        String regExpression = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(utilisateur.getEmail());
        boolean formatEmail = m.matches();
        if (!formatEmail) {
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new Exception("L'adresse email n'est pas dans un format valide");
        }

    }
    public void formatPseudo(Utilisateur utilisateur) throws Exception {
        String regExpression = "[a-zA-Z\\d]*";
        Pattern p = Pattern.compile(regExpression);
        Matcher m = p.matcher(utilisateur.getPseudo());
        boolean formatPseudo = m.matches();
        if (!formatPseudo) {
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new Exception("Le pseudo doit être au format alpha numérique");
        }
    }

    public static List<Utilisateur> selectAllUtilisateur() throws SQLException, BllException{
        List<Utilisateur> listUtilisateur = new ArrayList<>();
        try{
            listUtilisateur = utilisateurDao.getAllUtilisateur();
        }catch (SQLException | DalException e){
        	BllException bllException = new BllException();
            bllException.addError(ErrorCodesBLL.ERROR_NO_RESULTS);
            throw new BllException(e.getMessage(), e);
        }
        return  listUtilisateur;
    }


    public Utilisateur addCredit(int noUtilisateur, int newCredit) throws SQLException, BllException, DalException {
        Utilisateur utilisateurRetourner = null;
        utilisateurRetourner = utilisateurDao.updateCredit(noUtilisateur, newCredit);
        return utilisateurRetourner;
    }

    public Utilisateur selectLogin(String pseudoOuEmail) throws SQLException, DalException{
        Utilisateur utilisateurmdp = null;
            utilisateurmdp=utilisateurDao.selectLogin(pseudoOuEmail);
        return utilisateurmdp;
    }

    public void modifMotDePasse(String motDePasse, String cle) throws SQLException, DalException {
        utilisateurDao.modifMotDePasse(motDePasse,cle);
    }
    
    public static Utilisateur inscriptionUtilisateur(Utilisateur utilisateur) throws BusinessException, SQLException, DalException {

		validerCoordonnees(utilisateur);


		if (!businessException.hasErreurs()) {
			utilisateurDao.insert(utilisateur);

		} else {

			throw businessException;
		}
		return utilisateur;
	}
    
    private static void validerCoordonnees(Utilisateur utilisateur) {

		if (utilisateur.getPseudo().trim().equals("") || utilisateur.getNom().trim().equals("")
				|| utilisateur.getPrenom().trim().equals("") || utilisateur.getEmail().trim().equals("")
				|| utilisateur.getRue().trim().equals("") || utilisateur.getCodePostal().trim().equals("")
				|| utilisateur.getVille().trim().equals("") || utilisateur.getMotDePasse().trim().equals("")) {

			businessException.ajouterErreur(ErrorCodesBLL.REGLE_UTILISATEURS_COORDONNEES_ERREUR);
		}
	}
}


















