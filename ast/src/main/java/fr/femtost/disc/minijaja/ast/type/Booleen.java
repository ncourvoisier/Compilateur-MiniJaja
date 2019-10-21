package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.JCType;

public final class Booleen extends ASTType {

    public String rewrite() {
        return "boolean";
    }

    @Override
    public JCType getType() {
        return JCType.BOOLEEN;
    }
}
