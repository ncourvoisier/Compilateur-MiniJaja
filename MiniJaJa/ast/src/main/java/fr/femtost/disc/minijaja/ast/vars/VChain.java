package fr.femtost.disc.minijaja.ast.vars;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTVars;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;

import java.util.List;

public class VChain extends ASTVars {

    private ASTVar var;
    private ASTVars vars;

    public VChain(ASTVar var, ASTVars vars) {
        this.var = var;
        this.vars = vars;
        this.setPosition(var.getLine(), var.getColumn());
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
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        switch(l.get(0).indice)
        {
            case 1:
                l.get(0).indice =2;
                l.add(0, new InterpretationPasAPasCouple(var,1));
                var.interpreterPasAPas(m,l, calls);
                break;

            case 2 :
                l.get(0).indice = 3;
                l.add(0, new InterpretationPasAPasCouple(vars,1));
                vars.interpreterPasAPas(m,l, calls);
                break;

            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);
        }
    }

    @Override
    public int getMaxEtape() {
        return 2;
    }

    @Override
    public void retirer(Memoire m) {
        vars.retirer(m);
        var.retirer(m);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        boolean b = var.typeCheck(global, local);
        boolean b1 = vars.typeCheck(global, local);
        return b && b1;
    }

}
