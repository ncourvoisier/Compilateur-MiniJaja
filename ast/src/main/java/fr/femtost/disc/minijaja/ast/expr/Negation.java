package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

import java.util.ArrayList;
import java.util.List;

public class Negation extends ASTExpr {
    private ASTExpr expr;

    public Negation(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return "(-" + expr.rewrite() + ")";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new OpUnaire(OpUnaire.Operandes.NEG)), e.taille + 1);
    }

    @Override
    public Object eval(Memoire m) {
        int e = (int) expr.eval(m);
        return -e;
    }

    @Override
    public Object tryEval(Memoire m, List<MethodeEvalTuple> evaluations) {
        return -((int)expr.tryEval(m, evaluations));
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        boolean b1 = expected == Sorte.INT || expected == Sorte.VOID;
        if (!b1) {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + Sorte.INT.name());
        }
        boolean b2 = expr.typeCheck(global, local, Sorte.INT);
        return b1 && b2;
    }

    @Override
    public List<AppelE> getAllCalls() {
        return expr.getAllCalls();
    }
}
