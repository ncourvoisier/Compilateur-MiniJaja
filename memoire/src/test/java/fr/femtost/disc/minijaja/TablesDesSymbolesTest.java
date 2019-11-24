package fr.femtost.disc.minijaja;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TablesDesSymbolesTest {

    private TableDesSymboles tds;

    @Before
    public void Begin() {
        tds = new TableDesSymboles();
    }

    @Test
    public void tdsEmpty() {
        assertEquals(0, tds.getTableSymbole().size());
        assertNull(tds.chercheQuad("var"));
        assertNull(tds.chercheQuad("var", Sorte.INT));
    }

    @Test
    public void tdsCreerSymbole() {
        assertEquals(0, tds.getTableSymbole().size());
        assertNull(tds.chercheQuad("var"));
        assertNull(tds.chercheQuad("var", Sorte.INT));

        Quad q = tds.creerSymboles("var", 1, NatureObjet.VAR, Sorte.INT);
        assertEquals(q.getID(), "var");
        assertEquals(q.getVAL(), 1);
        assertEquals(q.getOBJ(), NatureObjet.VAR);
        assertEquals(q.getSORTE(), Sorte.INT);
    }

    @Test
    public void tdsChercherQuadAvecIDSORTE() {
        Quad q = tds.creerSymboles("var", 1, NatureObjet.VAR, Sorte.INT);
        Quad retQ = tds.chercheQuad("var", Sorte.INT);

        assertEquals(q.getID(), retQ.getID());
        assertEquals(q.getVAL(), retQ.getVAL());
        assertEquals(q.getOBJ(), retQ.getOBJ());
        assertEquals(q.getSORTE(), retQ.getSORTE());
    }

    @Test
    public void tdsEnlverSymboleEmpty() {
        Quad q1 = new Quad("var", 1, NatureObjet.VAR, Sorte.INT);
        assertEquals(0, tds.getTableSymbole().size());
        tds.enleverSymbole(q1.getID());
        assertEquals(0, tds.getTableSymbole().size());
    }

    @Test
    public void tdsEnlverSymbolePremier() {
        Quad q1 = tds.creerSymboles("var", 1, NatureObjet.VAR, Sorte.INT);
        Quad q2 = tds.creerSymboles("var2", 2, NatureObjet.VAR, Sorte.INT);

        assertEquals(q1.getID(), "var");
        assertEquals(q1.getVAL(), 1);
        assertEquals(q1.getOBJ(), NatureObjet.VAR);
        assertEquals(q1.getSORTE(), Sorte.INT);
        assertEquals(2, tds.getTableSymbole().size());

        tds.enleverSymbole(q1.getID());

        assertEquals(0, tds.getTableSymbole().get("var").size());
        //assertNull(tds.chercheQuad("var"));
        //System.out.println(tds.chercheQuad("var", Sorte.INT).getSORTE());
    }

}
