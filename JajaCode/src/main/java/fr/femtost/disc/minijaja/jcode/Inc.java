package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCode;

public class Inc extends JCode {

    private JCIdent ident;

    public Inc(JCIdent ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "inc(" + ident.rewrite() + ")";
    }
}
