package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Identifiant extends ASTIdentGenerique {

    private String name;

    public Identifiant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
