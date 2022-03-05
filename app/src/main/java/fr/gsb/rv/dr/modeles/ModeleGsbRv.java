package fr.gsb.rv.dr.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;

public class ModeleGsbRv {

    public static Visiteur seConnecter(String matricule, String mdp) throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                SELECT t.vis_matricule,
                    t.tra_role,
                    t.jjmmaa,
                    v.vis_prenom,
                    v.vis_nom
                FROM Travailler t
                    INNER JOIN (
                        SELECT tra_role,
                            vis_matricule,
                            MAX(jjmmaa) AS jjmmaa
                        FROM Travailler
                        GROUP BY vis_matricule
                    ) AS s
                    INNER JOIN Visiteur AS v ON s.vis_matricule = t.vis_matricule
                    AND t.jjmmaa = s.jjmmaa
                    AND v.vis_matricule = t.vis_matricule
                WHERE t.tra_role = 'Délégué'
                    AND v.vis_matricule = ?
                    AND v.vis_mdp = ?
                    """;

        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, matricule);
            pstmt.setString(2, mdp);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                Visiteur visiteur = new Visiteur(
                        res.getString("vis_matricule"),
                        res.getString("vis_nom"),
                        res.getString("vis_prenom"));
                pstmt.close();
                return visiteur;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Visiteur> getVisiteurs() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                SELECT vis_matricule,
                    vis_nom,
                    vis_prenom
                FROM Visiteur;
                """;

        try {
            Statement stmt = connexion.createStatement();
            ResultSet res = stmt.executeQuery(requete);
            List<Visiteur> visiteurs = new ArrayList<>();
            while (res.next()) {
                Visiteur visiteur = new Visiteur(
                        res.getString("vis_matricule"),
                        res.getString("vis_nom"),
                        res.getString("vis_prenom"));
                visiteurs.add(visiteur);
            }
            stmt.close();
            return visiteurs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Praticien> getPraticiens() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                SELECT p.pra_num,
                    p.pra_nom,
                    p.pra_prenom,
                    p.pra_ville,
                    p.pra_coefnotoriete,
                    rv.rap_date_visite,
                    rv.rap_coefficient,
                    p.pra_adresse,
                    p.pra_cp
                FROM Praticien p
                    INNER JOIN (
                        SELECT MAX(rap_date_visite) AS rap_date_visite,
                            MAX(rap_coefficient) AS rap_coefficient
                        FROM RapportVisite
                        GROUP BY rap_num
                    ) AS r
                    INNER JOIN RapportVisite as rv ON p.pra_num = rv.pra_num
                WHERE rv.rap_date_visite = r.rap_date_visite
                    AND rv.rap_coefficient = r.rap_coefficient;
                    """;

        try {
            Statement stmt = connexion.createStatement();
            ResultSet res = stmt.executeQuery(requete);
            List<Praticien> praticiens = new ArrayList<>();
            while (res.next()) {
                Praticien praticien = new Praticien(
                        res.getInt("p.pra_num"),
                        res.getString("p.pra_nom"),
                        res.getString("p.pra_prenom"),
                        res.getString("p.pra_ville"),
                        res.getDouble("pra_coefnotoriete"),
                        res.getDate("rv.rap_date_visite").toLocalDate(),
                        res.getInt("rv.rap_coefficient"),
                        res.getString("p.pra_adresse"),
                        res.getString("p.pra_cp"));
                praticiens.add(praticien);
            }
            stmt.close();
            return praticiens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Praticien> getPraticiensHesitants() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                SELECT p.pra_num,
                    p.pra_nom,
                    p.pra_prenom,
                    p.pra_ville,
                    p.pra_coefnotoriete,
                    rv.rap_date_visite,
                    rv.rap_coefficient,
                    p.pra_adresse,
                    p.pra_cp
                FROM Praticien p
                    INNER JOIN (
                        SELECT MAX(rap_date_visite) AS rap_date_visite,
                            MAX(rap_coefficient) AS rap_coefficient
                        FROM RapportVisite
                        GROUP BY rap_num
                    ) AS r
                    INNER JOIN RapportVisite as rv ON p.pra_num = rv.pra_num
                WHERE rv.rap_date_visite = r.rap_date_visite
                    AND rv.rap_coefficient = r.rap_coefficient
                    AND rv.rap_coefficient < 5;
                    """;

        try {
            Statement stmt = connexion.createStatement();
            ResultSet res = stmt.executeQuery(requete);
            List<Praticien> praticiens = new ArrayList<>();
            while (res.next()) {
                Praticien praticien = new Praticien(
                        res.getInt("p.pra_num"),
                        res.getString("p.pra_nom"),
                        res.getString("p.pra_prenom"),
                        res.getString("p.pra_ville"),
                        res.getDouble("pra_coefnotoriete"),
                        res.getDate("rv.rap_date_visite").toLocalDate(),
                        res.getInt("rv.rap_coefficient"),
                        res.getString("p.pra_adresse"),
                        res.getString("p.pra_cp"));
                praticiens.add(praticien);
            }
            stmt.close();
            return praticiens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<RapportVisite> getRapportsVisite(String matricule, int mois, int annee)
            throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                SELECT rv.vis_matricule,
                    rv.pra_num,
                    rap_num,
                    rap_date_visite,
                    rap_date_redaction,
                    rap_bilan,
                    rap_coefficient,
                    rap_motif,
                    rap_lu
                FROM RapportVisite rv
                    INNER JOIN Visiteur v ON rv.vis_matricule = v.vis_matricule
                WHERE rv.vis_matricule = ?
                    AND YEAR(rv.rap_date_visite) = ?
                    AND MONTH(rv.rap_date_visite) = ?
                    """;

        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, matricule);
            pstmt.setInt(2, annee);
            pstmt.setInt(3, mois);
            ResultSet res = pstmt.executeQuery();
            List<RapportVisite> rapportsVisite = new ArrayList<>();
            while (res.next()) {
                RapportVisite rapportVisite = new RapportVisite();
                rapportVisite.setNumero(res.getInt("rap_num"));
                rapportVisite.setDateVisite(res.getDate("rv.rap_date_visite").toLocalDate());
                rapportVisite.setDateRedaction(res.getDate("rv.rap_date_redaction").toLocalDate());
                rapportVisite.setBilan(res.getString("rap_bilan"));
                rapportVisite.setMotif(res.getString("rap_motif"));
                rapportVisite.setCoefConfiance(res.getInt("rap_coefficient"));
                rapportVisite.setLu(res.getBoolean("rap_lu"));

                for (Visiteur visiteur : getVisiteurs()) {
                    if (visiteur.getMatricule() == res.getString("vis_matricule")) {
                        rapportVisite.setVisiteur(visiteur);
                    }
                }

                for (Praticien praticien : getPraticiens()) {
                    if (praticien.getNumero() == res.getInt("pra_num")) {
                        rapportVisite.setPraticien(praticien);
                    }
                }

                rapportsVisite.add(rapportVisite);
            }
            pstmt.close();
            return rapportsVisite;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setRapportsVisiteLu(String matricule, int numRapport) throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                UPDATE RapportVisite
                SET rap_lu = true
                WHERE vis_matricule = ?
                    AND rap_num = ?;
                    """;
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, matricule);
            pstmt.setInt(2, numRapport);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
