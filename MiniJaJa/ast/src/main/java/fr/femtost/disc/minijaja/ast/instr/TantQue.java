package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.ast.expr.BoolVal;
import fr.femtost.disc.minijaja.jcode.Goto;
import fr.femtost.disc.minijaja.jcode.If;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

import java.util.List;

public class TantQue extends ASTInstr {

    private ASTInstrs instrs;
    private ASTExpr expr;

    public TantQue(ASTExpr expr, ASTInstrs instrs) {
        this.instrs = instrs;
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("while");
        sb.append("(").append(expr.rewrite()).append(")");
        sb.append(" {\n");
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        CompilationCouple iss = instrs.compiler(actual + e.taille + 2);

        JCodes builder = JCodes.concatLeft(new OpUnaire(OpUnaire.Operandes.NOT), JCodes.concatLeft(new If(actual + e.taille + iss.taille + 3),
                JCodes.concatRight(iss.jCodes, new Goto(actual))));
        return new CompilationCouple(JCodes.concatenate(e.jCodes, builder), e.taille + iss.taille + 3);
    }

    @Override
    public void interpreter(Memoire m) {
        Boolean ee = (Boolean)expr.eval(m);
        if (ee) {
            instrs.interpreter(m);
            this.interpreter(m);
        }
    }

    @Override
    public void forwardTypeRetour(Sorte sorte) {
        instrs.forwardTypeRetour(sorte);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        boolean b1 = expr.typeCheck(global, local, Sorte.BOOL);
        boolean b2 = instrs.typeCheck(global, local);
        if (expr instanceof BoolVal) {
            ASTLogger.getInstance().logWarning(expr, "Utilisation d'une constante dans une boucle while");
        }
        return b1 && b2;
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        switch (l.get(0).indice) {
            case 1 :
                Boolean ee = (Boolean)expr.eval(m);
                if(ee) {
                    l.get(0).indice = 2;
                } else {
                    l.get(0).indice = 3;
                }
                break;
            case 2 :
                l.get(0).indice = 1;
                l.add(0, new InterpretationPasAPasCouple(instrs, 1));
                instrs.interpreterPasAPas(m, l, leval);
                break;
            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);
        }
    }

    @Override
    public int getMaxEtape() {
        return 2;
    }
}
