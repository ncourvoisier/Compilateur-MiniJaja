package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
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
        Object v =ident.eval(m);
        System.out.print(v);
        ASTLogger.getInstance().logInfo(v.toString());
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        if (ident instanceof HardcodedString)
            return true;

        if (local.containsSymbol(ident.getName())) {
            return true;
        } else {
            if (global.containsSymbol(ident.getName())) {
                return true;
            } else {
                ASTLogger.getInstance().logError(this, "Variable non déclarée : " + ident.getName());
                return false;
            }
        }
    }
}
