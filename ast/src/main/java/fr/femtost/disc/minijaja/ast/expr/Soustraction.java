package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;

public class Soustraction extends ASTExpr {
    private  ASTExpr expr2 ;
    private ASTExpr expr1;

    public Soustraction(ASTExpr expr2, ASTExpr expr1) {
        this.expr2 = expr2;
        this.expr1 = expr1;
    }

    public String rewrite() {
        return "(" + expr1.rewrite() + " - " + expr2.rewrite() + ")";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e1 = expr1.compiler(actual);
        CompilationCouple e2 = expr2.compiler(actual + e1.taille);

        return new CompilationCouple(JCodes.concatenate(e1.jCodes, JCodes.concatRight(e2.jCodes, new OpBinaire(OpBinaire.Operandes.SUB))), e1.taille + e2.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public Object eval(Memoire m) {
        int e1 = (int) expr1.eval(m);
        int e2 = (int) expr2.eval(m);
        return e1 - e2;
    }
}
