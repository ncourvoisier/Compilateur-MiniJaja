package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class AStore extends JCode {

    private String ident;

    public AStore(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "astore(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            Object v = m.getPile().depiler().getVAL();
            int index = (int) m.getPile().depiler().getVAL();
            m.getPile().affecterValT(ident, v, index);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
