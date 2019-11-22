package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCType;
import fr.femtost.disc.minijaja.JCode;

public class NewArray extends JCode {

    private JCIdent ident;
    private JCType type;

    public NewArray(JCIdent ident, JCType type) {
        this.ident = ident;
        this.type = type;
    }

    @Override
    public String rewrite() {
        return "newarray(" + ident.rewrite() + "," + type.name() + ")";
    }
}
