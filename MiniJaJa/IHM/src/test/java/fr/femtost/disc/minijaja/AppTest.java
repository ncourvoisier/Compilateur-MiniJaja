package fr.femtost.disc.minijaja;

import static org.junit.Assert.assertTrue;

import fr.femtost.disc.minijaja.ast.ASTClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void TestTotalAST() {
        String s = " class factorielle{ int x=7; void pause(){ int i;}; int fact(int x){ int retour; if (x==0) {";
        s += "retour=1; }else{ retour=x*fact(x-1); }; return retour; }; main { int res=fact(x); pause(); } }";
        try {
            ASTClass astClass = new SyntaxChecker(new BufferedReader(new StringReader(s))).S();
            System.out.println(astClass.rewrite());
            Memoire m = new Memoire(100000);
            astClass.interpreter(m);
            astClass.compiler(1);
        } catch (Throwable e) {
            System.out.println("Syntax check failed: " + e.getMessage());
        }


    }
}