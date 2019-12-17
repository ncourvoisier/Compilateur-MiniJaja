package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;

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
    public boolean typeCheck(Memoire global, Memoire local) {
        if (typeRetour == null) {
            ASTLogger.getInstance().logError(this, "Utilisation de retour dans le main");
            return false;
        }
        return expr.typeCheck(global, local, typeRetour);
    }
}
