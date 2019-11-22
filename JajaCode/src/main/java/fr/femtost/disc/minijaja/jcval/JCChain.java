package fr.femtost.disc.minijaja.jcval;

import fr.femtost.disc.minijaja.JCVal;

public class JCChain extends JCVal {

    private String s;

    public JCChain(String s) {
        this.s = s;
    }

    @Override
    public String rewrite() {
        return "\"" + s + "\"";
    }
}
