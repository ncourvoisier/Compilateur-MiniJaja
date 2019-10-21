package fr.femtost.disc.minijaja.jcode.oper;

import fr.femtost.disc.minijaja.jcode.Oper;

public class OpBinaire extends Oper {

    public enum Operandes {
        ADD,
        SUB,
        MUL,
        DIV,
        CMP,
        SUP,
        AND,
        OR
    }

    private Operandes operandes;

    public OpBinaire(Operandes operandes) {
        this.operandes = operandes;
    }
}
