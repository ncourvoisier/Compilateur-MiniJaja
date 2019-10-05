package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTEntetes;

public class EChain extends ASTEntetes {
    private ASTEntetes Succesor;
    private ASTEntete node;



    public EChain(ASTEntetes succesor, ASTEntete node) {
        Succesor = succesor;
        this.node = node;
    }
    public ASTEntetes getSuccesor() {
        return Succesor;
    }

    public ASTEntete getNode() {
        return node;
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
