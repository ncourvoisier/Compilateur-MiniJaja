package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.instrs.IChain;

public class Si extends ASTInstr {
    private ASTExpr expr;
    private ASTInstrs instrsIf;
    private ASTInstrs instrsElse;

    public Si(ASTExpr expr, ASTInstrs instrsIf) {
        this.expr = expr;
        this.instrsIf = instrsIf;
    }

    public Si(ASTExpr expr, ASTInstrs instrsIf, ASTInstrs instrsElse) {
        this.expr = expr;
        this.instrsIf = instrsIf;
        this.instrsElse = instrsElse;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("if");
        sb.append(expr.rewrite()).append(" {\n");
        sb.append(instrsIf.rewrite()).append("}");
        if (instrsElse != null && instrsElse instanceof IChain) {
            sb.append(" else {\n");
            sb.append(instrsElse.rewrite());
            sb.append("}");
        }
        sb.append("\n");
        return sb.toString();
    }
}
