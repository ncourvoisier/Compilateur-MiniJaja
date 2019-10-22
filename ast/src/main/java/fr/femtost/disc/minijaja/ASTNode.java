package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JNil;

public abstract class ASTNode {

   public abstract String rewrite();

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }

    public CompilationCouple compiler(int actual) {
        throw new IllegalArgumentException("Compilation not implemented in " + this.getClass().getName());
        //Actuel manque retrait des déclarations
    }

    /*public abstract CompilationCouple compiler(int actual);*/

    public CompilationCouple retirerCompile(int actual) {
        return new CompilationCouple(new JNil(), actual);
    }
}
