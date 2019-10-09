package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Egal extends ASTExpr {
    private ASTExpr expr1;
    private ASTExpr expr2;

    public Egal(ASTExpr expr1, ASTExpr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public ASTExpr getExpr1() {
        return expr1;
    }

    public ASTExpr getExpr2() {
        return expr2;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(expr1.rewrite());
        sb.append("==");
        sb.append(expr2.rewrite());

        return sb.toString();
    }
}
