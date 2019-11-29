package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.jcode.Load;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class Identifiant extends ASTIdentGenerique {

    protected String name;


    public Identifiant(String name ) {
        this.name = name;
    }

    public String rewrite() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Load(new JCIdent(name)), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        try {
            return m.getPile().Val(name);
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.toString());
        }
        return null;
    }
}
