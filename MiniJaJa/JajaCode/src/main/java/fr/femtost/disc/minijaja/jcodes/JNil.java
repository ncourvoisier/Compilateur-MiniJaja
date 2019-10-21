package fr.femtost.disc.minijaja.jcodes;

import fr.femtost.disc.minijaja.JCodes;

public final class JNil extends JCodes {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public JCodes next() {
        throw new NullPointerException("Usage of next() on end of JajaCode instruction chain");
    }

    @Override
    public void setNext(JCodes next) {
        throw new NullPointerException("Usage of setNext() on end of JajaCode instruction chain");
    }
}
