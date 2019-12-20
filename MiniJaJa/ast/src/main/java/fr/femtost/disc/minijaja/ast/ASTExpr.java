package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;

import java.util.List;

public abstract class ASTExpr extends ASTNode {
    public abstract Object eval(Memoire m);

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, Sorte expected);


    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
