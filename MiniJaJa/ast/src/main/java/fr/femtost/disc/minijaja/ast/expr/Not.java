package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Not extends ASTExpr {
    private ASTExpr expr;

    public Not(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("!");
        sb.append(expr.rewrite());

        return sb.toString();
    }
}
