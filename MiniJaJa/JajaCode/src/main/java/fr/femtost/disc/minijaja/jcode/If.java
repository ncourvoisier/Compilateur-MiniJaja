package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;

public class If extends JCode {

    private int adr;

    public If(int adr) {
        this.adr = adr;
    }

    @Override
    public String rewrite() {
        return "if(" + adr + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            boolean b = (boolean) m.getPile().depiler().getVAL();
            if (b)
                return adr;
            else
                return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
