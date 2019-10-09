package fr.femtost.disc.minijaja.ast.type;

import fr.femtost.disc.minijaja.ast.ASTTypeMeth;

public final class Void extends ASTTypeMeth {

    public String rewrite() {
        return "void";
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }

}
