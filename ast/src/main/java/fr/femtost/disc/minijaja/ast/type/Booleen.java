package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.JCType;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

public final class Booleen extends ASTType {

    public String rewrite() {
        return "boolean";
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public JCType getType() {
        return JCType.BOOLEEN;
    }

    @Override
    public Sorte getSorte() {
        return Sorte.BOOL;
    }
}
