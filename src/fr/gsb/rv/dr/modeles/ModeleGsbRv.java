package fr.gsb.rv.dr.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.dr.entites.Praticien;
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
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setString(2, mdp);
            ResultSet resultat = requetePreparee.executeQuery();
            if (resultat.next()) {
                Visiteur visiteur = new Visiteur();
                visiteur.setMatricule(resultat.getString("vis_matricule"));
                visiteur.setNom(resultat.getString("vis_nom"));
                visiteur.setPrenom(resultat.getString("vis_prenom"));

                requetePreparee.close();
                return visiteur;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Praticien> getPraticiensHesitants() throws ConnexionException {

        Connection connexion = ConnexionBD.getConnexion();

        String requete = """
                    SELECT p.pra_num,
                    p.pra_nom,
                    p.pra_ville,
                    p.pra_coefnotoriete,
                    rv.rap_date_visite,
                    rv.rap_coefficient
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
                        res.getString("p.pra_ville"),
                        res.getDouble("pra_coefnotoriete"),
                        res.getDate("rv.rap_date_visite").toLocalDate(),
                        res.getInt("rv.rap_coefficient"));
                praticiens.add(praticien);
            }
            return praticiens;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
