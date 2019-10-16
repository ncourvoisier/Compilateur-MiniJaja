package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Soustraction extends ASTExpr {
    private  ASTExpr expr2 ;
    private ASTExpr expr1;

    public Soustraction(ASTExpr expr2, ASTExpr expr1) {
        this.expr2 = expr2;
        this.expr1 = expr1;
    }

    public String rewrite() {
        return "(" + expr1.rewrite() + " - " + expr2.rewrite() + ")";
    }
}
