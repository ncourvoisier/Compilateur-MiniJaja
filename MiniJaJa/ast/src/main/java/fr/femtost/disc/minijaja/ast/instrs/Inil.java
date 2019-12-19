package fr.femtost.disc.minijaja.ast.instrs;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public final class Inil extends ASTInstrs {

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
        //fin de chaine
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        return true;
    }

    @Override
    public void forwardTypeRetour(Sorte sorte) {
        //fin de chaine
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
