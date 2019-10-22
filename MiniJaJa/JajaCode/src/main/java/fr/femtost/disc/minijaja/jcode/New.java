package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class New extends JCode {

    private JCIdent ident;
    private JCType type;
    private JCSorte sorte;
    private JCVal val; //Profondeur dans la pile où chercher la valeur

    public New(JCIdent ident, JCType type, JCSorte sorte, JCVal val) {
        this.ident = ident;
        this.type = type;
        this.sorte = sorte;
        this.val = val;
    }
}
