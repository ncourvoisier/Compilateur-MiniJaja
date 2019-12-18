package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcode.Goto;
import fr.femtost.disc.minijaja.jcode.Load;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestStaticJCodes {

    private static JCodes getChain() {
        JCode j1 = new Push(6);
        JCode j2 = new Pop();
        JCode j3 = new Load("cst");

        return new JChain(j1, new JChain(j2, new JChain(j3, new JNil())));
    }

    @Test
    public void test_toArray() {
        JCode j1 = new Push(6);
        JCode j2 = new Pop();
        JCode j3 = new Load("cst");

        JCodes s = new JChain(j1, new JChain(j2, new JChain(j3, new JNil())));
        List<JCode> ls = JCodes.asArray(s);
        Assert.assertEquals(3, ls.size());
        Assert.assertEquals(j1, ls.get(0));
        Assert.assertEquals(j2, ls.get(1));
        Assert.assertEquals(j3, ls.get(2));
    }

    @Test
    public void test_concat_right() {
        JCodes chain = getChain();
        JCode add = new Goto(44);

        List<JCode> ls = JCodes.asArray(JCodes.concatRight(chain, add));
        Assert.assertEquals(4, ls.size());
        Assert.assertEquals(add, ls.get(3));
    }

    @Test
    public void test_concat_right_nil() {
        JCodes chain = new JNil();
        JCode add = new Goto(44);

        List<JCode> ls = JCodes.asArray(JCodes.concatRight(chain, add));
        Assert.assertEquals(1, ls.size());
        Assert.assertEquals(add, ls.get(0));
    }

    @Test
    public void test_concat_left() {
        JCodes chain = getChain();
        JCode add = new Goto(44);

        List<JCode> ls = JCodes.asArray(JCodes.concatLeft(add, chain));
        Assert.assertEquals(4, ls.size());
        Assert.assertEquals(add, ls.get(0));
    }

    @Test
    public void test_concatenate_nil() {
        JCodes codes = getChain();
        Assert.assertEquals(codes, JCodes.concatenate(codes, new JNil()));
        Assert.assertEquals(codes, JCodes.concatenate(new JNil(), codes));
    }

    @Test
    public void test_concatenate() {
        JCode j1 = new Push(6);
        JCode j2 = new Pop();
        JCode j3 = new Load("cst");
        JCodes s = new JChain(j1, new JChain(j2, new JChain(j3, new JNil())));
        JCodes codes = getChain();

        List<JCode> ls = JCodes.asArray(JCodes.concatenate(s, codes));
        Assert.assertEquals(6, ls.size());
        Assert.assertEquals(j3, ls.get(2));
    }
}
