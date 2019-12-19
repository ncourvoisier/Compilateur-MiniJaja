package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;

import java.util.List;

public abstract class ASTTypeMeth extends ASTNode {

    public abstract Sorte getSorte();

    @Override
    public CompilationCouple compiler(int actual) {
        throw new UnsupportedOperationException("Compilation not implemented in Type");
    }

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation not implemented in Type");
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        throw new UnsupportedOperationException("Interpretation not implemented in Type");
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
