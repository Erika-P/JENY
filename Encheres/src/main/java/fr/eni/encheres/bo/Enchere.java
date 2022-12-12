package fr.eni.encheres.bo;

import java.time.LocalDate;

public class Enchere {
	private int id;
    private Utilisateur noUtilisateur;
    private Article noArticle;
    private LocalDate dateEnchere;
    private int montantEnchere;

    public Enchere (){}

    public Enchere(int id, Utilisateur noUtilisateur, Article noArticle, LocalDate dateEnchere, int montantEnchere) {
    	this.id = id;
    	this.noUtilisateur = noUtilisateur;
        this.noArticle = noArticle;
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    
    public Utilisateur getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(Utilisateur utilisateur) {
        this.noUtilisateur = utilisateur;
    }

    public Article getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(Article article) {
        this.noArticle = article;
    }


    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public String toString() {
        return "Enchere{" +
        	    "id=" + id +
                "noUtilisateur=" + noUtilisateur +
                ", noArticle=" + noArticle +
                ", dateEnchere=" + dateEnchere +
                ", montantEnchere=" + montantEnchere +
                '}';
    }
}
