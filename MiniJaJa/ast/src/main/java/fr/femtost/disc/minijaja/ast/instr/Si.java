package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Si extends ASTInstr {
    private ASTExpr expr;
    private ASTInstrs instrsIf;
    private ASTInstrs instrsElse;

    public Si(ASTExpr expr, ASTInstrs instrsIf, ASTInstrs instrsElse) {
        this.expr = expr;
        this.instrsIf = instrsIf;
        this.instrsElse = instrsElse;
    }

    public ASTExpr getExpr() {
        return expr;
    }

    public ASTInstrs getInstrsIf() {
        return instrsIf;
    }

    public ASTInstrs getInstrsElse() {
        return instrsElse;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("if");
        sb.append(expr.rewrite()).append(" {\n");
        sb.append(instrsIf.rewrite()).append("\n}else {\n");
        sb.append(instrsElse.rewrite()).append("\n}\n");
        return sb.toString();
    }
}
