package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;

public class Increment extends ASTInstr {

    private ASTIdentGenerique identGenerique;

    public Increment(ASTIdentGenerique identGenerique) {
        this.identGenerique = identGenerique;
    }

    public ASTIdentGenerique getIdentGenerique() {
        return identGenerique;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(identGenerique.rewrite());
        sb.append("++");

        return sb.toString();
    }
}
