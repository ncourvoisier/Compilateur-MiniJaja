package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class BoolVal extends ASTExpr {

    private boolean value;

    public BoolVal(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public String rewrite() {
        //TODO : implement
        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
