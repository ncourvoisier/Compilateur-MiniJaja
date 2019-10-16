package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Addition extends ASTExpr {

    private ASTExpr expr1;
    private ASTExpr expr2;

    public Addition(ASTExpr expr1, ASTExpr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(expr1.rewrite());
        sb.append("+");
        sb.append(expr2.rewrite());
        sb.append(")");

        return sb.toString();
    }

}
