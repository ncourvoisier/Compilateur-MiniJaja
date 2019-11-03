package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Write;

public class Ecrire extends ASTInstr {

    protected Identifiant ident;

    public Ecrire(Identifiant ident) {
        this.ident = ident;
    }

    public Identifiant getIdent() {
        return ident;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("write");
        sb.append("(");
        sb.append(ident.rewrite());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = ident.compiler(actual);

        return new CompilationCouple(JCodes.concatRight(e.jCodes, new Write()), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        ident.eval(m);
        System.out.println(ident.toString());
    }

    @Override
    public void retirer(Memoire m) {

    }

}
