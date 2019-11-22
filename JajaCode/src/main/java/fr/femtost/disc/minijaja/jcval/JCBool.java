package fr.femtost.disc.minijaja.jcval;

import fr.femtost.disc.minijaja.JCVal;

public class JCBool extends JCVal {

    private boolean val;

    public JCBool(boolean val) {
        this.val = val;
    }

    @Override
    public String rewrite() {
        return val ? "vrai" : "faux";
    }
}
