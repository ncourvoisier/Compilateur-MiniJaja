package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCBool;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class BoolVal extends ASTExpr {

    private boolean value;

    public BoolVal(boolean value) {
        this.value = value;
    }

    public String rewrite() {
        if(value){
            return "true";
        }else{
            return "false";
        }
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(new JCBool(value)), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m) {
        if (value) {
            return true;
        } else {
            return false;
        }
    }
}
