package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

import java.util.List;

public abstract class ASTExpr extends ASTNode {
    abstract public Object eval(Memoire m);

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, Sorte expected);


    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
