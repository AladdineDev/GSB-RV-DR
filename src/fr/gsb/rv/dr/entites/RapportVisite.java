package fr.gsb.rv.dr.entites;

import java.time.LocalDate;

public class RapportVisite {

    private int numero;
    private LocalDate dateVisite;
    private LocalDate dateRedaction;
    private String bilan;
    private String motif;
    private int coefConfiance;
    private boolean lu;
    private Visiteur visiteur;
    private Praticien praticien;

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDateVisite() {
        return this.dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public LocalDate getDateRedaction() {
        return this.dateRedaction;
    }

    public void setDateRedaction(LocalDate dateRedaction) {
        this.dateRedaction = dateRedaction;
    }

    public String getBilan() {
        return this.bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getMotif() {
        return this.motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getCoefConfiance() {
        return this.coefConfiance;
    }

    public void setCoefConfiance(int coefConfiance) {
        this.coefConfiance = coefConfiance;
    }

    public boolean isLu() {
        return this.lu;
    }

    public boolean getLu() {
        return this.lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public Visiteur getVisiteur() {
        return this.visiteur;
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public Praticien getPraticien() {
        return this.praticien;
    }

    public void setPraticien(Praticien praticien) {
        this.praticien = praticien;
    }

    @Override
    public String toString() {
        return "{" +
                " numero='" + getNumero() + "'" +
                ", dateVisite='" + getDateVisite() + "'" +
                ", dateRedaction='" + getDateRedaction() + "'" +
                ", bilan='" + getBilan() + "'" +
                ", motif='" + getMotif() + "'" +
                ", coefConfiance='" + getCoefConfiance() + "'" +
                ", lu='" + isLu() + "'" +
                ", visiteur='" + getVisiteur() + "'" +
                ", praticien='" + getPraticien() + "'" +
                "}";
    }

}
