package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public final class Enil extends ASTEntetes {

    @Override
    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JNil(), 0);
    }

    @Override
    public void retirer(Memoire m) {
        //noop
    }

    @Override
    public int getChainPosition() {
        return 0;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        return true;
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
