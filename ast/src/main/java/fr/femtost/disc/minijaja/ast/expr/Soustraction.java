package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class Soustraction extends ASTExpr {
    private  ASTExpr expr2 ;
    private ASTExpr expr1;

    public Soustraction(ASTExpr expr2, ASTExpr expr1) {
        this.expr2 = expr2;
        this.expr1 = expr1;
    }

    public ASTExpr getExpr2() {
        return expr2;
    }

    public ASTExpr getExpr1() {
        return expr1;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(expr1.rewrite());
        sb.append("-");
        sb.append(expr2.rewrite());

        return sb.toString();
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
