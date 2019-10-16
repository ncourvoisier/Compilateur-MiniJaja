package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Negation extends ASTExpr {
    private ASTExpr expr;

    public Negation(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("-");
        sb.append(expr.rewrite());
        sb.append(")");

        return sb.toString();
    }
}
