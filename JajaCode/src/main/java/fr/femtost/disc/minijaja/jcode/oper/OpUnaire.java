package fr.femtost.disc.minijaja.jcode.oper;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.jcode.Oper;

public class OpUnaire extends Oper {

    public enum Operandes {
        NEG,
        NOT
    }

    private Operandes op;

    public OpUnaire(Operandes op) {
        this.op = op;
    }

    @Override
    public String rewrite() {
        return op.name().toLowerCase();
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            Object v = m.getPile().depiler().getVAL();
            switch (op) {
                case NOT:
                    m.getPile().declCst(null, !((boolean)v), Sorte.VOID);
                    return current+1;
                case NEG:
                    m.getPile().declCst(null, -((int)v), Sorte.VOID);
                    return current+1;
            }
            ASTLogger.getInstance().logWarning("Unexpected operator:" + op.name());
            return -1;
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
            return -1;
        }
    }
}
