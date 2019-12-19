package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.jcode.Push;

import java.util.List;

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

        JCodes codes = JCodes.concatenate(dvs.jCodes, JCodes.concatenate(iss.jCodes, JCodes.concatLeft(new Push(0), retrait.jCodes)));
        return new CompilationCouple(codes, dvs.taille + iss.taille + retrait.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        vars.interpreter(m);
        instrs.interpreter(m);
        vars.retirer(m);
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        switch (l.get(0).indice)
        {
            case 1:
                l.get(0).indice = 2;
                l.add(new InterpretationPasAPasCouple(vars,1));
                vars.interpreterPasAPas(m,l);
                break;

            case 2:
                l.get(0).indice = 3;
                l.add(new InterpretationPasAPasCouple(instrs,1));
                instrs.interpreterPasAPas(m,l);
                break;

            case 3:
                l.get(0).indice = 4;
                vars.retirer(m);
                while(l.get(0).indice > l.get(0).node.getMaxEtape()) {
                    l.remove(0);
                }
                l.get(0).node.interpreterPasAPas(m, l);
                break;


            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);;
        }
    }

    @Override
    public int getMaxEtape() {
        return 3;
    }

    public boolean typeCheck(Memoire m) {
        Memoire locale = new Memoire(128);
        if (vars.typeCheck(m, locale)) {
            instrs.forwardTypeRetour(null);
            return instrs.typeCheck(m, locale);
        }
        return false;
    }

}
