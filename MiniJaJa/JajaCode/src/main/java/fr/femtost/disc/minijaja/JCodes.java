package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.ArrayList;
import java.util.List;

public abstract class JCodes extends JajaNode {

    public static JCodes concatenate(JCodes left, JCodes right) {

        if(left instanceof JNil)
            return right;

        if(right instanceof JNil)
            return left;

        JCodes current = left;
        while(current.hasNext()) {
            current = current.next();
        }
        current.setNext(right);

        return left;
    }

    public static JCodes concatLeft(JCode left, JCodes right) {
        return new JChain(left, right);
    }

    public static JCodes concatRight(JCodes left, JCode right) {
        return concatenate(left, new JChain(right, new JNil()));
    }

    public static List<JCode> asArray(JCodes source) {
        ArrayList<JCode> result = new ArrayList<>();

        JCodes current = source;
        while(current.hasNext()) {
            result.add(current.getContent());
            current = current.next();
        }
        result.add(current.getContent());

        return result;
    }

    public abstract boolean hasNext();
    public abstract JCodes next();
    public abstract void setNext(JCodes next);
    public abstract JCode getContent();

    public String rewriteWithLines() {
        List<JCode> codes = JCodes.asArray(this);
        StringBuilder result = new StringBuilder();

        for(int i=0; i<codes.size(); ++i) {
            result.append(i+1).append("\t").append(codes.get(i).rewrite()).append("\n");
        }

        return result.toString();
    }

    public void interpreterFull() {
        List<JCode> codes = JCodes.asArray(this);
        int current = 1;
        Memoire m = new Memoire(4096);
        while (current > 0) {
            ASTLogger.getInstance().logJJC("ins " + current + "\n");
            current = codes.get(current-1).interpreter(m, current);
        }
    }
}
