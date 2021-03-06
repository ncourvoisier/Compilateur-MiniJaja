package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTVars extends ASTNode {

    public abstract void retirer(Memoire m);

    public abstract boolean typeCheck(Memoire global, Memoire local);
}
