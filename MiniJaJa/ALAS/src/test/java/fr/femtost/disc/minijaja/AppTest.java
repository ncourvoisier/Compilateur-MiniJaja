package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.ASTClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test_astOK1() {
        String file = "./minijaja/fact.mjj";
        try {
            ASTClass cl = new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (ParseException e) {
            fail("Erreur de parsing");
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test
    public void test_astOK2() {
        String file = "./minijaja/quick_sort.mjj";
        try {
            ASTClass cl = new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (ParseException e) {
            fail("Erreur de parsing");
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test
    public void test_astOK3() {
        String file = "./minijaja/test.mjj";
        try {
            ASTClass cl = new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (ParseException e) {
            fail("Erreur de parsing");
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_NoMain() throws ParseException {
        String file = "./minijaja/echec1.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_SemicolonMethod() throws ParseException {
        String file = "./minijaja/echec2.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_SemicolonInstr() throws ParseException {
        String file = "./minijaja/echec3.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_WriteConcatenate() throws ParseException {
        String file = "./minijaja/echec4.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_MainParenthesis() throws ParseException {
        String file = "./minijaja/echec5.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test
    public void test_rewrite1() {
        String file = "./minijaja/test.mjj";
        try {
            ASTClass cl = new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
            assertFalse(cl.rewrite().isEmpty());
        } catch (ParseException e) {
            fail("Erreur de parsing");
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test
    public void test_rewrite2() {
        String file = "./minijaja/synonymie.mjj";
        try {
            ASTClass cl = new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
            assertFalse(cl.rewrite().isEmpty());
        } catch (ParseException e) {
            fail("Erreur de parsing");
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

}
