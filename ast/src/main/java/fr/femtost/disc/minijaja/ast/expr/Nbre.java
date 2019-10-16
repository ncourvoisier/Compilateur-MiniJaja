package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Nbre extends ASTExpr {
    private int expr;

    public Nbre(int expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return Integer.valueOf(expr).toString();
    }
}
