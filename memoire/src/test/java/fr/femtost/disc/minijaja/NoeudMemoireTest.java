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


}
