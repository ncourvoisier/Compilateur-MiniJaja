package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public class ASTMain extends ASTNode {

    private ASTVars vars;
    private ASTInstrs instrs;

    public ASTMain(ASTVars vars, ASTInstrs instrs) {
        this.vars = vars;
        this.instrs = instrs;
    }

    public ASTVars getVars() {
        return vars;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
