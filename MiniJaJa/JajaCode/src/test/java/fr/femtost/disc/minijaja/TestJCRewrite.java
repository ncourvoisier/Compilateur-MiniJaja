package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcode.*;
import org.junit.Assert;
import org.junit.Test;

public class TestJCRewrite {
    @Test
    public void rewriteAInc(){
        AInc test = new AInc("test");
        Assert.assertEquals("ainc(test)",test.rewrite());
    }
    @Test
    public void rewriteALoad(){
        ALoad test = new ALoad("test");
        Assert.assertEquals("aload(test)",test.rewrite());
    }
    @Test
    public void rewriteAStore(){
        AStore test = new AStore("test");
        Assert.assertEquals("astore(test)",test.rewrite());
    }
    @Test
    public void rewriteGoto(){
        Goto test = new Goto(71);
        Assert.assertEquals("goto(71)",test.rewrite());
    }
    @Test
    public void rewriteIf(){
        If test = new If(45);
        Assert.assertEquals("if(45)",test.rewrite());
    }
    @Test
    public void rewriteInc(){
        Inc test = new Inc("test");
        Assert.assertEquals("inc(test)",test.rewrite());
    }
    @Test
    public void rewriteInit(){
        Init test = new Init();
        Assert.assertEquals("init",test.rewrite());
    }
    @Test
    public void rewriteInvoke(){
        Invoke test = new Invoke("test");
        Assert.assertEquals("invoke(test)",test.rewrite());
    }
    @Test
    public void rewriteJCStop(){
        JCStop test = new JCStop();
        Assert.assertEquals("jcstop",test.rewrite());
    }
    @Test
    public void rewriteLoad(){
        Load test = new Load("test");
        Assert.assertEquals("load(test)",test.rewrite());
    }
    @Test
    public void rewriteNew(){
        New test = new New("test",Sorte.BOOL,JCSorte.CONSTANTE,4);
        Assert.assertEquals("new(test,BOOL,CONSTANTE,4)",test.rewrite());
    }
    @Test
    public void rewriteNewArray(){
        NewArray test = new NewArray("test",Sorte.BOOL);
        Assert.assertEquals("newarray(test,BOOL)",test.rewrite());
    }
    @Test
    public void rewritePop(){
        Pop test = new Pop();
        Assert.assertEquals("pop",test.rewrite());
    }
    @Test
    public void rewritePush(){
        Push test = new Push(4);
        Assert.assertEquals("push(4)",test.rewrite());
    }
    @Test
    public void rewriteReturn(){
        Return test = new Return();
        Assert.assertEquals("return",test.rewrite());
    }
    @Test
    public void rewriteStore(){
        Store test = new Store("test");
        Assert.assertEquals("store(test)",test.rewrite());
    }
    @Test
    public void rewriteSwap(){
        Swap test = new Swap();
        Assert.assertEquals("swap",test.rewrite());
    }
    @Test
    public void rewriteWrite(){
        Write test = new Write();
        Assert.assertEquals("write",test.rewrite());
    }
    @Test
    public void rewriteWriteLn(){
        WriteLn test = new WriteLn();
        Assert.assertEquals("writeln",test.rewrite());
    }
}
