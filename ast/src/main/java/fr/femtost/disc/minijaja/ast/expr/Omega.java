package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public final class Omega extends ASTExpr {

    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(0), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        return null;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        return true;
    }

}
