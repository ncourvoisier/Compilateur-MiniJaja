package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Invoke;
import fr.femtost.disc.minijaja.jcode.Pop;

public class AppelI extends ASTInstr {

    private Identifiant ident;
    private ASTListExpr listExpr;

    public AppelI(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("(");
        sb.append(listExpr.rewrite());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple lexp = listExpr.compiler(actual);
        CompilationCouple retrait = listExpr.retirerCompile(actual + lexp.taille + 1);

        return new CompilationCouple(JCodes.concatenate(lexp.jCodes, JCodes.concatLeft(new Invoke(new JCIdent(ident.getName())), JCodes.concatRight(retrait.jCodes, new Pop()))),
                lexp.taille + retrait.taille + 2);
    }

    @Override
    public void interpreter(Memoire m) {
//Manque ExpParam()
    }

    @Override
    public void retirer(Memoire m) {

    }
}
