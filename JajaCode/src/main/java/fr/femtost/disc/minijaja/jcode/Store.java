package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class Store extends JCode {

    private String ident;

    public Store(String ident) {
        this.ident = ident;
    }

    @Override
    public String rewrite() {
        return "store(" + ident + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            Object v = m.getPile().Depiler().getVAL();
            m.getPile().AffecterVal(ident, v);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
            return -1;
        }
    }
}
