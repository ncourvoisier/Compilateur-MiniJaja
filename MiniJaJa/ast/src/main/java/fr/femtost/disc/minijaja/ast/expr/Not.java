package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

public class Not extends ASTExpr {

    private ASTExpr expr;

    public Not(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("!");
        sb.append(expr.rewrite());

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new OpUnaire(OpUnaire.Operandes.NOT)), e.taille + 1);
    }
}
