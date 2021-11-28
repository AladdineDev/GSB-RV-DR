use gsbrv;
--
-- 3 -- Matricule des visiteurs qui occupent ou qui ont occupé le poste de DR
--
SELECT DISTINCT v.vis_matricule
FROM Visiteur v
    INNER JOIN Travailler t ON v.vis_matricule = t.vis_matricule
WHERE tra_role = 'Délégué';
--
-- 4 et 5 -- Informations relatives au DR dont le matricule et le mot de passe sont renseignés
--
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
    AND v.vis_mdp = ?;