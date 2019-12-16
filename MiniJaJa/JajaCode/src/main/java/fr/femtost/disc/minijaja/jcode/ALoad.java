package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class ALoad extends JCode {

    private String ident;

    public ALoad(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "aload(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            int index = (int) m.getPile().Depiler().getVAL();
            m.getPile().DeclCst(null, m.getPile().ValT(ident, index), Sorte.VOID);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
            return -1;
        }
    }
}
