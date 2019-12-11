package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.jcodes.JNil;

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
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public int getChainPosition() {
        return 0;
    }


    @Override
    public void typeCheck(Memoire m) {

    }
}
