package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;

public class ASTMain extends ASTNode {

    private ASTVars vars;
    private ASTInstrs instrs;

    public ASTMain(ASTVars vars, ASTInstrs instrs) {
        this.vars = vars;
        this.instrs = instrs;
    }

    public ASTVars getVars() {
        return vars;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("main ");
        sb.append(" {\n");
        sb.append(vars.rewrite());
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }
}
