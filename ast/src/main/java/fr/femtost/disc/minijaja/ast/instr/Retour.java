package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;

import java.util.List;


public class Retour extends ASTInstr {

    private ASTExpr expr;
    private Sorte typeRetour;

    public Retour(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public void forwardTypeRetour(Sorte sorte) {
        this.typeRetour = sorte;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(expr.rewrite());
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return expr.compiler(actual);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        try {
            m.getPile().affecterVal(ASTClass.getVariableClass(), v);
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.toString());
        }
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        if (l.get(0).indice < 4) {
            if (helperPas(m, l, calls, expr.getAllCalls())) {
                l.get(0).indice = 4;
                Object v = expr.tryEval(m, calls);
                try {
                    m.getPile().affecterVal(ASTClass.getVariableClass(), v);
                } catch (PileException e) {
                    ASTLogger.getInstance().logError(this,e.toString());
                }
                if(!expr.getAllCalls().isEmpty()) {
                    cleanEvals(calls);
                }
            }
        }
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        if (typeRetour == null) {
            ASTLogger.getInstance().logError(this, "Utilisation de retour dans le main");
            return false;
        }
        return expr.typeCheck(global, local, typeRetour);
    }
}
