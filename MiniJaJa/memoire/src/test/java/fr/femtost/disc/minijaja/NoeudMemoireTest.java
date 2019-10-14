package fr.femtost.disc.minijaja;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoeudMemoireTest {


    @Test
    public void noeudMemoireDisponibleAdresse() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        assertEquals(A1, 0);
        int A2 = r1.allouerMemoire(10);
        assertEquals(A2, 10);
    }

    @Test
    public void noeudMemoireDisponibleAdresseDepordementMemoire() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A3 = r1.allouerMemoire(110);
        assertEquals(A3, -1);
    }

    @Test
    public void noeudMemoireDisponibleAdresseVerifiePropagation() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        assertEquals(A1, 0);
        int A2 = r1.allouerMemoire(10);
        assertEquals(A2, 10);
        assertEquals(AM1.getRacine().taille, 80);
    }

    @Test
    public void noeudMemoireDisponibleAdresseAjouteTaille0() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(0);
        assertEquals(A1, 0);
    }

    @Test
    public void profondeurArbreMemoire1() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        assertEquals(1, AM1.getProfondeurArbre());
    }

    @Test
    public void profondeurArbreMemoire2() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        assertEquals(A1, 0);
        int A2 = r1.allouerMemoire(10);
        assertEquals(A2, 10);
        assertEquals(3, AM1.getProfondeurArbre());
    }

    @Test
    public void getNoeud() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        int A2 = r1.allouerMemoire(10);
        assertTrue(AM1.getNoeud(A1).adresse == A1);
        assertTrue(AM1.getNoeud(A2).adresse == A2);
    }


    @Test
    public void suppressionNoeud() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        int A2 = r1.allouerMemoire(10);
        AM1.libererMemoire(A2);
        NoeudMemoire noeud2 = AM1.getNoeud(A2);
        assertTrue(noeud2.disponible);
    }

    @Test
    public void reallocationNoeud() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        int A2 = r1.allouerMemoire(10);
        AM1.libererMemoire(A2);
        NoeudMemoire noeud2 = AM1.getNoeud(A2);
        assertTrue(noeud2.disponible);
        int A3 = r1.allouerMemoire(10);
        assertEquals(10, A3);
        assertFalse(AM1.getNoeud(A3).disponible);
    }

    @Test
    public void multiAllocNoeud1() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(5);
        int A2 = r1.allouerMemoire(10);
        int A3 = r1.allouerMemoire(20);
        assertFalse(AM1.getNoeud(A1).disponible);
        assertFalse(AM1.getNoeud(A2).disponible);
        assertFalse(AM1.getNoeud(A3).disponible);

        assertEquals(0, A1);
        assertEquals(5, A2);
        assertEquals(15, A3);

        assertEquals(65, AM1.getRacine().taille);

        AM1.libererMemoire(A2);
        NoeudMemoire noeud2 = AM1.getNoeud(A2);
        assertTrue(noeud2.disponible);

        assertEquals(75, AM1.getRacine().taille);
    }

    @Test
    public void multiAllocNoeud3() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(10);
        int A2 = r1.allouerMemoire(89);
        assertFalse(AM1.getNoeud(A1).disponible);
        assertFalse(AM1.getNoeud(A2).disponible);

        assertEquals(0, A1);
        assertEquals(10, A2);
    }

    @Test
    public void multiAllocNoeud2() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int A1 = r1.allouerMemoire(5);
        int A2 = r1.allouerMemoire(10);
        int A3 = r1.allouerMemoire(20);
        assertFalse(AM1.getNoeud(A1).disponible);
        assertFalse(AM1.getNoeud(A2).disponible);
        assertFalse(AM1.getNoeud(A3).disponible);

        assertEquals(0, A1);
        assertEquals(5, A2);
        assertEquals(15, A3);

        assertEquals(65, AM1.getRacine().taille);

        AM1.libererMemoire(A2);
        NoeudMemoire noeud2 = AM1.getNoeud(A2);
        assertTrue(noeud2.disponible);

        int A4 = r1.allouerMemoire(5);
        int A5 = r1.allouerMemoire(5);
        assertFalse(AM1.getNoeud(A4).disponible);

        //System.out.println("aaaaaasezqzqzz" + AM1.getNoeud(A5));
        //System.out.println("adresse de A5 " + A5);

        assertFalse(AM1.getNoeud(A5).disponible);

        assertEquals(0, A1);
        assertEquals(15, A3);
        assertEquals(5, A4);
        assertEquals(10, A5);

        assertEquals(65, AM1.getRacine().taille);
    }

    @Test
    public void debugTest() {
        ArbreMemoire AM1 = new ArbreMemoire(100);
        NoeudMemoire r1 = AM1.getRacine();
        int a1 = r1.allouerMemoire(10);
        int a2 = r1.allouerMemoire(46);
        System.out.println("add 1: " + a1 + ", add 2: " + a2);
        NoeudMemoire n1 = AM1.getNoeud(a1);
        NoeudMemoire n2 = AM1.getNoeud(a2);
        if (r1.droit.gauche == null) {  // LES 2 FILS PAS NULL
            System.out.println("fils droit/gauche null");
        }
        else {
            System.out.println("fils droit/gauche addr: " + r1.droit.gauche.adresse + " (disp:" + r1.droit.gauche.disponible + ")");  // ADRESSES DES 2 FILS BONNES
        }
        if (r1.droit.droit == null) {
            System.out.println("fils droit/droit null");
        }
        else {
            System.out.println("fils droit/droit addr: " + r1.droit.droit.adresse + " (disp:" + r1.droit.droit.disponible + ")");
        }
        if (n1 == null) { System.out.println("n1 null"); }
        if (n2 == null) { System.out.println("n2 null"); }
    }
}
