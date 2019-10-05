package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTVars;

public class ASTMethode extends ASTDecl {

    public ASTVars getVars() {
        return vars;
    }

    public ASTMethode(ASTVars vars) {
        this.vars = vars;
    }

    private ASTVars vars;

    public String rewrite() {
        //TODO : implement
        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
