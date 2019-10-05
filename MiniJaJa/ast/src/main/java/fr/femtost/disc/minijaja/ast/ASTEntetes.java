package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public abstract class ASTEntetes extends ASTNode {
    private ASTEntetes entetes;
    private ASTEntete entete;

    public ASTEntetes(ASTEntetes entetes, ASTEntete entete) {
        this.entetes = entetes;
        this.entete = entete;
    }

    public ASTEntetes getEntetes() {
        return entetes;
    }

    public ASTEntete getEntete() {
        return entete;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
