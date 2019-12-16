package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class Pop extends JCode {

    @Override
    public String rewrite() {
        return "pop";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            m.getPile().Depiler();
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
