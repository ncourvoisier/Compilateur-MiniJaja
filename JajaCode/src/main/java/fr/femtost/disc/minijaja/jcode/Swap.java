package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class Swap extends JCode {

    @Override
    public String rewrite() {
        return "swap";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            m.getPile().Echanger();
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
            return -1;
        }
    }
}
