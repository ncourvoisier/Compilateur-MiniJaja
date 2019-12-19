package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTDecl extends ASTNode {

    public abstract void retirer(Memoire m);
    public abstract boolean typeCheck(Memoire global, Memoire local);
    public abstract boolean firstCheck(Memoire global);

    @Override
    public int getMaxEtape() {
        return 1;
    }
}
