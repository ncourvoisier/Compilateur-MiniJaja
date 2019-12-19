package fr.femtost.disc.minijaja.ast.decls;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.EvaluationCouplePasAPas;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTDecls;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public final class Dnil extends ASTDecls {

    @Override
    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JNil(), 0);
    }

    @Override
    public void interpreter(Memoire m) {
        //noop
    }

    @Override
    public boolean firstCheck(Memoire global) {
        return true;
    }

    @Override
    public boolean typeCheck(Memoire global) {
        return true;
    }

    @Override
    public void retirer(Memoire m) {
        //noop
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        while(l.get(0).indice > l.get(0).node.getMaxEtape()) {
            l.remove(0);
        }
        l.get(0).node.interpreterPasAPas(m, l, leval);
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
