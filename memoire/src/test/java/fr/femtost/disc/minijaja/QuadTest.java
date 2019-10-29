package fr.femtost.disc.minijaja;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class QuadTest {

    Stack<Quad> s;
    Pile p;
    Quad q;
    @Before
    public void Begin() {
        s = new Stack<>();
        p = new Pile(s);
        p.DeclVar("Var", 1, Sorte.INT);
        q = s.peek();
    }

    @Test
    public void verifieQuad() {
        assertSame("Var", q.getID());
        assertSame(1, q.getVAL());
        assertSame(NatureObjet.VAR, q.getOBJ());
        assertSame(Sorte.INT, q.getSORTE());
    }

    @Test
    public void setIDTest() {
        q.setID("nvVar");
        assertSame("nvVar", q.getID());
    }

    @Test
    public void setVALTest() {
        q.setVAL(2);
        assertSame(2, q.getVAL());
    }

    @Test
    public void setOBJTest() {
        q.setOBJ(NatureObjet.CST);
        assertSame(NatureObjet.CST, q.getOBJ());
    }

    @Test
    public void setSORTETest() {
        q.setSORTE(Sorte.BOOL);
        assertSame(Sorte.BOOL, q.getSORTE());
    }

    @Test
    public void toStringTest() {
        assertEquals("(Var, 1, VAR, INT)", q.toString());
    }

}
