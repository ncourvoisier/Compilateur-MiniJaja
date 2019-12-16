package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class AInc extends JCode {

    private String ident;

    public AInc(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "ainc(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            int v = (int) m.getPile().Depiler().getVAL();
            int index = (int) m.getPile().Depiler().getVAL();
            m.getPile().AffecterValT(ident, (int) m.getPile().ValT(ident, index) + v, index);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
