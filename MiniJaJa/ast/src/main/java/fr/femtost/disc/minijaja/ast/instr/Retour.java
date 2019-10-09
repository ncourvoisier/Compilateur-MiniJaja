package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;

public class Retour extends ASTInstr {

    private ASTExpr expr;

    public Retour(ASTExpr expr) {
        this.expr = expr;
    }

    public ASTExpr getExpr() {
        return expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("writeln");
        sb.append("(");
        sb.append(expr.rewrite());
        sb.append(")");
        return sb.toString();
    }
}