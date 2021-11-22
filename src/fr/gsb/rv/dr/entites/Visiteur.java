package fr.gsb.rv.dr.entites;

public class Visiteur {

    String matricule;
    String nom;
    String prenom;

    public Visiteur() {
    }

    public Visiteur(String matricule, String nom, String prenom) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "{" + " matricule='" + getMatricule() + "'" + ", nom='" + getNom() + "'" + ", prenom='" + getPrenom()
                + "'" + "}";
    }

}
