package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCode;

public class Invoke extends JCode {

    private JCIdent ident;

    public Invoke(JCIdent ident) {
        this.ident = ident;
    }
}
