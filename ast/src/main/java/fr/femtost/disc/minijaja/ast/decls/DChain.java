package fr.femtost.disc.minijaja.ast.decls;

import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTDecls;

public class DChain extends ASTDecls {

    private ASTDecls successor;
    private ASTDecl node;

    public DChain(ASTDecls successor, ASTDecl node) {
        this.successor = successor;
        this.node = node;
    }

    public ASTDecls getSuccessor() {
        return successor;
    }

    public ASTDecl getNode() {
        return node;
    }
}
