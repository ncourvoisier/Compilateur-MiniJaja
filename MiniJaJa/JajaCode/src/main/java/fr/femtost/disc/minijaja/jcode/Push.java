package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCVal;
import fr.femtost.disc.minijaja.JCode;

public class Push extends JCode {

    private JCVal val;

    public Push(JCVal val) {
        this.val = val;
    }

    @Override
    public String rewrite() {
        return "push(" + val.rewrite() + ")";
    }
}
