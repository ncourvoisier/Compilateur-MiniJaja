package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.JCType;

public final class Entier extends ASTType {


    public String rewrite() {
        return "int";
    }

    @Override
    public JCType getType() {
        return JCType.ENTIER;
    }
}
