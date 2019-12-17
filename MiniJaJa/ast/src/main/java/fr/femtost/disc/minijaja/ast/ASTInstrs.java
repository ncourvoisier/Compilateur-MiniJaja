package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

public abstract class ASTInstrs extends ASTNode {

    public abstract boolean typeCheck(Memoire global, Memoire local);

    public abstract void forwardTypeRetour(Sorte sorte);
}
