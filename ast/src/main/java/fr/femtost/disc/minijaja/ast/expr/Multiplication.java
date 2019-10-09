package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Multiplication extends ASTExpr {
    private ASTExpr expr1;
    private ASTExpr expr2;

    public Multiplication(ASTExpr expr1, ASTExpr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public ASTExpr getExpr1() {
        return expr1;
    }

    public ASTExpr getExpr2() {
        return expr2;
    }
}
