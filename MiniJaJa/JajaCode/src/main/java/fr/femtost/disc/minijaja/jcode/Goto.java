package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;

public class Goto extends JCode {

    private int adr;

    public Goto(int adr) {
        this.adr = adr;
    }

    @Override
    public String rewrite() {
        return "goto(" + adr + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        return adr;
    }
}
