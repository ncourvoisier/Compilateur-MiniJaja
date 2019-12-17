package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.Sorte;

public final class Entier extends ASTType {

    @Override
    public String rewrite() {
        return "int";
    }


    @Override
    public Sorte getSorte() {
        return Sorte.INT;
    }
}
