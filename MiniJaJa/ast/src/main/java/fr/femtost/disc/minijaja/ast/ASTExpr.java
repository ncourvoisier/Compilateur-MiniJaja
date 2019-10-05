package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public abstract class ASTExpr extends ASTNode {
    ASTExpr expr1;

    public ASTExpr(ASTExpr expr1) {
        this.expr1 = expr1;
    }

    public ASTExpr getExpr1() {
        return expr1;
    }
    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
