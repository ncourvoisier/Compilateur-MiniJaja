package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public abstract class ASTListExpr extends ASTNode {
    private ASTInstr instr;
    private ASTListExpr listExpr;

    public ASTListExpr(ASTInstr instr, ASTListExpr listExpr) {
        this.instr = instr;
        this.listExpr = listExpr;
    }

    public ASTInstr getInstr() {
        return instr;
    }

    public ASTListExpr getListExpr() {
        return listExpr;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
