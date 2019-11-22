package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCode;

public class If extends JCode {

    private int adr;

    public If(int adr) {
        this.adr = adr;
    }

    @Override
    public String rewrite() {
        return "if(" + adr + ")";
    }
}
