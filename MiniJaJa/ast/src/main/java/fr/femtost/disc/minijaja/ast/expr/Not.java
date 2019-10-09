package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Not extends ASTExpr {
    private ASTExpr expr;


    public Not(ASTExpr expr) {
        this.expr = expr;
    }
    public ASTExpr getExpr() {
        return expr;
    }

}
