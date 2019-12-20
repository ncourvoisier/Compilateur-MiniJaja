package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.AppelI;
import fr.femtost.disc.minijaja.jcode.Invoke;

import java.util.ArrayList;
import java.util.List;


public class AppelE extends ASTExpr {
    private Identifiant ident;
    private ASTListExpr listExpr;


    public AppelE(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    public ASTListExpr getListExpr() {
        return listExpr;
    }

    public String getIdent() {
        return ident.getName();
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

        return new CompilationCouple(JCodes.concatenate(lexp.jCodes, JCodes.concatLeft(new Invoke(ident.getName()), retrait.jCodes)),
                lexp.taille + retrait.taille + 1);
    }

    @Override
    public Object eval(Memoire m) {
        new AppelI(ident, listExpr).interpreter(m);
        try {
            return m.getPile().val(ASTClass.getVariableClass());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.toString());
        }
        return null;
    }

    @Override
    public Object tryEval(Memoire m, List<MethodeEvalTuple> evaluations) {
        for (MethodeEvalTuple tuple : evaluations) {
            if (tuple.appel == this)
                return tuple.retour;
        }
        ASTLogger.getInstance().logError(this, "Fail pas à pas : eval not found");
        return null;
    }

    @Override
    public List<AppelE> getAllCalls() {
        List<AppelE> result = listExpr.getAllCalls();
        result.add(this);
        return result;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        if (!global.containsSymbol(ident.getName())) {
            ASTLogger.getInstance().logError(this, "Fonction non déclarée : " + ident.getName());
            return false;
        }
        ASTMethode methode = (ASTMethode) global.getPile().parametre(ident.getName());
        if (methode.getTypeMeth().getSorte() == Sorte.VOID) {
            ASTLogger.getInstance().logError(this, "Usage d'une fonction sans retour dans une expression");
            return false;
        }
        boolean b1 = methode.getTypeMeth().getSorte() == expected  || expected == Sorte.VOID;
        if (!b1) {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got "
                    + methode.getTypeMeth().getSorte().name());
        }
        boolean b2 = listExpr.typeCheck(global, local, methode.getEntetes());
        return b1 && b2;
    }


}

