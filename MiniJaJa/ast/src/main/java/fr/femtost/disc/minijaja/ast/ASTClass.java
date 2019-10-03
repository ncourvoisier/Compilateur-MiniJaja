package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class ASTClass extends ASTNode {

    private Identifiant ident;
    private ASTDecls decls;
    private ASTMain main;

    public ASTClass(Identifiant ident, ASTDecls decls, ASTMain main) {
        this.ident = ident;
        this.decls = decls;
        this.main = main;
    }

    public Identifiant getIdent() {
        return ident;
    }

    public ASTDecls getDecls() {
        return decls;
    }

    public ASTMain getMain() {
        return main;
    }
}
