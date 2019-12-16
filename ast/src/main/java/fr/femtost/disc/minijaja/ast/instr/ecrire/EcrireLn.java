package fr.femtost.disc.minijaja.ast.instr.ecrire;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.Ecrire;
import fr.femtost.disc.minijaja.jcode.Write;
import fr.femtost.disc.minijaja.jcode.WriteLn;

public class EcrireLn extends Ecrire {

    public EcrireLn(Identifiant ident) {
        super(ident);
    }

    @Override
    public String rewrite() {
        return "writeln(" + ident.rewrite() + ")";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = ident.compiler(actual);

        return new CompilationCouple(JCodes.concatRight(e.jCodes, new WriteLn()), e.taille + 1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v =ident.eval(m);
        System.out.println(v);
        ASTLogger.getInstance().logInfo(v.toString() + "\n");
    }
}
