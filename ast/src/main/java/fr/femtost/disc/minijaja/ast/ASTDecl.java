package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.EvaluationCouplePasAPas;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;

import java.util.List;

public abstract class ASTDecl extends ASTNode {

    public abstract void retirer(Memoire m);
    public abstract boolean typeCheck(Memoire global, Memoire local);
    public abstract boolean firstCheck(Memoire global);

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        interpreter(m);
        l.get(0).indice = 2;
    }

    @Override
    public int getMaxEtape() {
        return 1;
    }
}
