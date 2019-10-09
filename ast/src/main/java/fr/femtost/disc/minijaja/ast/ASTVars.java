package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;

public abstract class ASTVars extends ASTNode {

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
