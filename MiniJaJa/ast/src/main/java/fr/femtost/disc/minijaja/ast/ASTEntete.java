package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCSorte;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.jcode.New;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class ASTEntete extends ASTNode {
    private Identifiant ident;
    private ASTType type;

    private int position;

    public ASTEntete(Identifiant ident, ASTType type) {
        this.ident = ident;
        this.type = type;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.rewrite()).append(" ");
        sb.append(ident.rewrite());

        return sb.toString();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new New(new JCIdent(ident.getName()), type.getType(), JCSorte.VARIABLE, new JCNbre(position)), new JNil()), 1);
    }
}
