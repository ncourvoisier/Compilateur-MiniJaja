package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class Ecrire extends ASTInstr {

    private Identifiant ident;

    public Ecrire(Identifiant ident) {
        this.ident = ident;
    }

    public Identifiant getIdent() {
        return ident;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("write");
        sb.append("(");
        sb.append(ident.rewrite());
        sb.append(")");
        return sb.toString();
    }
}
