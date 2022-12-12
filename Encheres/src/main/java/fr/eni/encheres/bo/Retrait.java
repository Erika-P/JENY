package fr.eni.encheres.bo;

public class Retrait {

    private int noArticle;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean retrait;


    public Retrait() {
    	
    }

    
    public Retrait(String rue, 
    		       String codePostal, 
    		       String ville) {
    	super();
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(int noArticle, 
    		       String rue, 
    		       String codePostal, 
    		       String ville) {
    	super();
        this.noArticle = noArticle;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(int noArticle, 
    		       String rue, 
    		       String codePostal, 
    		       String ville, 
    		       boolean retrait) {
        this.noArticle = noArticle;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.retrait = retrait ;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
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

    public boolean getRetrait() {
        return retrait;
    }

    public void setRetrait(boolean retrait) {
        this.retrait = retrait;
    }

    @Override
    public String toString() {
        return "Retrait{" +
                "noArticle=" + noArticle +
                ", rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
