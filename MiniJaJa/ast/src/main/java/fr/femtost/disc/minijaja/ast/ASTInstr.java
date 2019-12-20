package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.expr.AppelE;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;

import java.util.List;

public abstract class ASTInstr extends ASTNode {

    public abstract boolean typeCheck(Memoire global, Memoire local);

    public void forwardTypeRetour(Sorte sorte) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 3;
    }
}
