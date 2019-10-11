package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class ASTVarSimple extends ASTVar {

    private ASTTypeMeth typeMeth;

    public ASTVarSimple(Identifiant identifiant, ASTTypeMeth typeMeth) {
        super(identifiant);
        this.typeMeth = typeMeth;
    }

    public ASTVarSimple(Identifiant identifiant, ASTExpr expr, ASTTypeMeth typeMeth) {
        super(identifiant, expr);
        this.typeMeth = typeMeth;
    }

    public ASTTypeMeth getTypeMeth() {
        return typeMeth;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(typeMeth.rewrite()).append(" ").append(this.getIdentifiant().rewrite());
        if(this.getExpr() != null)
            sb.append(" = ").append(this.getExpr().rewrite());
        return sb.toString();
    }
}
