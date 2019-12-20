package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.expr.AppelE;

public class MethodeEvalTuple {

    public AppelE appel;
    public boolean traite = false;
    public Object retour;
    public int profondeur;

    public MethodeEvalTuple(AppelE appel, int profondeur) {
        this.appel = appel;
        this.profondeur = profondeur;
    }
}
