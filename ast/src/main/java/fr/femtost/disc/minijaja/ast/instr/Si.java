package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.ast.expr.BoolVal;
import fr.femtost.disc.minijaja.ast.instrs.IChain;
import fr.femtost.disc.minijaja.ast.instrs.Inil;
import fr.femtost.disc.minijaja.jcode.Goto;
import fr.femtost.disc.minijaja.jcode.If;

import java.util.List;

public class Si extends ASTInstr {

    private ASTExpr expr;
    private ASTInstrs instrsIf;
    private ASTInstrs instrsElse;

    public Si(ASTExpr expr, ASTInstrs instrsIf, ASTInstrs instrsElse) {
        this.expr = expr;
        this.instrsIf = instrsIf;
        this.instrsElse = instrsElse;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("if (");
        sb.append(expr.rewrite()).append(") {\n");
        sb.append(instrsIf.rewrite()).append("}");
        if (instrsElse instanceof IChain) {
            sb.append(" else {\n");
            sb.append(instrsElse.rewrite());
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        CompilationCouple stElse = instrsElse.compiler(actual + e.taille + 1);
        CompilationCouple stThen = instrsIf.compiler(actual + e.taille + stElse.taille + 2);

        JCodes s1 = JCodes.concatLeft(new Goto(actual + e.taille + stElse.taille + stThen.taille + 2), stThen.jCodes);
        JCodes s2 = JCodes.concatenate(stElse.jCodes, s1);
        JCodes s3 = JCodes.concatLeft(new If(actual + e.taille + stElse.taille + 2), s2);

        return new CompilationCouple(JCodes.concatenate(e.jCodes, s3), e.taille + stElse.taille + stThen.taille + 2);
    }

    @Override
    public void interpreter(Memoire m) {
        Boolean ee = (Boolean)expr.eval(m);
        if (ee) {
            instrsIf.interpreter(m);
        } else {
            instrsElse.interpreter(m);
        }
    }

    @Override
    public void forwardTypeRetour(Sorte sorte) {
        instrsIf.forwardTypeRetour(sorte);
        instrsElse.forwardTypeRetour(sorte);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        boolean b1 = expr.typeCheck(global, local, Sorte.BOOL);
        if (expr instanceof BoolVal) {
            ASTLogger.getInstance().logWarning(expr, "Utilisation d'une constante dans une condition");
        }
        boolean b2 = instrsIf.typeCheck(global, local);
        boolean b3 = instrsElse.typeCheck(global, local);
        if (instrsIf instanceof Inil) {
            ASTLogger.getInstance().logWarning(instrsIf, "Bloc if vide");
        }
        return b1 && b2 && b3;
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
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
                l.get(0).indice = 4;
                l.add(0, new InterpretationPasAPasCouple(instrsIf, 1));
                instrsIf.interpreterPasAPas(m, l);
                break;
            case 3 :
                l.get(0).indice = 4;
                l.add(0, new InterpretationPasAPasCouple(instrsElse, 1));
                instrsElse.interpreterPasAPas(m, l);
                break;
            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);
        }
    }

    @Override
    public int getMaxEtape() {
        return 3;
    }
}
