package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class Nbre extends ASTExpr {

    private int expr;

    public Nbre(int expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return Integer.valueOf(expr).toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(new JCNbre(expr)), new JNil()), 1);
    }
}
