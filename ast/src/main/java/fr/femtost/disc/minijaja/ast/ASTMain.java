package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class ASTMain extends ASTNode {

    private ASTVars vars;
    private ASTInstrs instrs;

    public ASTMain(ASTVars vars, ASTInstrs instrs) {
        this.vars = vars;
        this.instrs = instrs;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("main {\n");
        sb.append(vars.rewrite());
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple dvs = vars.compiler(actual);
        CompilationCouple iss = instrs.compiler(actual + dvs.taille);
        CompilationCouple retrait = vars.retirerCompile(actual + dvs.taille + iss.taille + 1);

        JCodes codes = JCodes.concatenate(dvs.jCodes, JCodes.concatenate(iss.jCodes, JCodes.concatLeft(new Push(new JCNbre(0)), retrait.jCodes)));
        return new CompilationCouple(codes, dvs.taille + iss.taille + retrait.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        vars.interpreter(m);
        instrs.interpreter(m);
        vars.retirer(m);
    }

    @Override
    public void retirer(Memoire m) {
        throw new UnsupportedOperationException("Retrait de methode");
    }

    @Override
    public void typeCheck(Memoire m) {
        vars.typeCheck(m);
        instrs.typeCheck(m);
    }

}
