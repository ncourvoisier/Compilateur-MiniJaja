package fr.femtost.disc.minijaja;


import org.junit.Assert;
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
        p.empiler(q1);
        assertEquals(1, p.returnTaillePile());
    }

    @Test
    public void empilerDepilerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q1);
        p.depiler();
        assertEquals(0, p.returnTaillePile());
    }

    @Test
    public void empiler2Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q2);
        assertEquals(2, p.returnTaillePile());
    }

    @Test
    public void empiler2Sans1Test() {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
    }

    @Test
    public void empilerDepiler2Test() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q1);
        Quad q2 = new Quad("2", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q2);
        p.depiler();
        p.depiler();
        assertEquals(0, p.returnTaillePile());
    }

    @Test(expected = PileException.class)
    public void Depiler() throws PileException {
        p.depiler();
    }

    @Test(expected = PileException.class)
    public void echangerTest0() throws PileException{
        p.echanger();
    }

    @Test(expected = PileException.class)
    public void echangerTest1() throws PileException {
        p.declVar("Var", 5, Sorte.INT);
        p.echanger();
    }

    @Test
    public void echangerTest() throws PileException {
        Quad q1 = new Quad("1", 0, NatureObjet.VAR, Sorte.INT);
        p.empiler(q1);
        Quad q2 = new Quad("2", 1, NatureObjet.VAR, Sorte.INT);
        p.empiler(q2);
        assertEquals(2, p.returnTaillePile());
        assertEquals(1, p.getStackTop().getVAL());
        p.echanger();
        assertEquals(2, p.returnTaillePile());
        assertEquals(0, p.getStackTop().getVAL());
    }

    @Test
    public void DeclVarTest1() {
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.VAR, q.getOBJ());
    }

    @Test
    public void DeclVarTest2() {
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.declVar("hauteur", 25, Sorte.INT);
        p.declVar("largeur", 18, Sorte.INT);
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
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.declVar("Var", 2, Sorte.INT);
        assertEquals(2, p.returnTaillePile());

        Quad q1 = p.getTds().chercheQuad("Var");
        assertEquals(2, q1.getVAL());
        assertSame("Var", q1.getID());
        assertSame(Sorte.INT, q1.getSORTE());
        assertSame(NatureObjet.VAR, q1.getOBJ());
    }

    @Test (expected = PileException.class)
    public void IdentValTest1() throws PileException {
        p.identVal("Var", Sorte.INT, 15);
    }

    @Test
    public void IdentValTest2() {
        p.declVar("V", 15, Sorte.INT);
        try {
            p.identVal("Var", Sorte.INT, 15);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible d'utiliser la fonction identVal.", e.getMessage());
            assertEquals("La pile est vide, impossible d'utiliser la fonction identVal. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void IdentValTest3() throws PileException {
        p.declVar("V", 15, Sorte.INT);
        p.identVal("V", Sorte.INT, 0);
    }

    @Test
    public void IdentValTest4() throws PileException {
        p.declVar("V1", 15, Sorte.INT);
        p.declVar("V2", 15, Sorte.INT);
        p.identVal("V1", Sorte.INT, 1);
    }

    @Test
    public void DeclConstTest1() {
        p.declCst("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.CST, q.getOBJ());
    }

    @Test
    public void DeclConstTest2() {
        p.declCst("Var", null, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertNull(q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.VCST, q.getOBJ());
    }

    @Test
    public void DeclMethTest() {
        p.declMeth("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        Quad q = p.getTds().chercheQuad("Var");
        assertEquals(0, q.getVAL());
        assertSame("Var", q.getID());
        assertSame(Sorte.INT, q.getSORTE());
        assertSame(NatureObjet.METH, q.getOBJ());
    }

    @Test
    public void chercherValeurTest() {
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        p.declVar("hauteur", 25, Sorte.INT);
        p.declVar("largeur", 18, Sorte.INT);
        assertEquals(3, p.returnTaillePile());

        Quad q1 = p.returnQuadWithId("Var");
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        Quad q2 = p.returnQuadWithId("largeur");
        assertSame("largeur", q2.getID());
        assertSame(18, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }



    @Test
    public void affecterValTest0() {
        assertEquals(0, p.returnTaillePile());
        try {
            p.affecterVal("Cst", 42);
        } catch (Exception e) {
            assertEquals("La pile est vide, impossible de dépiler un élément.", e.getMessage());
            assertEquals("La pile est vide, impossible de dépiler un élément. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void affecterValTest1() throws PileException {
        p.declCst("Cst", null, Sorte.INT);
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Cst", q1.getID());
        assertSame(null, q1.getVAL());
        assertSame(NatureObjet.VCST, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.affecterVal("Cst", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Cst", q2.getID());
        assertSame(42, q2.getVAL());
        assertSame(NatureObjet.CST, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest2() throws PileException {
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.affecterVal("Var", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Var", q2.getID());
        assertSame(42, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest3() throws PileException {
        p.declVar("Var", 0, Sorte.INT);
        assertEquals(1, p.returnTaillePile());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertSame(0, q1.getVAL());
        assertSame(NatureObjet.VAR, q1.getOBJ());
        assertSame(Sorte.INT, q1.getSORTE());

        p.affecterVal("Faux", 42);
        assertEquals(1, p.returnTaillePile());

        Quad q2 = p.getStackTop();
        assertSame("Var", q2.getID());
        assertSame(0, q2.getVAL());
        assertSame(NatureObjet.VAR, q2.getOBJ());
        assertSame(Sorte.INT, q2.getSORTE());
    }

    @Test
    public void affecterValTest4() throws PileException {
        p.declVar("Var", 0, Sorte.INT);
        p.declVar("Var2", 1, Sorte.INT);
        assertEquals(2, p.returnTaillePile());

        p.affecterVal("Var", 5);

        Quad q2 = p.depiler();
        assertSame("Var2", q2.getID());
        assertEquals(1, q2.getVAL());

        Quad q1 = p.getStackTop();
        assertSame("Var", q1.getID());
        assertEquals(5, q1.getVAL());
    }

    @Test
    public void valObjectSorte0() {
        try {
            p.val("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la valeur de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la valeur de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.object("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la nature objet de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la nature objet de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
        try {
            p.sorte("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'obtenir la sorte de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible d'obtenir la sorte de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void valObjectSorte1() throws PileException {
        p.declVar("Var1", 1, Sorte.INT);
        p.declVar("Var2", 2, Sorte.INT);
        p.declVar("Var3", 3, Sorte.INT);

        assertSame(1, p.val("Var1"));
        assertSame(2, p.val("Var2"));
        assertSame(3, p.val("Var3"));
    }

    @Test
    public void valObjectSorte2() throws PileException {
        p.declVar("Var1", 1, Sorte.INT);
        p.declVar("Var2", 2, Sorte.INT);
        p.declVar("Var3", 3, Sorte.INT);

        assertSame(NatureObjet.VAR, p.object("Var1"));
        assertSame(NatureObjet.VAR, p.object("Var2"));
        assertSame(NatureObjet.VAR, p.object("Var3"));
    }

    @Test
    public void valObjectSorte3() throws PileException {
        p.declVar("Var1", 1, Sorte.INT);
        p.declVar("Var2", 2, Sorte.INT);
        p.declVar("Var3", 3, Sorte.INT);

        assertSame(Sorte.INT, p.sorte("Var1"));
        assertSame(Sorte.INT, p.sorte("Var2"));
        assertSame(Sorte.INT, p.sorte("Var3"));
    }

    @Test
    public void valObjectSorte4() throws PileException {
        p.declVar("Var1", 1, Sorte.INT);
        p.declVar("Var2", 2, Sorte.INT);
        p.declVar("Var3", 3, Sorte.INT);

        assertSame("NOK_", p.val("Var"));
        assertSame(null, p.object("Var"));
        assertSame(null, p.sorte("Var"));
    }

    @Test
    public void RetirerDecl0() {
        try {
            p.retirerDecl("Var");
        } catch (Exception e) {
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var.", e.getMessage());
            assertEquals("La pile est vide impossible de retirer la déclaration de l'élément Var. -> fr.femtost.disc.minijaja.PileException", e.toString());
        }
    }

    @Test
    public void RetirerDecl1() {
        p.declTab("t", 5, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        assertSame("t", p.getStackTop().getID());
        assertSame(0, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());

        try {
            p.retirerDecl("t");
            assertEquals(0, p.returnTaillePile());
        } catch (PileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RetirerDecl2() {
        p.declTab("c", 5, Sorte.INT);
        p.declVar("Var", 2, Sorte.INT);
        p.declTab("t", 5, Sorte.INT);

        try {
            p.retirerDecl("t");
            assertEquals(2, p.returnTaillePile());
            p.retirerDecl("c");
            assertEquals(1, p.returnTaillePile());
        } catch (PileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void AffecterType0() {
        try {
            p.affecterType("Var", Sorte.INT);
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'affecter le type de l'ID : Var.", e.getMessage());
        }
    }

    @Test
    public void AffecterType1() {
        p.declVar("Var", 1, Sorte.INT);
        assertSame("Var", p.getStackTop().getID());
        assertSame(1, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
        assertEquals(1, p.returnTaillePile());
        try {
            assertFalse(p.affecterType("VarExistePas", Sorte.INT));
        } catch (Exception e) {
            assertEquals("La pile est vide impossible d'affecter le type de l'ID : Var.", e.getMessage());
        }
    }

    @Test
    public void AffecterType2() {
        p.declVar("Var", 1, Sorte.INT);
        assertSame("Var", p.getStackTop().getID());
        assertSame(1, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
        assertEquals(1, p.returnTaillePile());
        try {
            assertTrue(p.affecterType("Var", Sorte.BOOL));
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
        p.declTab("t", 5, Sorte.INT);
        assertEquals(1, p.returnTaillePile());
        assertSame("t", p.getStackTop().getID());
        assertSame(0, p.getStackTop().getVAL());
        assertSame(Sorte.INT, p.getStackTop().getSORTE());
    }

    @Test
    public void AjouterRef() {
        assertEquals(-2, p.ajouterRef(true));
        assertEquals(0, p.ajouterRef(2));
    }

    @Test
    public void Parametre0() {
        p.declVar("Var", 2, Sorte.INT);
        assertNull(p.parametre("Var"));
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
        p.declVar("Var1", 2, Sorte.INT);
        p.declVar("Var2", 5, Sorte.INT);
        p.declVar("Var3", 7, Sorte.INT);
        assertEquals("<Var3, 7, VAR, INT>.<Var2, 5, VAR, INT>.<Var1, 2, VAR, INT>", p.toString());
    }

   /*@Test
    public void affecterTabTest() {
        int addr = p.getTas().allouer(10);
        p.declTab("Tab", 10, Sorte.INT);
        try {
            p.affecterVal("Tab", addr);
            assertEquals(addr, p.val("Tab"));
        }catch (PileException e) {
            Assert.fail();
        }
    }*/

   @Test
   public void parametreTest() {
       p.declMeth("Var", 2, Sorte.INT);
       assertEquals(2, p.parametre("Var"));
   }

   @Test
   public void valTTest() {
       p.declTab("Tab", 10, Sorte.INT);
       try {
           p.affecterValT("Tab", 5, 0);
           assertEquals(5, p.valT("Tab", 0));
       }catch (PileException e) {
           Assert.fail();
       }
   }

   @Test
    public void valTPileVideTest() {
       try {
           p.valT("Tab", 0);
       }catch (PileException e) {
           return;
       }
       Assert.fail();
   }

   @Test
   public void valTIDNonExistantTest() {
       p.declVar("Var", 5, Sorte.INT);
       try {
           assertNull(p.valT("Tab", 0));
       }catch (PileException e) {
           Assert.fail();
       }
   }

   @Test
   public void affecterValTPileVideTest() {
       try {
           p.affecterValT("Var", 2, 0);
       }catch (PileException e) {
           return;
       }
       Assert.fail();
   }
}
