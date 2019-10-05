package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.AstIdent;
import fr.femtost.disc.minijaja.ast.type.ASTType;

public class ASTEntete extends ASTNode {
    private AstIdent ident;
    private ASTType type;



    public ASTEntete(AstIdent ident, ASTType type) {
        this.ident = ident;
        this.type = type;
    }

    public AstIdent getIdent() {
        return ident;
    }

    public ASTType getType() {
        return type;
    }

    public String rewrite() {

        return null;
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
