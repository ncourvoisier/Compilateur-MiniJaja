package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class ASTClass extends ASTNode {

    private Identifiant ident;
    private ASTDecls decls;
    private ASTMain main;

    public ASTClass(Identifiant ident, ASTDecls decls, ASTMain main) {
        this.ident = ident;
        this.decls = decls;
        this.main = main;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ");
        sb.append(ident.rewrite());
        sb.append(" {\n");
        sb.append(decls.rewrite()).append("\n");
        sb.append(main.rewrite()).append("\n");
        sb.append("}\n");

        return sb.toString();
    }
}
