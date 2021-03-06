package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class Write extends JCode {

    @Override
    public String rewrite() {
        return "write";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            ASTLogger.getInstance().logJJC(m.getPile().depiler().getVAL().toString());
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
