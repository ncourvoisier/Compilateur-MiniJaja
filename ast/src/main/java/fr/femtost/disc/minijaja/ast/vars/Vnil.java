package fr.femtost.disc.minijaja.ast.vars;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.ast.ASTVars;
import fr.femtost.disc.minijaja.jcodes.JNil;

public final class Vnil extends ASTVars {
    @Override
    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JNil(), 0);
    }
}
