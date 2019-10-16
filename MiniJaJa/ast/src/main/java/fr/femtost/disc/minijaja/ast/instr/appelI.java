package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class appelI extends ASTInstr {

    private Identifiant ident;
    private ASTListExpr listExpr;

    public appelI(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("(");
        sb.append(listExpr.rewrite());
        sb.append(")");


        return sb.toString();
    }
}
