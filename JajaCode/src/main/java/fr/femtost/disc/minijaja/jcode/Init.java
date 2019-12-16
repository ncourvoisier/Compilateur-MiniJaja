package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;

public class Init extends JCode {

    @Override
    public String rewrite() {
        return "init";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        return current+1;
    }
}
