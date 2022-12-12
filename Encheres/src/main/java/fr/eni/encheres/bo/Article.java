package fr.eni.encheres.bo;

import java.time.LocalDate;


public class Article {

    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private String etatVente;
    private Utilisateur noUtilisateur;
    private Categorie noCategorie;
    private Retrait retrait;

    
    public Article() {
    	
    }

    
    public Article(int noArticle,
            String nomArticle,
            String description,
            LocalDate dateDebutEncheres,
            LocalDate dateFinEncheres,
            int prixInitial,
            int prixVente,
            String etatVente,
            Utilisateur noUtilisateur,
            Categorie noCategorie,
            Retrait retrait) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.noUtilisateur = noUtilisateur;
        this.noCategorie = noCategorie;
        this.retrait = retrait;
    }

    public Article(String nomArticle,
            String description,
            LocalDate dateDebutEncheres,
            LocalDate dateFinEncheres,
            int prixInitial,
            int prixVente,
            String etatVente,
            Utilisateur noUtilisateur,
            Categorie noCategorie,
            Retrait retrait) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.noUtilisateur = noUtilisateur;
        this.noCategorie = noCategorie;
        this.retrait = retrait;
    }
    
    
    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Utilisateur getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(Utilisateur articleUtilisateur) {
        this.noUtilisateur = articleUtilisateur;
    }

    public Categorie getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(Categorie articleCategorie) {
        this.noCategorie = articleCategorie;
    }
    
	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}
    
    
    @Override
    public String toString() {
        return "Article{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", prixInitial=" + prixInitial +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                ", noUtilisateur=" + noUtilisateur +
                ", noCategorie=" + noCategorie +
                ", retrait=" + retrait +
                '}';
    }
}
