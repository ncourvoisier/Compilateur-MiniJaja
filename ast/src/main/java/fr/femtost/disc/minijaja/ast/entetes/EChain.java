package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.type.Entier;

public class EChain extends ASTEntetes {
    private ASTEntetes successor;
    private ASTEntete node;



    public EChain(ASTEntetes successor, ASTEntete node) {
        this.successor = successor;
        this.node = node;
    }
    public ASTEntetes getsuccesor() {
        return successor;
    }

    public ASTEntete getNode() {
        return node;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        if(successor instanceof EChain) {
            sb.append(", ");
            sb.append(successor.rewrite());
        }

        return sb.toString();
    }
}
