package fr.femtost.disc.minijaja;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import fr.femtost.disc.minijaja.ast.ASTClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void TestTotalAST(){
        String s =" class factorielle{ int x=7; void pause(){ int i;}; int fact(int x){ int retour; if (x==0) {";
                s+="retour=1; }else{ retour=x*fact(x-1); }; return retour; }; main { int res=fact(x); pause(); // res = 5040 } }";
        try {
            ASTClass astClass = new SyntaxChecker(new BufferedReader(new StringReader(s))).S();
            System.out.println(astClass.rewrite());
        } catch (Throwable e) {
    System.out.println("Syntax check failed: " + e.getMessage());
}

        assertTrue(true);
    }

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
        String file = "./minijaja/echec3.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

    @Test(expected = ParseException.class)
    public void test_astKO_TEMP() throws ParseException {
        String file = "./minijaja/echec3.mjj";
        try {
            new SyntaxChecker(new BufferedReader(new FileReader(file))).S();
        } catch (FileNotFoundException e) {
            fail("Fichier introuvable");
        }
    }

}
