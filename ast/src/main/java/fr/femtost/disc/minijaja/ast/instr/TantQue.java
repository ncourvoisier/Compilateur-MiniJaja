package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;

public class TantQue extends ASTInstr {
    ASTInstrs instrs;
    ASTExpr expr;

    public TantQue(ASTInstrs instrs, ASTExpr expr) {
        this.instrs = instrs;
        this.expr = expr;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public ASTExpr getExpr() {
        return expr;
    }
}
