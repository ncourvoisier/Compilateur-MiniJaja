package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

public class Negation extends ASTExpr {
    private ASTExpr expr;

    public Negation(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return "-(" + expr.rewrite() + ")";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new OpUnaire(OpUnaire.Operandes.NEG)), e.taille + 1);
    }

    @Override
    public void typeCheck(Memoire m) {
        if (expr.eval(m) == null) {
            System.out.println(expr.eval(m) + "is not initialize.");
        }
    }

    @Override
    public Object eval(Memoire m) {
        int e = (int) expr.eval(m);
        return -e;
    }
}
