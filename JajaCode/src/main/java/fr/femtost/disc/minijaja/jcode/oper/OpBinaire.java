package fr.femtost.disc.minijaja.jcode.oper;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;
import fr.femtost.disc.minijaja.Sorte;
import fr.femtost.disc.minijaja.jcode.Oper;

public class OpBinaire extends Oper {

    public enum Operandes {
        ADD,
        SUB,
        MUL,
        DIV,
        CMP,
        SUP,
        AND,
        OR
    }

    private Operandes operandes;

    public OpBinaire(Operandes operandes) {
        this.operandes = operandes;
    }

    @Override
    public String rewrite() {
        return operandes.name().toLowerCase();
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            Object v2 = m.getPile().depiler().getVAL();
            Object v1 = m.getPile().depiler().getVAL();
            switch (operandes) {
                case OR:
                    m.getPile().declCst(null, (boolean)v1 || (boolean)v2, Sorte.VOID);
                    return current+1;
                case ADD:
                    m.getPile().declCst(null, (int)v1 + (int)v2, Sorte.VOID);
                    return current+1;
                case AND:
                    m.getPile().declCst(null, (boolean)v1 && (boolean)v2, Sorte.VOID);
                    return current+1;
                case CMP:
                    m.getPile().declCst(null, v1.equals(v2), Sorte.VOID);
                    return current+1;
                case DIV:
                    m.getPile().declCst(null, (int)v1 / (int)v2, Sorte.VOID);
                    return current+1;
                case MUL:
                    m.getPile().declCst(null, (int)v1 * (int)v2, Sorte.VOID);
                    return current+1;
                case SUB:
                    m.getPile().declCst(null, (int)v1 - (int)v2, Sorte.VOID);
                    return current+1;
                case SUP:
                    m.getPile().declCst(null, (int)v1 > (int)v2, Sorte.VOID);
                    return current+1;
                default:
                    ASTLogger.getInstance().logWarning("Opérateur non trouvé");
            }
            ASTLogger.getInstance().logWarningJJC("Unexpected operator:" + operandes.name());
            return -1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
