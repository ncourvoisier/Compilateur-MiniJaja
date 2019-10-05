package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.AstIdent;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;

public class AppelE extends ASTExpr {
    private AstIdent ident;
    private ASTListExpr listExpr;


    public AppelE(AstIdent ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    public AstIdent getIdent() {
        return ident;
    }

    public ASTListExpr getListExpr() {
        return listExpr;
    }


    public String rewrite() {
        //TODO : implement
        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
