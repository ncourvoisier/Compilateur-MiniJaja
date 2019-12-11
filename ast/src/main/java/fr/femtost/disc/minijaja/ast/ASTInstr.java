package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTInstr extends ASTNode {

    @Override
    public void retirer(Memoire m) {
        throw new UnsupportedOperationException("Retrait instruction");
    }
}
