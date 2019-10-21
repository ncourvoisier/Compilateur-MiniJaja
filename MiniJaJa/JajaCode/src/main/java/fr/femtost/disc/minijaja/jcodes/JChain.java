package fr.femtost.disc.minijaja.jcodes;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.JCodes;

public class JChain extends JCodes {

    private JCode instruction;
    private JCodes next;

    public JChain(JCode instruction, JCodes next) {
        this.instruction = instruction;
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return next instanceof JChain;
    }

    @Override
    public JCodes next() {
        return next;
    }

    @Override
    public void setNext(JCodes next) {
        this.next = next;
    }
}
