package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;
import org.junit.Assert;
import org.junit.Test;

public class TestsOper {
    private static Memoire getMemoire() {
        Memoire r = new Memoire(128);
        r.getPile().declVar("classe", null, null);
        r.getPile().declVar("varInt", 10, Sorte.INT);
        r.getPile().declVar("varBool", false, Sorte.BOOL);
        r.getPile().declCst("cst", 9, Sorte.INT);
        r.getPile().declTab("t", 4, Sorte.INT);
        r.getPile().declTab("tBool", 1, Sorte.BOOL);

        return r;
    }

    private static Memoire emptyMemoire() {
        return new Memoire(128);
    }

    @Test
    public void TestOpBin()
    {
        OpBinaire op = new OpBinaire(OpBinaire.Operandes.ADD);
        Memoire m = emptyMemoire();
        m.getPile().declVar("Add",5,null);
        m.getPile().declVar("",8,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.DIV);
        m.getPile().declVar("",8,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.MUL);
        m.getPile().declVar("",8,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.SUB);
        m.getPile().declVar("",8,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.SUP);
        m.getPile().declVar("",8,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        op = new OpBinaire(OpBinaire.Operandes.AND);
        m.getPile().declVar("",true,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.OR);
        m.getPile().declVar("",true,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
        op = new OpBinaire(OpBinaire.Operandes.CMP);
        m.getPile().declVar("",false,null);
        Assert.assertEquals(7,op.interpreter(m,6));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
    }
    @Test
    public void TestOpBinaireException()
    {
        Memoire m = emptyMemoire();
        OpBinaire op = new OpBinaire(OpBinaire.Operandes.ADD);
        Assert.assertEquals(-1,op.interpreter(m,6));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void TestOpBinaireRewrite()
    {
        Memoire m = emptyMemoire();
        m.getPile().declVar("Add",5,null);
        m.getPile().declVar("",8,null);
        OpBinaire op = new OpBinaire(OpBinaire.Operandes.ADD);
        Assert.assertEquals("add",op.rewrite());
        Assert.assertEquals(2, m.getPile().returnTaillePile());
    }

}
