package fr.femtost.disc.minijaja.ast.listexpr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.jcodes.JNil;

public final class Exnil extends ASTListExpr {

    @Override
    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JNil(), 0);
    }
}
