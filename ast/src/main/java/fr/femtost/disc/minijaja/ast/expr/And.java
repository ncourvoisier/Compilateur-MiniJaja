package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;

public class And extends ASTExpr {

    private ASTExpr expr1;
    private ASTExpr expr2;

    public And(ASTExpr expr1, ASTExpr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public String rewrite() {
        return "(" + expr1.rewrite() + " && " + expr2.rewrite() + ")";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e1 = expr1.compiler(actual);
        CompilationCouple e2 = expr2.compiler(actual + e1.taille);

        return new CompilationCouple(JCodes.concatenate(e1.jCodes, JCodes.concatRight(e2.jCodes, new OpBinaire(OpBinaire.Operandes.AND))), e1.taille + e2.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public Object eval(Memoire m) {
        System.out.println("expr1 : "+expr1+"\nexpr2 :"+expr2);
        boolean e1 = (boolean) expr1.eval(m);
        boolean e2 = (boolean) expr2.eval(m);
        return e1 && e2;
    }
}
