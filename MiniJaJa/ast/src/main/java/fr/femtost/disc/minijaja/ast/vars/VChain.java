package fr.femtost.disc.minijaja.ast.vars;

import fr.femtost.disc.minijaja.ast.ASTVars;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;

public class VChain extends ASTVars {

    private ASTVar var;
    private ASTVars vars;

    public VChain(ASTVar var, ASTVars vars) {
        this.var = var;
        this.vars = vars;
    }

    public ASTVar getVar() {
        return var;
    }

    public ASTVars getVars() {
        return vars;
    }

}
