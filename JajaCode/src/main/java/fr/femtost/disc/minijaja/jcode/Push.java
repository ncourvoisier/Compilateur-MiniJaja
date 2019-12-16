package fr.femtost.disc.minijaja.jcode;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.Memoire;

public class Push extends JCode {

    private Object val;

    public Push(Object val) {
        this.val = val;
    }

    @Override
    public String rewrite() {
        return "push(" + val.toString() + ")";
    }

    @Override
    public int interpreter(Memoire m, int current) {
        m.getPile().DeclCst(null, val, null);
        return current+1;
    }
}
