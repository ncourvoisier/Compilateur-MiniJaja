package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.JCType;
import fr.femtost.disc.minijaja.Sorte;

public final class Entier extends ASTType {


    public String rewrite() {
        return "int";
    }

    @Override
    public JCType getType() {
        return JCType.ENTIER;
    }

    @Override
    public Sorte getSorte() {
        return Sorte.INT;
    }
}
