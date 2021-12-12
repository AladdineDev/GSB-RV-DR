UPDATE RapportVisite
SET rap_lu = true
WHERE vis_matricule = ?
    AND rap_num = ?;