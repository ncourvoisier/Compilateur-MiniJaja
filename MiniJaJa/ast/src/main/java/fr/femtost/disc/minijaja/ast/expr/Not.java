package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

public class Not extends ASTExpr {

    private ASTExpr expr;

    public Not(ASTExpr expr) {
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        return "!" + expr.rewrite();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new OpUnaire(OpUnaire.Operandes.NOT)), e.taille + 1);
    }

    @Override
    public Object eval(Memoire m) {
        Boolean e = (Boolean) expr.eval(m);
        return !e;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        boolean b1 = expected == Sorte.BOOL || expected == Sorte.VOID;
        if (!b1) {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + Sorte.BOOL.name());
        }
        boolean b2 = expr.typeCheck(global, local, Sorte.BOOL);
        return b1 && b2;
    }
}
