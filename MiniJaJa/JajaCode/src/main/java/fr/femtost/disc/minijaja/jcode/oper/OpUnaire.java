package fr.femtost.disc.minijaja.jcode.oper;

import fr.femtost.disc.minijaja.jcode.Oper;

public class OpUnaire extends Oper {

    public enum Operandes {
        NEG,
        NOT
    }

    private Operandes op;

    public OpUnaire(Operandes op) {
        this.op = op;
    }
}
