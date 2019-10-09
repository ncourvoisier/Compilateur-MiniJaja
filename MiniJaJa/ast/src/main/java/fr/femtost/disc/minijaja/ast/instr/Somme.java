package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Somme extends ASTInstr {

    private ASTIdentGenerique ident;
    private ASTExpr expr;

    public Somme(ASTIdentGenerique ident, ASTExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    public ASTIdentGenerique getIdent() {
        return ident;
    }

    public ASTExpr getExpr() {
        return expr;
    }


    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(ident.rewrite());
        sb.append("+=");
        sb.append(expr.rewrite());
        sb.append(")");
        return sb.toString();
    }
}
