package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTEntetes;

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
        sb.append(",");
        sb.append(successor.rewrite());

        return sb.toString();
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
