package fr.eni.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String MotDePasse;
    private int credit;
    private boolean administration;

    
    public Utilisateur() {
    }


    public Utilisateur(int id,
    		           String pseudo, 
    		           String nom, 
    	           	   String prenom, 
    		           String email, 
    	           	   String telephone, 
    	               String rue,  
    		           String codePostal, 
    		           String ville, 
    		           String motDePasse, 
    		           int credit, 
    		           boolean administration) {
    	this.id = id;
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.MotDePasse = motDePasse;
        this.credit = credit;
        this.administration = administration;
    }

    public Utilisateur(String pseudo, 
    		           String nom, 
    		           String prenom, 
    		           String email, 
    		           String telephone, 
    		           String rue,            
    		           String codePostal, 
    		           String ville, 
    		           String motDePasse, 
    		           int credit, 
    		           boolean administration) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.MotDePasse = motDePasse;
        this.credit = credit;
        this.administration = administration;
    }

	
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean getAdministration() {
        return administration;
    }

    public void setAdministration(boolean administration) {
        this.administration = administration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    @Override
    public String toString() {
        return "Utilisateur{" +
        		"id=" + id +
                "noUtilisateur=" + noUtilisateur +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", MotDePasse='" + MotDePasse + '\'' +
                ", credit=" + credit +
                ", administration=" + administration +
                '}';
    }
}
