package fr.eni.encheres.bll;

 abstract class ErrorCodesBLL {
     static final int ERROR_LENGTH_PSEUDO_UTILISATEUR = 30000;
     static final int ERROR_LENGTH_NOM_UTILISATEUR = 30001;
     static final int ERROR_LENGTH_PRENOM_UTILISATEUR = 30002;
     static final int ERROR_LENGTH_EMAIL_UTILISATEUR = 30003;
     static final int ERROR_LENGTH_TELEPHONE_UTILISATEUR = 30004;
     static final int ERROR_LENGTH_RUE_UTILISATEUR = 30005;
     static final int ERROR_LENGTH_CODE_POSTAL_UTILISATEUR = 30006;
     static final int ERROR_LENGTH_VILLE_UTILISATEUR = 30007;
     static final int ERROR_FORMAT_EMAIL_UTILISATEUR = 30008;
     static final int ERROR_FORMAT_TELEPHONE_UTILISATEUR = 30009;
     static final int ERROR_PSEUDO_OR_MAIL_ALREADY_TAKEN = 30010;
     static final int ERROR_PSEUDO_NOT_ALPHANUMERIC = 30011;
     static final int ERROR_LENGTH_NOM_ARTICLE = 30020;
     static final int ERROR_LENGTH_DESCRIPTION_ARTICLE = 30021;
     static final int ERROR_VALUE_STATUT_VENTE_ARTICLE = 30022;
     static final int ERROR_START_DATE_AFTER_END_DATE = 30023;
     static final int ERROR_DATE_BEFORE_TODAY = 30024;
     static final int ERROR_LENGTH_LIBELLE_CATEGORIE = 30030;
     static final int ERROR_LIBELLE_CATEGORIE_ALREADY_TAKEN = 30031;
     static final int ERROR_LENGTH_RUE_RETRAIT = 30040;
     static final int ERROR_LENGTH_CODE_POSTAL_RETRAIT = 30041;
     static final int ERROR_LENGTH_VILLE_RETRAIT = 30042;
     static final int ERROR_NO_RESULTS = 30050;
     
     /**
 	 * Echec quand le libelle ne respecte pas les règles définies
 	 */
 	public static final int REGLE_CATEGORIES_LIBELLE_ERREUR=20000;
 	
 	/**
 	 * Echec quand la la date de l'enchere ne respecte pas les règles définies
 	 */
 	public static final int REGLE_ENCHERES_DATE_ERREUR=20001;
 	
 	/**
 	 * Echec quand l'adresse  ne respecte pas les règles définies
 	 */
 	public static final int REGLE_RETRAITS_ADRESSE_ERREUR=20002;
 	
 	/**
 	 * Echec quand les coordonnées de l'utilisateur ne respecte pas les règles définies
 	 */
 	public static final int REGLE_UTILISATEURS_COORDONNEES_ERREUR=20003;
 	
 	/**
 	 * Echec quand la liste des articles vendus ne respecte pas les règles définies
 	 */
 	public static final int REGLE_UTILISATEURS_ARTICLE_VENDU_ERREUR=20004;
 	
 	/**
 	 * Echec quand la liste des articles achetés ne respecte pas les règles définies
 	 */
 	public static final int REGLE_UTILISATEURS_ARTICLE_ACHETE_ERREUR=20005;
 	
 	/**
 	 * Echec quand la liste des encheres ne respecte pas les règles définies
 	 */
 	public static final int REGLE_UTILISATEURS_ENCHERES_ERREUR=20006;
 	/**
 	 * Echec quand la date de début et de fin des enchères  ne respectent pas les règles définies
 	 */
 	public static final int REGLE_ARTICLES_DATE_ERREUR=20007;
 	/**
 	 * Echec lors d'une tentative de supprimer un article déjà acheté
 	 */
 	public static final int REGLE_ARTICLES_ETAT_VENTE_ERREUR=20008;
}
