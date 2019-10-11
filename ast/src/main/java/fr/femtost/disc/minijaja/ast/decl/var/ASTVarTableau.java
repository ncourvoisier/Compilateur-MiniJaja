package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class ASTVarTableau extends ASTVar {

    private ASTTypeMeth typeMeth;

    public ASTVarTableau(Identifiant identifiant, ASTExpr expr, ASTTypeMeth typeMeth) {
        super(identifiant, expr);
        this.typeMeth = typeMeth;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(typeMeth.rewrite()).append(" ").append(this.getIdentifiant().rewrite());
        sb.append("[").append(this.getExpr().rewrite()).append("]");
        return sb.toString();
    }
}
