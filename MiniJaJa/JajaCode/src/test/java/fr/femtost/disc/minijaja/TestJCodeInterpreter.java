package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcode.*;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class TestJCodeInterpreter {

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
    public void interpreterAInc()
    {
        Memoire m = getMemoire();
        int taille = m.getPile().returnTaillePile();
        m.getPile().declCst(null, 0, null);
        m.getPile().declCst(null, 6, null);
        try {
            m.getPile().affecterValT("t", 2, 0);
        } catch (PileException e) {
            fail();
        }

        Assert.assertEquals(3, new AInc("t").interpreter(m, 2));
        try {
            Assert.assertEquals(8, m.getPile().valT("t", 0));
        } catch (PileException e) {
            fail();
        }
        Assert.assertEquals(taille, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterAIncException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new AInc("t").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterALoad()
    {
        Memoire m = getMemoire();
        int taille = m.getPile().returnTaillePile();
        m.getPile().declCst("index", 0, null);
        try {
            m.getPile().affecterValT("t", 12, 0);
        } catch (PileException e) {
            fail();
        }

        Assert.assertEquals(3, new ALoad("t").interpreter(m, 2));
        Assert.assertEquals(12, m.getPile().getStackTop().getVAL());
        Assert.assertEquals(taille+1, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterALoadException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new ALoad("t").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterAStore()
    {
        Memoire m = getMemoire();
        int taille = m.getPile().returnTaillePile();
        m.getPile().declCst("index", 0, null);
        m.getPile().declCst("val", 6, null);
        try {
            m.getPile().affecterValT("t", 12, 0);
        } catch (PileException e) {
            fail();
        }

        Assert.assertEquals(3, new AStore("t").interpreter(m, 2));
        try {
            Assert.assertEquals(6, m.getPile().valT("t", 0));
        } catch (PileException e) {
            fail();
        }
        Assert.assertEquals(taille, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterAStoreException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new AStore("t").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterGoTo()
    {
        Memoire m = emptyMemoire();

        Assert.assertEquals(10, new Goto(10).interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterIf()
    {
        Memoire m = emptyMemoire();
        m.getPile().declCst("condition", true, null);
        Assert.assertEquals(57, new If(57).interpreter(m, 9));
        m.getPile().declCst("condition", false, null);
        Assert.assertEquals(10, new If(57).interpreter(m, 9));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterIfException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new If(6).interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterInc()
    {
        Memoire m = emptyMemoire();
        m.getPile().declVar("Add",5,null);
        m.getPile().declVar("",8,null);

        Assert.assertEquals(3, new Inc("Add").interpreter(m, 2));
        Assert.assertEquals(13,m.getPile().getStackTop().getVAL());
        Assert.assertEquals(1, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterIncException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Inc("t").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
  @Test
  public void interpreterInit()
  {
      Memoire m = emptyMemoire();
      Assert.assertEquals(89, new Init().interpreter(m, 88));
      Assert.assertEquals(0, m.getPile().returnTaillePile());
  }
    @Test
    public void interpreterInvoke()
    {
        Memoire m = emptyMemoire();
        m.getPile().declVar("Add",5,null);
        m.getPile().declVar("",8,null);

        Assert.assertEquals(5, new Invoke("Add").interpreter(m, 88));
        Assert.assertEquals(3, m.getPile().returnTaillePile());
    }
/*    @Test
    public void interpreterInvokeException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Invoke("test").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }*/
  @Test
  public void interpreterNew() {
      Memoire m = emptyMemoire();
      m.getPile().declCst(null, 10, null);
      Assert.assertEquals(10, new New("id",Sorte.VOID,JCSorte.METHODE,0).interpreter(m, 9));
      m.getPile().declCst(null, 77, null);
      Assert.assertEquals(10, new New("id",Sorte.BOOL,JCSorte.CONSTANTE,0).interpreter(m, 9));
      m.getPile().declCst(null, 3, null);
      Assert.assertEquals(10, new New("id",Sorte.INT,JCSorte.VARIABLE,0).interpreter(m, 9));
      Assert.assertEquals(3, m.getPile().returnTaillePile());
  }
    @Test
    public void interpreterNewException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new New("id",Sorte.VOID,JCSorte.METHODE,0).interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterLoad() {
        Memoire m = emptyMemoire();
        m.getPile().declCst(null, 10, null);
        Assert.assertEquals(10, new Load("id").interpreter(m, 9));
        Assert.assertEquals(2, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterLoadException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new NewArray("t",Sorte.INT).interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterNewArray() {
        Memoire m = emptyMemoire();
        m.getPile().declCst(null, 10, null);
        Assert.assertEquals(10, new NewArray("id",Sorte.BOOL).interpreter(m, 9));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterNewArrayException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new NewArray("t",Sorte.INT).interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterPop() {
        Memoire m = emptyMemoire();
        m.getPile().declCst(null, 10, null);
        m.getPile().declCst(null, 93, null);
        m.getPile().declCst(null, 40, null);
        Assert.assertEquals(75, new Pop().interpreter(m, 74));
        Assert.assertEquals(2, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterPopException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Pop().interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterPush() {
        Memoire m = emptyMemoire();
        int push = 4;
        Assert.assertEquals(77, new Push(push).interpreter(m, 76));
        Assert.assertEquals(1, m.getPile().returnTaillePile());
    }

    @Test
    public void interpreterReturn() {
        Memoire m = emptyMemoire();
        m.getPile().declCst(null, 930, null);
        m.getPile().declCst(null, 9, null);
        m.getPile().declCst(null, 3, null);
        m.getPile().declCst(null, 93, null);
        Assert.assertEquals(93, new Return().interpreter(m, 74));
        Assert.assertEquals(3, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterReturnException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Return().interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterStore() {
        Memoire m = emptyMemoire();

        m.getPile().declCst(null, 93, null);
        Assert.assertEquals(75, new Store("store").interpreter(m, 74));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterStoreException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Store("t").interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterSwap() {
        Memoire m = emptyMemoire();

        m.getPile().declCst("numero1", 93, null);
        m.getPile().declCst("numero2", 55, null);
        Assert.assertEquals("numero2",m.getPile().getStackTop().getID());
        Assert.assertEquals(75, new Swap().interpreter(m, 74));
        Assert.assertEquals(2, m.getPile().returnTaillePile());
        Assert.assertEquals("numero1",m.getPile().getStackTop().getID());
    }
    @Test
    public void interpreterSwapException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Swap().interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterWrite() {
        Memoire m = emptyMemoire();

        m.getPile().declCst("numero", 93, null);
        m.getPile().declCst("numero", 9, null);
        m.getPile().declCst("numero", 3, null);
        Assert.assertEquals(75, new Write().interpreter(m, 74));
        Assert.assertEquals(2, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterWriteException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new Write().interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterWriteLn() {
        Memoire m = emptyMemoire();

        m.getPile().declCst("numero", 93, null);
        m.getPile().declCst("numero", 9, null);
        m.getPile().declCst("numero", 3, null);
        Assert.assertEquals(75, new WriteLn().interpreter(m, 74));
        Assert.assertEquals(2, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterWriteLnException()
    {
        Memoire m = emptyMemoire();
        Assert.assertEquals(-1, new WriteLn().interpreter(m, 2));
        Assert.assertEquals(0, m.getPile().returnTaillePile());
    }
    @Test
    public void interpreterJCStop() {
        Memoire m = emptyMemoire();

        m.getPile().declCst("numero", 93, null);
        m.getPile().declCst("numero", 9, null);
        m.getPile().declCst("numero", 3, null);
        Assert.assertEquals(-1, new JCStop().interpreter(m, 74));
        Assert.assertEquals(3, m.getPile().returnTaillePile());
    }
}
