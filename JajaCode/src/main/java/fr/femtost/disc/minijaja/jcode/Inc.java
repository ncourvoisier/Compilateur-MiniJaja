package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class Inc extends JCode {

    private String ident;

    public Inc(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "inc(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            int v = (int) m.getPile().Depiler().getVAL();
            m.getPile().AffecterVal(ident, (int)m.getPile().Val(ident) + v);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
