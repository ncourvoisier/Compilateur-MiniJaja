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

    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        sb.append(";\n");
        sb.append(successor.rewrite());

        return sb.toString();
    }
}
