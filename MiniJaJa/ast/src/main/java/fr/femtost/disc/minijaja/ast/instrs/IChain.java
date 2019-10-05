package fr.femtost.disc.minijaja.ast.instrs;

import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTDecls;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;

public class IChain extends ASTInstrs {
    private ASTInstrs successor;
    private ASTInstr node;

    public ASTInstrs getSuccessor() {
        return successor;
    }

    public ASTInstr getNode() {
        return node;
    }

    public IChain(ASTInstrs successor, ASTInstr node) {
        this.successor = successor;
        this.node = node;
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
