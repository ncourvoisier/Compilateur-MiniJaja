package fr.femtost.disc.minijaja.ast.decls;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTDecls;
import fr.femtost.disc.minijaja.jcodes.JNil;

public final class Dnil extends ASTDecls {

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
}
