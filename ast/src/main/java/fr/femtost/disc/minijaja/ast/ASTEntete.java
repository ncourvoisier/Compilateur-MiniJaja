package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;

public class ASTEntete extends ASTNode {
    private Identifiant ident;
    private ASTType type;



    public ASTEntete(Identifiant ident, ASTType type) {
        this.ident = ident;
        this.type = type;
    }

    public Identifiant getIdent() {
        return ident;
    }

    public ASTType getType() {
        return type;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.rewrite()).append(" ");
        sb.append(ident.rewrite());

        return sb.toString();
    }
}
