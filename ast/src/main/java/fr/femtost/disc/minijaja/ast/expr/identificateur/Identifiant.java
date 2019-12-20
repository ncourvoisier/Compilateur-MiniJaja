package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.jcode.Load;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public class Identifiant extends ASTIdentGenerique {

    protected String name;

    public Identifiant(String name ) {
        this.name = name;
    }

    @Override
    public String rewrite() {
        return name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Load(name), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        try {
            return m.getPile().val(name);
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.getMessage());
        }
        return null;
    }

    @Override
    public Object tryEval(Memoire m, List<MethodeEvalTuple> evaluations) {
        return eval(m);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        Quad decl;
        if (local.containsSymbol(name)) {
            decl = local.getPile().returnQuadWithId(name);
        } else {
            if (global.containsSymbol(name)) {
                decl = global.getPile().returnQuadWithId(name);
            } else {
                ASTLogger.getInstance().logError(this, "Variable non déclarée : " + name);
                return false;
            }
        }

        if (decl.getSORTE() == expected || expected == Sorte.VOID) {
            return true;
        } else {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + decl.getSORTE().name());
            return false;
        }
    }

}
