package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;

public abstract class ASTExpr extends ASTNode {
    abstract public Object eval(Memoire m);

    @Override
    public void interpreter(Memoire m) {
        throw new UnsupportedOperationException("Interpretation sur expression");
    }

    public abstract boolean typeCheck(Memoire global, Memoire local, Sorte expected);
}
