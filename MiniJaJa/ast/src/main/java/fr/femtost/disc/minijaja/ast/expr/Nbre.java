package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public class Nbre extends ASTExpr {

    private int expr;

    public Nbre(int expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return Integer.toString(expr);
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(expr), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        return expr;
    }

    @Override
    public Object tryEval(Memoire m, List<MethodeEvalTuple> evaluations) {
        return expr;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        if (expected == Sorte.INT || expected == Sorte.VOID)
            return true;
        else {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + Sorte.INT.name());
            return false;
        }
    }
}
