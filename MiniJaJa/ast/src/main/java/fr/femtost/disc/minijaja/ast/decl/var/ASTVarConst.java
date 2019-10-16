package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;

public class ASTVarConst extends ASTVar {

    private ASTType type;

    public ASTVarConst(Identifiant identifiant, ASTType type) {
        super(identifiant);
        this.type = type;
    }

    public ASTVarConst(Identifiant identifiant, ASTExpr expr, ASTType type) {
        super(identifiant, expr);
        this.type = type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("final ");
        sb.append(type.rewrite()).append(" ").append(this.identifiant.rewrite());
        if(this.expr != null)
            sb.append(" = ").append(this.expr.rewrite());
        return sb.toString();
    }
}
