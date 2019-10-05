package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public abstract class ASTInstr extends ASTNode {
    private ASTIdentGenerique ident;
    private ASTExpr expr;

    public ASTInstr(ASTIdentGenerique ident, ASTExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    public ASTIdentGenerique getIdent() {
        return ident;
    }

    public ASTExpr getExpr() {
        return expr;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
