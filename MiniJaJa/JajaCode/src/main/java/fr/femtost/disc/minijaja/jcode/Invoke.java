package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class Invoke extends JCode {

    private String ident;

    public Invoke(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "invoke(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        m.getPile().DeclCst(null, current+1, Sorte.VOID);
        try {
            return (int) m.getPile().Val(ident);
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
