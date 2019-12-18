package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.jcode.AInc;
import fr.femtost.disc.minijaja.jcode.Inc;

public class Somme extends ASTInstr {

    private ASTIdentGenerique ident;
    private ASTExpr expr;

    public Somme(ASTIdentGenerique ident, ASTExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("+=");
        sb.append(expr.rewrite());
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        if(ident instanceof Tableau) {
            CompilationCouple index = ((Tableau)ident).getIndex(actual + e.taille);

            return new CompilationCouple(JCodes.concatenate(e.jCodes, JCodes.concatRight(index.jCodes, new AInc(ident.getName()))), e.taille + index.taille + 1);
        }
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Inc(ident.getName())), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        if(ident instanceof Tableau)
        {
            int v2 = ((Tableau) ident).evalIndex(m);
            try {
                m.getPile().affecterValT(ident.getName(),(int)m.getPile().valT(ident.getName(),v2)+(int)v,v2);
            } catch (PileException e){
                ASTLogger.getInstance().logError(this,e.getMessage());
            }
        }
        else {
            try {
                m.getPile().affecterVal(ident.getName(),(int)(m.getPile().val(ident.getName()))+(int)v);
            } catch (PileException e) {
                ASTLogger.getInstance().logError(this,e.getMessage());
            }
        }
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        Quad decl;
        if (local.containsSymbol(ident.getName())) {
            decl = local.getPile().returnQuadWithId(ident.getName());
        } else {
            if (global.containsSymbol(ident.getName())) {
                decl = global.getPile().returnQuadWithId(ident.getName());
            } else {
                ASTLogger.getInstance().logError(this, "Variable non déclarée : " + ident.getName());
                return false;
            }
        }
        if (decl.getOBJ() == NatureObjet.CST) {
            ASTLogger.getInstance().logError(this, "Affectation d'une constante : " + ident.getName());
            return false;
        }
        if (decl.getOBJ() == NatureObjet.METH) {
            ASTLogger.getInstance().logError(this, "Affectation d'un nom de méthode : " + ident.getName());
            return false;
        }
        if (decl.getOBJ() == NatureObjet.TAB) {
            if (!(ident instanceof Tableau)) {
                ASTLogger.getInstance().logError(this, "Affectation d'une adresse de tableau : " + ident.getName());
                return false;
            } else {
                boolean b1 = decl.getSORTE() == Sorte.INT;
                if(!b1) {
                    ASTLogger.getInstance().logError(this, "Somme sur variable non-int " + ident.getName());
                }
                boolean b2 = ((Tableau) ident).checkIndex(global, local);
                return b1 && b2;
            }
        }

        if (decl.getOBJ() == NatureObjet.VAR && ident instanceof Tableau) {
            ASTLogger.getInstance().logError(this, "Variable simple utilisée comme tableau " + ident.getName());
            return false;
        }
        if (decl.getSORTE() == Sorte.INT) {
            return expr.typeCheck(global, local, decl.getSORTE());
        } else {
            ASTLogger.getInstance().logError(this, "Somme sur variable non-int " + ident.getName());
            return false;
        }
    }
}
