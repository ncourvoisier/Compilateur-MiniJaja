package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Nbre extends ASTExpr {
    private int expr;

    public Nbre(int expr) {
        this.expr = expr;
    }

    public int getExpr() {
        return expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(expr);

        return sb.toString();
    }
}
