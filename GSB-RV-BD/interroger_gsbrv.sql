use gsbrv;
--- Boite de dialogue --- 
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
--- Table ---
--
-- 2 -- Liste des praticiens hésitants
--
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
    AND rv.rap_coefficient BETWEEN 4 AND 6;
--- Table réactive ---
--
-- 1.a -- Liste des visiteurs
--
SELECT vis_matricule,
    vis_nom,
    vis_prenom
FROM Visiteur;
--
-- 1.b -- Liste des rapports
--
SELECT rap_num,
    vis_matricule,
    rap_date_visite
FROM RapportVisite rv
    INNER JOIN Visiteur v ON rv.vis_matricule = v.vis_matricule
WHERE vis_matricule = ?
    AND rap_date_visite = ?;
--
-- 1.c -- Le rapport a été lu
--
UPDATE RapportVisite
SET lu = true
WHERE rap_num = ?;