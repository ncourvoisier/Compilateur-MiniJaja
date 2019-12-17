package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class Load extends JCode {

    private String ident;

    public Load(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "load(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            m.getPile().declCst(null, m.getPile().val(ident), Sorte.VOID);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
