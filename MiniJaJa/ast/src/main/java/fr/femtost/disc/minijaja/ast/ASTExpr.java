package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.AppelE;

import java.util.LinkedList;
import java.util.List;

public abstract class ASTExpr extends ASTNode {

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, Sorte expected);

    public abstract Object eval(Memoire m);
    public abstract Object tryEval(Memoire m, List<MethodeEvalTuple> evaluations);
    public List<AppelE> getAllCalls() {
        return new LinkedList<>();
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
