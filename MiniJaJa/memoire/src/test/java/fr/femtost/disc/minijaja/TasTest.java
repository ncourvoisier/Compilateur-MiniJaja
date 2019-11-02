package fr.femtost.disc.minijaja;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TasTest {

    private Tas t;

    @Before
    public void Begin() {
        t = new Tas(1000);
    }

    @Test
    public void tropGrosseAllocationTest() {
        int a = t.allouer(1001);
        assertEquals(-1, a);
    }

    @Test
    public void bonneAllocationTest() {
        int a = t.allouer(1000);
        assertEquals(0, a);
    }

    @Test
    public void multiAllocationsTest() {
        int a = t.allouer(200);
        int b = t.allouer(300);
        int c = t.allouer(400);
        int d = t.allouer(500);
        assertEquals(0, a);
        assertEquals(200, b);
        assertEquals(600, c);
        assertEquals(-1, d);
    }

    @Test
    public void ecritureLectureOKTest() {
        int a = t.allouer(2);
        t.ecrire(a, 0, 50);
        t.ecrire(a, 1, 100);
        assertEquals(50, t.lire(a, 0));
        assertEquals(100, t.lire(a, 1));
    }

    @Test
    public void ecritureLectureKOTest() {
        int a = t.allouer(2);
        assertEquals(-2, t.ecrire(56, 0, 9));
        assertNull(t.lire(56, 0));
        assertEquals(-1, t.ecrire(a, 2, 9));
        assertEquals(-1, t.ecrire(a, -1, 9));
    }

    @Test
    public void ecritureLectureApresLiberationTest() {
        int a = t.allouer(2);
        assertEquals(0, t.ecrire(a, 0, 36));
        t.liberer(a);
        assertNull(t.lire(a, 0));
        assertEquals(-3, t.ecrire(a, 0, 39));
        a = t.allouer(2);
        assertEquals(0, t.ecrire(a, 0, 55));
    }

}
