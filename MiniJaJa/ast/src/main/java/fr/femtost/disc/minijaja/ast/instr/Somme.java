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
        sb.append("(");
        sb.append(ident.rewrite());
        sb.append("+=");
        sb.append(expr.rewrite());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        if(ident instanceof Tableau) {
            CompilationCouple index = ((Tableau)ident).getIndex(actual + e.taille);

            return new CompilationCouple(JCodes.concatenate(e.jCodes, JCodes.concatRight(index.jCodes, new AInc(new JCIdent(ident.getName())))), e.taille + index.taille + 1);
        }
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Inc(new JCIdent(ident.getName()))), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        if(ident instanceof Tableau)
        {
            int v2 = ((Tableau) ident).evalIndex(m);
            try{m.getPile().AffecterValT(ident.getName(),(int)m.getPile().ValT(ident.getName(),v2)+(int)v,v2);}
            catch (PileException e){
                ASTLogger.getInstance().logError(e.toString());
            }
        }
        else {
            try {
                m.getPile().AffecterVal(ident.getName(),(int)(m.getPile().Val(ident.getName()))+(int)v);
            } catch (PileException e) {
                ASTLogger.getInstance().logError(e.toString());
            }
        }
    }


    @Override
    public void retirer(Memoire m) {

    }
}
