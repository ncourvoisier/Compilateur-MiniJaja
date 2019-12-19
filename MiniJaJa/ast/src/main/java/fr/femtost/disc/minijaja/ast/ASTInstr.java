package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

import java.util.List;

public abstract class ASTInstr extends ASTNode {

    public abstract boolean typeCheck(Memoire global, Memoire local);

    public void forwardTypeRetour(Sorte sorte) {
        //noop
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        interpreter(m);
        l.get(0).indice = 2;
    }

    @Override
    public int getMaxEtape() {
        return 1;
    }
}
