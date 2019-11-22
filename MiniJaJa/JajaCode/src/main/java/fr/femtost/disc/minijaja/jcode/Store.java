package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCode;

public class Store extends JCode {

    JCIdent ident;

    public Store(JCIdent ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "store(" + ident.rewrite() + ")";
    }
}
