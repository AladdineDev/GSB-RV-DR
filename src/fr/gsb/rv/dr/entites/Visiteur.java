package fr.gsb.rv.dr.entites;

public class Visiteur {

    String nom;
    String matricule;

    public Visiteur() {

    }

    public Visiteur(String nom, String matricule) {
        this.nom = nom;
        this.matricule = matricule;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

}
