package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCChain;

public class HardcodedString extends Identifiant {

    public HardcodedString(String chain) {
        super(chain);
    }

    @Override
    public String rewrite() {
        return "\"" + this.name + "\"";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(new JCChain(name)), new JNil()), 1);
    }
}
