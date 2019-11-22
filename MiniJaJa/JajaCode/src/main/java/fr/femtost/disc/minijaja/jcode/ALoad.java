package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCode;

public class ALoad extends JCode {

    JCIdent ident;

    public ALoad(JCIdent ident) {
        this.ident = ident;
    }


    @Override
    public String rewrite() {
        return "aload(" + ident.rewrite() + ")";
    }
}
