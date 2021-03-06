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

    @Test
    public void ecritureFausseTest() {
        assertEquals(-2, t.ecrire(-1, 1, 50));
        assertEquals(-2, t.ecrire(9999999, 1, 50));
    }

    @Test
    public void tasInfos() {
        int a = t.allouer(200);
        int b = t.allouer(300);
        int c = t.allouer(400);
        int d = t.allouer(500);
        assertEquals(0, a);
        assertEquals(200, b);
        assertEquals(600, c);
        assertEquals(-1, d);
        TasInfos infos = t.getInfos();
        TasInfos.BlocInfos b1 = infos.getBlocs().get(0);
        assertEquals(1000, infos.getTaille());
        assertEquals(100, infos.getTailleDisponible());
        assertEquals(100, infos.getTailleMaximaleAllouable());
        assertEquals(4, infos.getNombreBlocs());
        assertEquals(0, b1.getAdresse());
        assertEquals(200, b1.getTaille());
        assertFalse(b1.estDisponible());
    }

    @Test
    public void tasPasNullTest() {
        Memoire m = new Memoire(10);
        assertNotNull(m.getTas());
    }

    @Test
    public void toStringArbreTest() {
        ArbreMemoire arbre = new ArbreMemoire(10);
        arbre.allouerMemoire(5);
        arbre.allouerMemoire(10);
        assertEquals("(0, 10) [(0, 5) [(-), (-)], (5, 5) [(-), (-)]]", arbre.toString());
    }

    @Test
    public void tasInfosStringTest() {
        Tas tas = new Tas(10);
        tas.allouer(5);
        TasInfos infos = tas.getInfos();
        assertEquals("[0->4:A][5->9:-]", infos.toString());
    }

    @Test
    public void ajoutMemoireTest() {
        Tas tas = new Tas(10);
        tas.allouer(5);
        tas.ecrire(0, 0, 1);
        tas.ecrire(0, 2, 2);
        tas.ecrire(0, 4, 3);
        tas.ecrire(0, 5, 4);
        Object[] mem = tas.getMemoire();
        assertEquals(1, mem[0]);
        assertEquals(2, mem[2]);
        assertEquals(3, mem[4]);
        assertNull(mem[5]);
        tas.liberer(0);
        assertNull(mem[0]);
    }
}
