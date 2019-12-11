package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
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
    public void typeCheck(Memoire m) {
        Boolean e = (Boolean) expr.eval(m);
        if (e == null) {
            System.out.println(e + "is not initialize.");
        }
    }

    @Override
    public Object eval(Memoire m) {
        Boolean e = (Boolean) expr.eval(m);
        return !e;
    }
}
