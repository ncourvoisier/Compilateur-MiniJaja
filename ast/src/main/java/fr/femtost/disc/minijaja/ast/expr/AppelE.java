package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.AppelI;
import fr.femtost.disc.minijaja.jcode.Invoke;

public class AppelE extends ASTExpr {
    private Identifiant ident;
    private ASTListExpr listExpr;


    public AppelE(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

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

        return new CompilationCouple(JCodes.concatenate(lexp.jCodes, JCodes.concatLeft(new Invoke(new JCIdent(ident.getName())), retrait.jCodes)),
                lexp.taille + retrait.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {

    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public Object eval(Memoire m) {
        new AppelI(ident, listExpr).interpreter(m);
        try {
            return m.getPile().Val(ASTClass.VariableClasse);
        } catch (PileException e) {
            e.printStackTrace();
        }
        return null;
    }
}

