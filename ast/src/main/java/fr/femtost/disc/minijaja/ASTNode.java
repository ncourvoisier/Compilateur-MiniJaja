package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JNil;

public abstract class ASTNode {

    public abstract String rewrite();

    public void typeCheck(Memoire m){
        //remettre en abstract a la fin du dev
    }


    public CompilationCouple compiler(int actual) {
        throw new IllegalArgumentException("Compilation not implemented in " + this.getClass().getName());
    }

    public CompilationCouple retirerCompile(int actual) {
    return new CompilationCouple(new JNil(), 0);
    }

    abstract public void interpreter(Memoire m);
    abstract public void retirer(Memoire m);

}

