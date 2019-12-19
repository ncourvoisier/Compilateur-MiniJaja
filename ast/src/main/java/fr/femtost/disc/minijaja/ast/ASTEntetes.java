package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTEntetes extends ASTNode {

    public abstract int getChainPosition();

    public abstract boolean typeCheck(Memoire global, Memoire local);
    public abstract void retirer(Memoire m);

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("");
    }
}
