package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCType;
import fr.femtost.disc.minijaja.Sorte;

public abstract class ASTTypeMeth extends ASTNode {

    public abstract JCType getType();
    public abstract Sorte getSorte();
    @Override
    public CompilationCouple compiler(int actual) {
        throw new IllegalArgumentException("Compilation not implemented in Type");
    }
}
