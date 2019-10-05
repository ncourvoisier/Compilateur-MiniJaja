package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.type.ASTType;

public abstract class ASTTypeMeth extends ASTNode {
    private ASTType type;

    public ASTTypeMeth() {

    }
    public ASTTypeMeth(ASTType type) {
        this.type = type;
    }

    public ASTType getType() {
        return type;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
