package fr.femtost.disc.minijaja;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PileTest {

    Pile p;
    @Before
    public void Begin() {
        Memoire mem = new Memoire(1000000);
        p = mem.getPile();

    }

    @Test
    public void videTest() {
        assertEquals(0, p.returnTaillePile());
    }

    @Test
    public void empilerTest() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        assertEquals(1, p.returnTaillePile());
    }

    @Test
    public void empilerDepilerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        p.Depiler();
        assertEquals(0, p.returnTaillePile());
    }

    @Test
    public void empiler2Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q2);
        assertEquals(2, p.returnTaillePile());
    }

    @Test
    public void empiler2Sans1Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
    }

    @Test
    public void empilerDepiler2Test() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q2);
        p.Depiler();
        p.Depiler();
        assertEquals(0, p.returnTaillePile());
    }

    @Test public void Depiler() {
        try {
            p.Depiler();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Impossible de dépiller un élément la pile est vide.");
            assertEquals("Impossible de dépiller un élément la pile est vide. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void echangerTest0() {
        try {
            p.Echanger();
        } catch (Exception e) {
            assertEquals("Impossible de dépiller un élément la pile est vide.", e.getMessage());
            assertEquals("Impossible de dépiller un élément la pile est vide. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void echangerTest1() {
        p.DeclVar("Var", 5, Sorte.INT);
        try {
            p.Echanger();
        } catch (PileException e) {
            assertEquals("Impossible d'échanger 2 éléments de la pile.", e.getMessage());
        }
    }

    @Test
    public void echangerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q1);
        Quad q2 = new Quad("2", 1, NatureObjet.VAR, Sorte.INT);
        p.Empiler(q2);
        assertEquals(2, p.returnTaillePile());
        assertEquals(1, p.getStackTop().getVAL());
        p.Echanger();
        assertEquals(2, p.returnTaillePile());
        assertEquals(0, p.getStackTop().getVAL());
    }

    @Test
    public void DeclVarTest1() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.VAR, q.getOBJ());
    }

    @Test
    public void DeclVarTest2() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.DeclVar("hauteur", 25, Sorte.INT);
        p.DeclVar("largeur", 18, Sorte.INT);
        assertEquals(3, p.returnTaillePile());

        Quad q1 = p.getTds().chercheQuad("Var");
        assertEquals(0, q1.getVAL());
        assertSame("Var", q1.getID());
        assertSame(Sorte.INT, q1.getSORTE());
        assertSame(NatureObjet.VAR, q1.getOBJ());

        Quad q2 = p.getTds().chercheQuad("hauteur");
        assertEquals(25, q2.getVAL());
        assertSame("hauteur", q2.getID());
        assertSame(Sorte.INT, q2.getSORTE());
        assertSame(NatureObjet.VAR, q2.getOBJ());

        Quad q3 = p.getTds().chercheQuad("largeur");
        assertEquals(18, q3.getVAL());
        assertSame("largeur", q3.getID());
        assertSame(Sorte.INT, q3.getSORTE());
        assertSame(NatureObjet.VAR, q3.getOBJ());
    }

    @Test
    public void DeclVarTest3() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.DeclVar("Var", 2, Sorte.INT);
        assertEquals(2, p.returnTaillePile());

        Quad q1 = p.getTds().chercheQuad("Var");
        assertEquals(2, q1.getVAL());
        assertSame("Var", q1.getID());
        assertSame(Sorte.INT, q1.getSORTE());
        assertSame(NatureObjet.VAR, q1.getOBJ());
    }

    @Test
    public void IdentValTest1() {
        try {
            p.IdentVal("Var", Sorte.INT, 15);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal.", e.getMessage());
            assertEquals("La pile est vide, impossible d'utiliser la fonction IdentVal. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void IdentValTest2() {
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
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.CST, q.getOBJ());
    }

    @Test
    public void DeclConstTest2() {
        p.DeclCst("Var", null, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertNull(q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.VCST, q.getOBJ());
    }

    @Test
    public void DeclMethTest() {
        p.DeclMeth("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.METH, q.getOBJ());
    }

    @Test
    public void chercherValeurTest() {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.DeclVar("hauteur", 25, Sorte.INT);
        p.DeclVar("largeur", 18, Sorte.INT);
        assertEquals(3, p.returnTaillePile());

        Quad q1 = p.ReturnQuadWithId("Var");
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        Quad q2 = p.ReturnQuadWithId("largeur");
        assertSame("largeur", q2.getID());
        assertSame(18, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }



    @Test
    public void affecterValTest0() {
        assertEquals(0, p.returnTaillePile());
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
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Cst", q1.getID());
        assertSame(null, q1.getVAL());
        assertSame(NatureObjet.VCST, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.AffecterVal("Cst", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Cst", q2.getID());
        assertSame(42, q2.getVAL());
        assertSame(NatureObjet.CST, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest2() throws PileException {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.AffecterVal("Var", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Var", q2.getID());
        assertSame(42, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest3() throws PileException {
        p.DeclVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.AffecterVal("Faux", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Var", q2.getID());
        assertSame(0, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest4() throws PileException {
        p.DeclVar("Var", 0, Sorte.INT);
        p.DeclVar("Var2", 1, Sorte.INT);
        assertEquals(2, p.returnTaillePile());

        p.AffecterVal("Var", 5);

        Quad q2 = p.Depiler();
        assertSame("Var2", q2.getID());
        assertEquals(1, q2.getVAL());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertEquals(5, q1.getVAL());
    }

    @Test
    public void valObjectSorte0() {
        try {
            p.Val("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la valeur de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la valeur de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.Object("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la nature objet de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la nature objet de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.Sorte("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la sorte de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la sorte de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
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
    public void RetirerDecl0() {
        try {
            p.RetirerDecl("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void RetirerDecl1() {
        p.DeclTab("t", 5, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        assertSame("t", p.getStackTop().getID());
        assertSame(0, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());

        try {
            p.RetirerDecl("t");
            assertEquals(0, p.returnTaillePile());
        } catch (PileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RetirerDecl2() {
        p.DeclTab("c", 5, Sorte.INT);
        p.DeclVar("Var", 2, Sorte.INT);
        p.DeclTab("t", 5, Sorte.INT);

        try {
            p.RetirerDecl("t");
            assertEquals(2, p.returnTaillePile());
            p.RetirerDecl("c");
            assertEquals(1, p.returnTaillePile());
        } catch (PileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AffecterType0() {
        try {
            p.AffecterType("Var", Sorte.INT);
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'affecter le type de l'ID : Var.", e.getMessage());
        }
    }

    @Test
    public void AffecterType1() {
        p.DeclVar("Var", 1, Sorte.INT);
        assertSame("Var", p.getStackTop().getID());
        assertSame(1, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
        assertEquals(1, p.returnTaillePile());
        try {
            assertFalse(p.AffecterType("VarExistePas", Sorte.INT));
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'affecter le type de l'ID : Var.", e.getMessage());
        }
    }

    @Test
    public void AffecterType2() {
        p.DeclVar("Var", 1, Sorte.INT);
        assertSame("Var", p.getStackTop().getID());
        assertSame(1, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
        assertEquals(1, p.returnTaillePile());
        try {
            assertTrue(p.AffecterType("Var", Sorte.BOOL));
            assertEquals(1, p.returnTaillePile());
            assertSame("Var", p.getStackTop().getID());
            assertSame(1, p.getStackTop().getVAL());
            assertSame(Sorte.BOOL, p.getStackTop().getSORTE());
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'affecter le type de l'ID : Var.", e.getMessage());
        }
    }

    @Test
    public void DeclTab() {
        p.DeclTab("t", 5, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        assertSame("t", p.getStackTop().getID());
        assertSame(0, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
    }

    @Test
    public void AjouterRef() {
        assertEquals(-2, p.AjouterRef(true));
        assertEquals(0, p.AjouterRef(2));
    }

    @Test
    public void Parametre0() {
        p.DeclVar("Var", 2, Sorte.INT);
        assertNull(p.Parametre("Var"));
    }

    @Test
    public void GetTas() {
        assertNotNull(p.getTas());
    }

    @Test
    public void testToString0() {
        assertEquals("", p.toString());
    }

    @Test
    public void testToString1() {
        p.DeclVar("Var1", 2, Sorte.INT);
        p.DeclVar("Var2", 5, Sorte.INT);
        p.DeclVar("Var3", 7, Sorte.INT);
        assertEquals("<Var3, 7, VAR, INT>.<Var2, 5, VAR, INT>.<Var1, 2, VAR, INT>", p.toString());
    }
}
