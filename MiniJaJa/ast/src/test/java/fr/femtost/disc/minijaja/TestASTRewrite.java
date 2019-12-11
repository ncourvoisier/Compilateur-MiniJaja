package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;
import fr.femtost.disc.minijaja.ast.listexpr.Exnil;
import org.junit.Assert;
import org.junit.Test;

public class TestASTRewrite {

    @Test
    public void test_rewrite_nbre() {
        Assert.assertEquals("10", new Nbre(10).rewrite());
    }

    @Test
    public void test_rewrite_ident() {
        Assert.assertEquals("toto", new Identifiant("toto").rewrite());
    }

    @Test
    public void test_rewrite_string() {
        Assert.assertEquals("\"toto\"", new HardcodedString("\"toto\"").rewrite());
    }

    @Test
    public void test_rewrite_bool() {
        Assert.assertEquals("true", new BoolVal(true).rewrite());
        Assert.assertEquals("false", new BoolVal(false).rewrite());
    }

    @Test
    public void test_rewrite_not() {
        Assert.assertEquals("!true", new Not(new BoolVal(true)).rewrite());
    }

    @Test
    public void test_rewrite_neg() {
        Assert.assertEquals("-(10)", new Negation(new Nbre(10)).rewrite());
    }

    @Test
    public void test_rewrite_omega() {
        Assert.assertEquals("", new Omega().rewrite());
    }

    @Test
    public void test_rewrite_binop() {
        ASTExpr e1 = new Nbre(6);
        ASTExpr e2 = new Nbre(8);
        Assert.assertEquals("(6+8)", new Binop(Binop.Operandes.ADDITION, e1, e2).rewrite());
        Assert.assertEquals("(6-8)", new Binop(Binop.Operandes.SOUSTRACTION, e1, e2).rewrite());
        Assert.assertEquals("(6*8)", new Binop(Binop.Operandes.MULTIPLICATION, e1, e2).rewrite());
        Assert.assertEquals("(6/8)", new Binop(Binop.Operandes.DIVISION, e1, e2).rewrite());
        Assert.assertEquals("(6>8)", new Binop(Binop.Operandes.SUPERIEUR, e1, e2).rewrite());
        Assert.assertEquals("(6==8)", new Binop(Binop.Operandes.EGAL, e1, e2).rewrite());
        Assert.assertEquals("(6&&8)", new Binop(Binop.Operandes.AND, e1, e2).rewrite());
        Assert.assertEquals("(6||8)", new Binop(Binop.Operandes.OR, e1, e2).rewrite());
    }

    @Test
    public void test_rewrite_tab() {
        Assert.assertEquals("t[6]", new Tableau("t", new Nbre(6)).rewrite());
    }

    @Test
    public void test_rewrite_lsExp() {
        Assert.assertEquals("6", new ExChain(new Nbre(6), new Exnil()).rewrite());
        Assert.assertEquals("3,6", new ExChain(new Nbre(3), new ExChain(new Nbre(6), new Exnil())).rewrite());
    }

    @Test
    public void test_rewrite_appel() {
        Assert.assertEquals("meth(4)", new AppelE(new Identifiant("meth"), new ExChain(new Nbre(4), new Exnil())).rewrite());
    }
}