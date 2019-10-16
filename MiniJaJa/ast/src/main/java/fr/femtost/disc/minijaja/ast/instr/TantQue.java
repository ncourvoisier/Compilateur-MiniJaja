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

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("while");
        sb.append("(").append(expr.rewrite()).append(")");
        sb.append(" {\n");
        sb.append(instrs.rewrite());
        sb.append("}\n");

        return sb.toString();
    }
}
