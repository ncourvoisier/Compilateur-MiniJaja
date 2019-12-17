package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;

public final class Void extends ASTTypeMeth {

    @Override
    public String rewrite() {
        return "void";
    }

    @Override
    public Sorte getSorte() {
        return Sorte.VOID;
    }
}
