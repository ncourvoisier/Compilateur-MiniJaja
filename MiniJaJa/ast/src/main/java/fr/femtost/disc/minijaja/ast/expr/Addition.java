package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;

public class Addition extends ASTExpr {

    private ASTExpr expr1;
    private ASTExpr expr2;

    public Addition(ASTExpr expr1, ASTExpr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(expr1.rewrite());
        sb.append("+");
        sb.append(expr2.rewrite());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e1 = expr1.compiler(actual);
        CompilationCouple e2 = expr2.compiler(actual + e1.taille);

        return new CompilationCouple(JCodes.concatenate(e1.jCodes, JCodes.concatRight(e2.jCodes, new OpBinaire(OpBinaire.Operandes.ADD))), e1.taille + e2.taille + 1);
    }

    @Override
    public Object eval(Memoire m) {
        int e1 = (int) expr1.eval(m);
        int e2 = (int) expr2.eval(m);
        return e1 + e2;
    }
}
