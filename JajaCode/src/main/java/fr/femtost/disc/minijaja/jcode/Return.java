package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class Return extends JCode {

    @Override
    public String rewrite() {
        return "return";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            return (int)m.getPile().depiler().getVAL();
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
