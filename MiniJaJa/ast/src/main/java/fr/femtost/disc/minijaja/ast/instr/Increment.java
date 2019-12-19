package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.jcode.AInc;
import fr.femtost.disc.minijaja.jcode.Inc;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public class Increment extends ASTInstr {

    private ASTIdentGenerique ident;

    public Increment(ASTIdentGenerique ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("++");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        if (ident instanceof Tableau) {
            CompilationCouple index = ((Tableau) ident).getIndex(actual);

            return new CompilationCouple(JCodes.concatenate(index.jCodes,
                    new JChain(new Push(1), new JChain(new AInc(ident.getName()), new JNil()))),
                    index.taille + 2);
        }
        return new CompilationCouple(new JChain(new Push(1), new JChain(new Inc(ident.getName()), new JNil())), 2);
    }
    @Override
    public void interpreter(Memoire m){

        if(ident instanceof Tableau)
        {
            int v = ((Tableau) ident).evalIndex(m);
            try{
                m.getPile().affecterValT(ident.getName(),(int)m.getPile().valT(ident.getName(),v)+1,v);
            } catch (PileException e){
                ASTLogger.getInstance().logError(this,e.getMessage());
            }
        }
        else {
            try {
                m.getPile().affecterVal(ident.getName(),(int)(m.getPile().val(ident.getName()))+1);
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
            ASTLogger.getInstance().logError(this, "Incrément d'une constante : " + ident.getName());
            return false;
        }
        if (decl.getOBJ() == NatureObjet.METH) {
            ASTLogger.getInstance().logError(this, "Incrément d'un nom de méthode : " + ident.getName());
            return false;
        }
        if (decl.getOBJ() == NatureObjet.TAB) {
            if (!(ident instanceof Tableau)) {
                ASTLogger.getInstance().logError(this, "Incrément d'une adresse de tableau : " + ident.getName());
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

        if (decl.getSORTE() == Sorte.INT) {
            return true;
        } else {
            ASTLogger.getInstance().logError(this, "Incrément sur variable non-int " + ident.getName());
            return false;
        }
    }
}
