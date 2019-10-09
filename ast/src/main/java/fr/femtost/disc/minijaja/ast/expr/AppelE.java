package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class AppelE extends ASTExpr {
    private Identifiant ident;
    private ASTListExpr listExpr;


    public AppelE(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    public Identifiant getIdent() {
        return ident;
    }

    public ASTListExpr getListExpr() {
        return listExpr;
    }


    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("(");
        sb.append(listExpr.rewrite());
        sb.append(")");


        return sb.toString();
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
