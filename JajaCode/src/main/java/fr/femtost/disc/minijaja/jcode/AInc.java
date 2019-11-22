package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCode;

public class AInc extends JCode {

    private JCIdent ident;

    public AInc(JCIdent ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "ainc(" + ident.rewrite() + ")";
    }
}
