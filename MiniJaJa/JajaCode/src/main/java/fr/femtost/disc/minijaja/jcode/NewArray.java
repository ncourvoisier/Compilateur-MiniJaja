package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.*;

public class NewArray extends JCode {

    private String ident;
    private Sorte type;

    public NewArray(String ident, Sorte type) {
        this.ident = ident;
        this.type = type;
    }

    @Override
    public String rewrite() {
        return "newarray(" + ident + "," + type.name() + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        try {
            m.getPile().declTab(ident, m.getPile().depiler().getVAL(),type);
            return current+1;
        } catch (PileException e) {
            ASTLogger.getInstance().logErrorJJC(e.getMessage());
            return -1;
        }
    }
}
