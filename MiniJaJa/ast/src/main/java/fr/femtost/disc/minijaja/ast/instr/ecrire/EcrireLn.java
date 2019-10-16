package fr.femtost.disc.minijaja.ast.instr.ecrire;

import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.Ecrire;

public class EcrireLn extends Ecrire {

    public EcrireLn(Identifiant ident) {
        super(ident);
    }

    @Override
    public String rewrite() {
        return "writeln(" + ident.rewrite() + ")";
    }
}
