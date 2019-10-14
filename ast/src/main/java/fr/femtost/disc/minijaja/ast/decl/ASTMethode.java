package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class ASTMethode extends ASTDecl {

    private ASTVars vars;
    private ASTTypeMeth typeMeth;
    private Identifiant ident;
    private ASTEntetes entetes;
    private ASTInstrs instrs;

    public ASTMethode(ASTVars vars, ASTTypeMeth typeMeth, Identifiant ident, ASTEntetes entetes, ASTInstrs instrs) {
        this.vars = vars;
        this.typeMeth = typeMeth;
        this.ident = ident;
        this.entetes = entetes;
        this.instrs = instrs;
    }

    public ASTVars getVars() {
        return vars;
    }

    public ASTTypeMeth getTypeMeth() {
        return typeMeth;
    }

    public Identifiant getIdent() {
        return ident;
    }

    public ASTEntetes getEntetes() {
        return entetes;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(typeMeth.rewrite()).append((" "));
        sb.append(ident.rewrite()).append(" ");
        sb.append("( ").append(entetes.rewrite()).append(" )");
        sb.append("{\n");
        sb.append(vars.rewrite());
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }
}
