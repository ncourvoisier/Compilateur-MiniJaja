package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.JCType;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;

public final class Void extends ASTTypeMeth {

    public String rewrite() {
        return "void";
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public JCType getType() {
        return JCType.VOID;
    }

    @Override
    public Sorte getSorte() {
        return null;
    }
}
