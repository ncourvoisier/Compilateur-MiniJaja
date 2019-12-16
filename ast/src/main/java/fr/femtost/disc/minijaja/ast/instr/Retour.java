package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;

public class Retour extends ASTInstr {

    private ASTExpr expr;

    public Retour(ASTExpr expr) {
        this.expr = expr;
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
            m.getPile().AffecterVal(ASTClass.getVariableClass(), v);
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.toString());
        }
    }


    @Override
    public void typeCheck(Memoire m) {

    }
}
