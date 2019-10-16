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

    @Test
    public void DeclVarTest1() {
        p.DeclVar("Var", 0, "int");
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame("int", q.SORTE);
        assertSame(NatureObjet.VAR, q.OBJ);
    }

    @Test
    public void DeclVarTest2() {
        p.DeclVar("Var", 0, "int");
        assertEquals(1, s.size());
        p.DeclVar("hauteur", 25, "int");
        p.DeclVar("largeur", 18, "int");
        assertEquals(3, s.size());

        Quad q1 = p.tds.tableSymbole.get("Var");
        assertEquals(0, q1.VAL);
        assertSame("Var", q1.ID);
        assertSame("int", q1.SORTE);
        assertSame(NatureObjet.VAR, q1.OBJ);

        Quad q2 = p.tds.tableSymbole.get("hauteur");
        assertEquals(25, q2.VAL);
        assertSame("hauteur", q2.ID);
        assertSame("int", q2.SORTE);
        assertSame(NatureObjet.VAR, q2.OBJ);

        Quad q3 = p.tds.tableSymbole.get("largeur");
        assertEquals(18, q3.VAL);
        assertSame("largeur", q3.ID);
        assertSame("int", q3.SORTE);
        assertSame(NatureObjet.VAR, q3.OBJ);
    }

    @Test
    public void DeclConstTest() {
        p.DeclCst("Var", 0, "int");
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame("int", q.SORTE);
        assertSame(NatureObjet.VCST, q.OBJ);
    }

    @Test
    public void DeclMethTest() {
        p.DeclMeth("Var", 0, "int");
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame("int", q.SORTE);
        assertSame(NatureObjet.METH, q.OBJ);
    }
}
