package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.EvaluationCouplePasAPas;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;
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
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
