package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTDecls extends ASTNode {

    public abstract boolean firstCheck(Memoire global);
    public abstract boolean typeCheck(Memoire global);
    public abstract void retirer(Memoire m);
}
