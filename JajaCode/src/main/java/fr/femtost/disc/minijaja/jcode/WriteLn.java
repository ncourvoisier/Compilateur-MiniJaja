package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class WriteLn extends Write {

    @Override
    public String rewrite() {
        return "writeln";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            ASTLogger.getInstance().logJJC(m.getPile().Depiler().getVAL().toString() + "\n");
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
