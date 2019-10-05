package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public abstract class ASTInstrs extends ASTNode {
    private  ASTInstrs instrs;
    private ASTInstr instr;

    public ASTInstrs(ASTInstrs instrs, ASTInstr instr) {
        this.instrs = instrs;
        this.instr = instr;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public ASTInstr getInstr() {
        return instr;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
