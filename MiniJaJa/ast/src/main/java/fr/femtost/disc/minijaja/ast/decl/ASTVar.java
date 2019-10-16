package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public abstract class ASTVar extends ASTDecl {

    protected Identifiant identifiant;
    protected ASTExpr expr;

    public ASTVar(Identifiant identifiant) {
        this.identifiant = identifiant;
    }

    public ASTVar(Identifiant identifiant, ASTExpr expr) {
        this.identifiant = identifiant;
        this.expr = expr;
    }
}
