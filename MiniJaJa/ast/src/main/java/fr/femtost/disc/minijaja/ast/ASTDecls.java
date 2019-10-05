package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public abstract class ASTDecls extends ASTNode {
    private ASTDecl  decl;

    public ASTDecl getDecl() {
        return decl;
    }

    public ASTDecls getDecls() {
        return decls;
    }

    public ASTDecls(ASTDecl decl, ASTDecls decls) {
        this.decl = decl;
        this.decls = decls;
    }

    private ASTDecls decls;

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
