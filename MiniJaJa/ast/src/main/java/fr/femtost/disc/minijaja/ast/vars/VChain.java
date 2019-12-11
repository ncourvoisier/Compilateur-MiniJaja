package fr.femtost.disc.minijaja.ast.vars;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTVars;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;

public class VChain extends ASTVars {

    private ASTVar var;
    private ASTVars vars;

    public VChain(ASTVar var, ASTVars vars) {
        this.var = var;
        this.vars = vars;
    }

    public ASTVar getVar() {
        return var;
    }

    public ASTVars getVars() {
        return vars;
    }


    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(var.rewrite()).append(";\n");
        sb.append(vars.rewrite());
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple dv = var.compiler(actual);
        CompilationCouple dvs = vars.compiler(actual + dv.taille);

        return new CompilationCouple(JCodes.concatenate(dv.jCodes, dvs.jCodes), dv.taille + dvs.taille);
    }

    @Override
    public CompilationCouple retirerCompile(int actual) {
        CompilationCouple vs = vars.retirerCompile(actual);
        CompilationCouple top = var.retirerCompile(actual + vs.taille);

        return new CompilationCouple(JCodes.concatenate(vs.jCodes, top.jCodes), vs.taille + top.taille);
    }

    @Override
    public void interpreter(Memoire m) {
        var.interpreter(m);
        vars.interpreter(m);

    }

    @Override
    public void retirer(Memoire m) {
        vars.retirer(m);
        var.retirer(m);
    }


    @Override
    public void typeCheck(Memoire m) {
        vars.typeCheck(m);
        var.typeCheck(m);
    }
}
