package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

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

    public abstract boolean hasNext();
    public abstract JCodes next();
    public abstract void setNext(JCodes next);
}
