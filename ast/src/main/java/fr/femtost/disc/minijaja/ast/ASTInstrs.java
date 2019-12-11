package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTInstrs extends ASTNode {

    @Override
    public void retirer(Memoire m) {
        throw new UnsupportedOperationException("Retrait d'instruction");
    }
}
