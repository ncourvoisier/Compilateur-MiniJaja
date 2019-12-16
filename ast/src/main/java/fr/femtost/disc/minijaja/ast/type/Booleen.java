package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

public final class Booleen extends ASTType {

    @Override
    public String rewrite() {
        return "boolean";
    }

    @Override
    public void typeCheck(Memoire m) {

    }

    @Override
    public Sorte getSorte() {
        return Sorte.BOOL;
    }
}
