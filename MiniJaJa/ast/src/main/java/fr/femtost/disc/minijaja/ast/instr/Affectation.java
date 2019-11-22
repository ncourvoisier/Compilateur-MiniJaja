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
            return new CompilationCouple(JCodes.concatenate(e.jCodes, JCodes.concatRight(index.jCodes, new AStore(new JCIdent(ident.getName())))),
                    e.taille + index.taille + 1);
        }
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Store(new JCIdent(ident.getName()))), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        if (ident instanceof Tableau) {
            int v2 = ((Tableau) ident).evalIndex(m);
            try {
                m.getPile().AffecterValT(ident.getName(), v, v2);
            } catch (PileException e) {
            }
        } else {
            try {
                m.getPile().AffecterVal(ident.getName(), v);
            } catch (PileException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void retirer(Memoire m) {

    }


}
