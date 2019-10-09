package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public abstract class ASTVar extends ASTDecl {

    private Identifiant identifiant;
    private ASTExpr expr;

    public ASTVar(Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    public ASTVar(Identifiant identifiant, ASTExpr expr) {
        this.identifiant = identifiant;
        this.expr = expr;
    }

    public Identifiant getIdentifiant() {
        return identifiant;
    }

    public ASTExpr getExpr() {
        return expr;
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
