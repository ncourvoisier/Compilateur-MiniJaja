package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.AppelE;

import java.util.List;

public abstract class ASTListExpr extends ASTNode {

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation dans liste expr");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, ASTEntetes entetes);

    public abstract List<AppelE> getAllCalls();

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        throw new UnsupportedOperationException("Interpretation dans liste expr");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
