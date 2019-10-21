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
}
