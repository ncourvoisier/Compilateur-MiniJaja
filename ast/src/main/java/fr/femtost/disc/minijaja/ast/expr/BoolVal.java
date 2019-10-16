package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ast.ASTExpr;

public class BoolVal extends ASTExpr {

    private boolean value;

    public BoolVal(boolean value) {
        this.value = value;
    }

    public String rewrite() {
        if(value){
            return "true";
        }else{
            return "false";
        }
    }
}
