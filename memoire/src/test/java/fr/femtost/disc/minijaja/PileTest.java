package fr.femtost.disc.minijaja;

import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class PileTest {

    Stack<Quad> s;
    Pile p;
    @Before
    public void Begin() {
        s = new Stack<>();
        p = new Pile(s);
    }

    @Test
    public void videTest() {
        assertEquals(0, s.size());
    }

    @Test
    public void empilerTest() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        assertEquals(1, s.size());
    }

    @Test
    public void empilerDepilerTest() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        p.Depiler();
        assertEquals(0, s.size());
    }

    @Test
    public void empiler2Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q2);
        assertEquals(2, s.size());
    }

    @Test
    public void empiler2Sans1Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, "sorte");
        assertEquals(1, s.size());
    }

    @Test
    public void empilerDepiler2Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q2);
        p.Depiler();
        p.Depiler();
        assertEquals(0, s.size());
    }

    @Test
    public void echangerTest() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, "sorte");
        p.Empiler(q1);
        Quad q2 = new Quad("2", 1, NatureObjet.VAR, "sorte");
        p.Empiler(q2);
        assertEquals(2, s.size());
        assertEquals(1, s.peek().VAL);
        p.Echanger();
        assertEquals(2, s.size());
        String ID = "1";
        assertEquals(0, s.peek().VAL);
    }

}
