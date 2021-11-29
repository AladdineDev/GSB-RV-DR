package fr.gsb.rv.dr.utilitaires;

import java.util.Comparator;

import fr.gsb.rv.dr.entites.Praticien;

public class ComparateurCoefNotoriete implements Comparator<Praticien> {

    @Override
    public int compare(Praticien praticien1, Praticien praticien2) {
        if (praticien1.getCoefNotoriete() == praticien2.getCoefNotoriete()) {
            return 0;
        } else if (praticien1.getCoefNotoriete() > praticien2.getCoefNotoriete()) {
            return 1;
        } else {
            return -1;
        }
    }

}
