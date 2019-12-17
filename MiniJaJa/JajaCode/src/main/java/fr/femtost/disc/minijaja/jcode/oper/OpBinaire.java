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
            Object v1 = m.getPile().Depiler().getVAL();
            Object v2 = m.getPile().Depiler().getVAL();
            switch (operandes) {
                case OR:
                    m.getPile().DeclCst(null, (boolean)v1 || (boolean)v2, Sorte.VOID);
                    return current+1;
                case ADD:
                    m.getPile().DeclCst(null, (int)v1 + (int)v2, Sorte.VOID);
                    return current+1;
                case AND:
                    m.getPile().DeclCst(null, (boolean)v1 && (boolean)v2, Sorte.VOID);
                    return current+1;
                case CMP:
                    m.getPile().DeclCst(null, v1.equals(v2), Sorte.VOID);
                    return current+1;
                case DIV:
                    m.getPile().DeclCst(null, (int)v1 / (int)v2, Sorte.VOID);
                    return current+1;
                case MUL:
                    m.getPile().DeclCst(null, (int)v1 * (int)v2, Sorte.VOID);
                    return current+1;
                case SUB:
                    m.getPile().DeclCst(null, (int)v1 - (int)v2, Sorte.VOID);
                    return current+1;
                case SUP:
                    /*System.out.println("v1 : "+v1);
                    System.out.println("v2 : "+v2);

                    int intValue1 = Integer.parseInt((String) v1);
                    int intValue2 = Integer.parseInt((String) v2);
                    m.getPile().DeclCst(null, intValue1 > intValue2, Sorte.VOID);*/
                    //Build quicksort renvoie exception
                    m.getPile().DeclCst(null, (int)v1 > (int)v2, Sorte.VOID);
                    return current+1;
            }
            ASTLogger.getInstance().logWarningJJC("Unexpected operator:" + operandes.name());
            return -1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
