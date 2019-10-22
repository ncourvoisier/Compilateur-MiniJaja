package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Identifiant extends ASTIdentGenerique {

    protected String name;

    public Identifiant(String name) {
        this.name = name;
    }

    public String rewrite() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CompilationCouple compiler(int actual) {
        throw new IllegalArgumentException("Compilation not implemented in Identifiant");
    }
}
