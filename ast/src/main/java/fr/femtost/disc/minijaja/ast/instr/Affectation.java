package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.jcode.AStore;
import fr.femtost.disc.minijaja.jcode.Store;

public class Affectation extends ASTInstr {

    private ASTIdentGenerique ident;
    private ASTExpr expr;

    public Affectation(ASTIdentGenerique ident, ASTExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("=");
        sb.append(expr.rewrite());

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        if (ident instanceof Tableau) {
            CompilationCouple index = ((Tableau) ident).getIndex(actual + e.taille);
            return new CompilationCouple(JCodes.concatenate(e.jCodes, JCodes.concatRight(index.jCodes, new AStore(ident.getName()))),
                    e.taille + index.taille + 1);
        }
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Store(ident.getName())), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        if (ident instanceof Tableau) {
            int v2 = ((Tableau) ident).evalIndex(m);
            try {
                m.getPile().affecterValT(ident.getName(), v, v2);
            } catch (PileException e) {
                ASTLogger.getInstance().logError(this,e.toString());
            }
        } else {
            try {
                m.getPile().affecterVal(ident.getName(), v);
            } catch (PileException e) {
                ASTLogger.getInstance().logError(this,e.toString());
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
                if (! ((Tableau) ident).checkIndex(global, local)) {
                    return false;
                }
            }
        }

        if (decl.getOBJ() == NatureObjet.VAR && ident instanceof Tableau) {
            ASTLogger.getInstance().logError(this, "Variable simple utilisée comme tableau " + ident.getName());
            return false;
        }
        return expr.typeCheck(global, local, decl.getSORTE());
    }
}
