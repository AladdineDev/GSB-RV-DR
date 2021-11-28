package fr.gsb.rv.dr.modeles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;

public class ModeleGsbRv {

    public static Visiteur seConnecter(String matricule, String mdp) throws ConnexionException {

        // Code de test à compléter

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
}
