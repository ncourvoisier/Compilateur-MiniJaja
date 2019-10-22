package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCodes;
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
        if(ident instanceof Tableau) {
            CompilationCouple index = ((Tableau)ident).getIndex(actual + e.taille);
            return new CompilationCouple(JCodes.concatenate(e.jCodes, JCodes.concatRight(index.jCodes, new AStore(new JCIdent(ident.getName())))),
                    e.taille + index.taille + 1);
        }
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Store(new JCIdent(ident.getName()))), e.taille + 1);
    }
}
