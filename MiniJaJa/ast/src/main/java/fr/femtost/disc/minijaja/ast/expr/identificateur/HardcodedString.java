package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class HardcodedString extends Identifiant {

    public HardcodedString(String chain) {
        super(chain.substring(1, chain.length()-1));
    }

    @Override
    public String rewrite() {
        return "\"" + this.name + "\"";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new Push(name), new JNil()), 1);
    }

    @Override
    public Object eval(Memoire m){
        return name;
    }
}
