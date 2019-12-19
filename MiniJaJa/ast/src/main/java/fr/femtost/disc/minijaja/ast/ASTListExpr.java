package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.EvaluationCouplePasAPas;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;

import java.util.List;

public abstract class ASTListExpr extends ASTNode {

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation dans liste expr");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, ASTEntetes entetes);

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        throw new UnsupportedOperationException("Interpretation dans liste expr");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
