package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.decl.ASTMethode;

public class EvaluationCouplePasAPas {

    public ASTMethode astm;
    public int val;

    public EvaluationCouplePasAPas(ASTMethode astm, int val) {
        this.astm = astm;
        this.val = val;
    }
}
