package fr.femtost.disc.minijaja.jcodes;

import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.JCodes;

import java.util.List;

public class JChain extends JCodes {

    private JCode instruction;
    private JCodes next;

    private JCodes last;

    public JChain(JCode instruction, JCodes next) {
        this.instruction = instruction;
        this.next = next;
        if(next instanceof JNil)
            this.last = this;
        else
            this.last = ((JChain)next).getLast();
    }

    @Override
    public boolean hasNext() {
        return next instanceof JChain;
    }

    @Override
    public JCodes next() {
        return next;
    }

    public JCodes getLast() {
        return last;
    }

    public void setLast(JCodes last) {
        this.last = last;
    }

    @Override
    public void setNext(JCodes next) {
        this.next = next;
    }

    @Override
    public JCode getContent() {
        return instruction;
    }

    @Override
    public String rewrite() {
        return instruction.rewrite() + "\n" + next.rewrite();
    }
}
