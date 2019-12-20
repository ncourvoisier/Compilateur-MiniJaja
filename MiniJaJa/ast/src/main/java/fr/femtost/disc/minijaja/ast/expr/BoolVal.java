package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class BoolVal extends ASTExpr {

    private boolean value;

    public BoolVal(boolean value) {
        this.value = value;
    }

    @Override
    public String rewrite() {
        if(value){
            return "true";
        }else{
            return "false";
        }
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(value), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        return value;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        if (expected == Sorte.BOOL || expected == Sorte.VOID)
            return true;
        else {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + Sorte.BOOL.name());
            return false;
        }
    }
}
