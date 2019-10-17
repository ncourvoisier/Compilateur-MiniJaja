package fr.femtost.disc.minijaja;

public class PileException extends Exception {

    public PileException(String msgErreur) {
        super(msgErreur);
    }

    public String toString() {
        return this.getMessage() + " -> " + this.getClass().getName();
    }

}
