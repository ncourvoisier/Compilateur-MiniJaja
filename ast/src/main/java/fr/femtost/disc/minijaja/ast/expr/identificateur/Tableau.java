package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Tableau extends ASTIdentGenerique {

    private String name;
    private ASTExpr index;

    public Tableau(String name, ASTExpr index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public ASTExpr getIndex() {
        return index;
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
