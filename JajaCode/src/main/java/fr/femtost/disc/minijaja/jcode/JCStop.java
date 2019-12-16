package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;

public class JCStop extends JCode {

    @Override
    public String rewrite() {
        return "jcstop";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        return -1;
    }
}
