package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Affectation extends ASTInstr {

    private ASTIdentGenerique ident;
    private ASTExpr expr;

    public Affectation(ASTIdentGenerique ident, ASTExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("=");
        sb.append(expr.rewrite());

        return sb.toString();
    }
}
