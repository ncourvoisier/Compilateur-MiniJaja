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
        assertEquals(0, p.returnTaillePile());
    }

    @Test
    public void empilerTest() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        assertEquals(1, s.size());
        assertEquals(1, p.returnTaillePile());
    }

    @Test
    public void empilerDepilerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        p.Depiler();
        assertEquals(0, s.size());
    }

    @Test
    public void empiler2Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q2);
        assertEquals(2, s.size());
    }

    @Test
    public void empiler2Sans1Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        assertEquals(1, s.size());
    }

    @Test
    public void empilerDepiler2Test() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q2);
        p.Depiler();
        p.Depiler();
        assertEquals(0, s.size());
    }

    @Test public void Depiler() throws PileException {
        try {
            p.Depiler();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Impossible de dépiller un élément la pile est vide.");
            assertEquals("Impossible de dépiller un élément la pile est vide. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void echangerTest0() throws PileException {
        try {
            p.Echanger();
        } catch (Exception e) {
            assertEquals("Impossible de dépiller un élément la pile est vide.", e.getMessage());
            assertEquals("Impossible de dépiller un élément la pile est vide. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void echangerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 1, NatureObjet.VAR, Sorte.INT);
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
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame(Sorte.INT, q.SORTE);
        assertSame(NatureObjet.VAR, q.OBJ);
    }

    @Test
    public void DeclVarTest2() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        p.DeclVar("hauteur", 25, Sorte.INT);
        p.DeclVar("largeur", 18, Sorte.INT);
        assertEquals(3, s.size());

        Quad q1 = p.tds.tableSymbole.get("Var");
        assertEquals(0, q1.VAL);
        assertSame("Var", q1.ID);
        assertSame(Sorte.INT, q1.SORTE);
        assertSame(NatureObjet.VAR, q1.OBJ);

        Quad q2 = p.tds.tableSymbole.get("hauteur");
        assertEquals(25, q2.VAL);
        assertSame("hauteur", q2.ID);
        assertSame(Sorte.INT, q2.SORTE);
        assertSame(NatureObjet.VAR, q2.OBJ);

        Quad q3 = p.tds.tableSymbole.get("largeur");
        assertEquals(18, q3.VAL);
        assertSame("largeur", q3.ID);
        assertSame(Sorte.INT, q3.SORTE);
        assertSame(NatureObjet.VAR, q3.OBJ);
    }

    @Test
    public void DeclVarTest3() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        p.DeclVar("Var", 2, Sorte.INT);
        assertEquals(2, s.size());

        Quad q1 = p.tds.tableSymbole.get("Var");
        assertEquals(2, q1.VAL);
        assertSame("Var", q1.ID);
        assertSame(Sorte.INT, q1.SORTE);
        assertSame(NatureObjet.VAR, q1.OBJ);
    }

    @Test
    public void IdentValTest1() throws PileException {
        try {
            p.IdentVal("Var", Sorte.INT, 15);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal.", e.getMessage());
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void IdentValTest2() throws PileException {
        p.DeclVar("V", 15, Sorte.INT);
        try {
            p.IdentVal("Var", Sorte.INT, 15);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal.", e.getMessage());
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void IdentValTest3() throws PileException {
        p.DeclVar("V", 15, Sorte.INT);
        p.IdentVal("V", Sorte.INT, 0);
    }

    @Test
    public void IdentValTest4() throws PileException {
        p.DeclVar("V1", 15, Sorte.INT);
        p.DeclVar("V2", 15, Sorte.INT);
        p.IdentVal("V1", Sorte.INT, 1);
    }

    @Test
    public void DeclConstTest1() {
        p.DeclCst("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame(Sorte.INT, q.SORTE);
        assertSame(NatureObjet.CST, q.OBJ);
    }

    @Test
    public void DeclConstTest2() {
        p.DeclCst("Var", null, Sorte.INT);
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(null, q.VAL);
        assertSame("Var", q.ID);
        assertSame(Sorte.INT, q.SORTE);
        assertSame(NatureObjet.VCST, q.OBJ);
    }

    @Test
    public void DeclMethTest() {
        p.DeclMeth("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        Quad q = p.tds.tableSymbole.get("Var");
        assertEquals(0, q.VAL);
        assertSame("Var", q.ID);
        assertSame(Sorte.INT, q.SORTE);
        assertSame(NatureObjet.METH, q.OBJ);
    }

    @Test
    public void chercherValeurTest() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());
        p.DeclVar("hauteur", 25, Sorte.INT);
        p.DeclVar("largeur", 18, Sorte.INT);
        assertEquals(3, s.size());

        Quad q1 = p.ReturnQuadWithId("Var");
        assertSame("Var", q1.ID);
        assertSame(0, q1.VAL);
        assertSame(NatureObjet.VAR, q1.OBJ);
        assertSame(Sorte.INT, q1.SORTE);

        Quad q2 = p.ReturnQuadWithId("largeur");
        assertSame("largeur", q2.ID);
        assertSame(18, q2.VAL);
        assertSame(NatureObjet.VAR, q2.OBJ);
        assertSame(Sorte.INT, q2.SORTE);
    }



    @Test
    public void affecterValTest0() throws  PileException {
        assertEquals(0, s.size());
        try {
            p.AffecterVal("Cst", 42);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible de dépiler un élément.", e.getMessage());
            assertEquals("La pile est vide, impossible de dépiler un élément. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void affecterValTest1() throws PileException {
        p.DeclCst("Cst", null, Sorte.INT);
        assertEquals(1, s.size());

        Quad q1 = p.pile.peek();
        assertSame("Cst", q1.ID);
        assertSame(null, q1.VAL);
        assertSame(NatureObjet.VCST, q1.OBJ);
        assertSame(Sorte.INT, q1.SORTE);

        p.AffecterVal("Cst", 42);
        assertEquals(1, s.size());

        Quad q2 = p.pile.peek();
        assertSame("Cst", q2.ID);
        assertSame(42, q2.VAL);
        assertSame(NatureObjet.CST, q2.OBJ);
        assertSame(Sorte.INT, q2.SORTE);
    }

    @Test
    public void affecterValTest2() throws PileException {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());

        Quad q1 = p.pile.peek();
        assertSame("Var", q1.ID);
        assertSame(0, q1.VAL);
        assertSame(NatureObjet.VAR, q1.OBJ);
        assertSame(Sorte.INT, q1.SORTE);

        p.AffecterVal("Var", 42);
        assertEquals(1, s.size());

        Quad q2 = p.pile.peek();
        assertSame("Var", q2.ID);
        assertSame(42, q2.VAL);
        assertSame(NatureObjet.VAR, q2.OBJ);
        assertSame(Sorte.INT, q2.SORTE);
    }

    @Test
    public void affecterValTest3() throws PileException {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, s.size());

        Quad q1 = p.pile.peek();
        assertSame("Var", q1.ID);
        assertSame(0, q1.VAL);
        assertSame(NatureObjet.VAR, q1.OBJ);
        assertSame(Sorte.INT, q1.SORTE);

        p.AffecterVal("Faux", 42);
        assertEquals(1, s.size());

        Quad q2 = p.pile.peek();
        assertSame("Var", q2.ID);
        assertSame(0, q2.VAL);
        assertSame(NatureObjet.VAR, q2.OBJ);
        assertSame(Sorte.INT, q2.SORTE);
    }

    @Test
    public void valObjectSorte0() throws PileException {
        try {
            p.Val("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtrenir la valeur de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtrenir la valeur de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.Object("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtrenir la nature objet de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtrenir la nature objet de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.Sorte("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtrenir la sorte de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtrenir la sorte de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void valObjectSorte1() throws PileException {
        p.DeclVar("Var1", 1, Sorte.INT);
        p.DeclVar("Var2", 2, Sorte.INT);
        p.DeclVar("Var3", 3, Sorte.INT);

        assertSame(1, p.Val("Var1"));
        assertSame(2, p.Val("Var2"));
        assertSame(3, p.Val("Var3"));
    }

    @Test
    public void valObjectSorte2() throws PileException {
        p.DeclVar("Var1", 1, Sorte.INT);
        p.DeclVar("Var2", 2, Sorte.INT);
        p.DeclVar("Var3", 3, Sorte.INT);

        assertSame(NatureObjet.VAR, p.Object("Var1"));
        assertSame(NatureObjet.VAR, p.Object("Var2"));
        assertSame(NatureObjet.VAR, p.Object("Var3"));
    }

    @Test
    public void valObjectSorte3() throws PileException {
        p.DeclVar("Var1", 1, Sorte.INT);
        p.DeclVar("Var2", 2, Sorte.INT);
        p.DeclVar("Var3", 3, Sorte.INT);

        assertSame(Sorte.INT, p.Sorte("Var1"));
        assertSame(Sorte.INT, p.Sorte("Var2"));
        assertSame(Sorte.INT, p.Sorte("Var3"));
    }

    @Test
    public void valObjectSorte4() throws PileException {
        p.DeclVar("Var1", 1, Sorte.INT);
        p.DeclVar("Var2", 2, Sorte.INT);
        p.DeclVar("Var3", 3, Sorte.INT);

        assertSame("NOK_", p.Val("Var"));
        assertSame(null, p.Object("Var"));
        assertSame(null, p.Sorte("Var"));
    }

    @Test
    public void RetirerDecl() throws PileException {
        try {
            p.RetirerDecl("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

}
