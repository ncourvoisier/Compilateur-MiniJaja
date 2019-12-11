package fr.femtost.disc.minijaja.ast.instrs;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.jcodes.JNil;

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
        //noop
    }

    @Override
    public void typeCheck(Memoire m) {
        //noop
    }
}
