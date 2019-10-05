package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;

public abstract class ASTVars extends ASTNode {
    private ASTVar var;
    private ASTVars vars;



    public ASTVars(ASTVar var, ASTVars vars) {
        this.var = var;
        this.vars = vars;
    }
    public ASTVar getVar() {
        return var;
    }

    public ASTVars getVars() {
        return vars;
    }


    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
