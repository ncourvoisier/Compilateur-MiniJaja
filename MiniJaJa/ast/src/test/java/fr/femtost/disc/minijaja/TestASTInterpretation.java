package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarConst;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.ast.expr.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.ast.instr.Retour;
import fr.femtost.disc.minijaja.ast.instrs.IChain;
import fr.femtost.disc.minijaja.ast.instrs.Inil;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;
import fr.femtost.disc.minijaja.ast.listexpr.Exnil;
import fr.femtost.disc.minijaja.ast.type.Entier;
import fr.femtost.disc.minijaja.ast.type.Void;
import fr.femtost.disc.minijaja.ast.vars.Vnil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class TestASTInterpretation {

    private static Memoire getMemoire() {
        Memoire r = new Memoire(128);
        r.getPile().declVar("classe", null, null);
        r.getPile().declVar("varInt", 10, Sorte.INT);
        r.getPile().declVar("varBool", false, Sorte.BOOL);
        r.getPile().declCst("cst", 9, Sorte.INT);
        r.getPile().declMeth("meth", new ASTMethode(new Entier(), new Identifiant("meth"),
                new EChain(new Enil(), new ASTEntete(new Identifiant("r0"), new Entier())),
                new Vnil(), new IChain(new Retour(new Nbre(42)), new Inil())), Sorte.INT);
        r.getPile().declMeth("methVoid", new ASTMethode(new Void(), new Identifiant("methVoid"),
                new Enil(), new Vnil(), new Inil()), Sorte.VOID);
        r.getPile().declTab("t", 4, Sorte.INT);
        r.getPile().declTab("tBool", 1, Sorte.BOOL);

        return r;
    }

    private static Memoire emptyMemoire() {
        return new Memoire(128);
    }

    /* ****************
        Expressions
    **************** */

    @Test
    public void test_eval_simple(){
        Assert.assertEquals(10, new Nbre(10).eval(emptyMemoire()));
        Assert.assertEquals(true, new BoolVal(true).eval(emptyMemoire()));
    }

    @Test
    public void test_eval_identifiant() {
        Assert.assertEquals(9, new Identifiant("cst").eval(getMemoire()));
        Assert.assertNull(new Identifiant("ident").eval(emptyMemoire()));
        Assert.assertEquals("NOK_", new Identifiant("ident").eval(getMemoire()));
    }

    @Test
    public void test_eval_tableau() {
        Memoire r = getMemoire();
        try {
            r.getPile().affecterValT("t", 14, 0);
        } catch (PileException e) {
            fail();
        }
        Assert.assertEquals(14, new Tableau("t", new Nbre(0)).eval(r));
        Assert.assertNull(new Tableau("tableau", new Nbre(0)).eval(r));
        Assert.assertNull(new Tableau("t", new Nbre(-1)).eval(r));
    }

    @Test
    public void test_eval_monop() {
        Assert.assertEquals(true, new Not(new BoolVal(false)).eval(emptyMemoire()));
        Assert.assertEquals(-6, new Negation(new Nbre(6)).eval(emptyMemoire()));
    }

    @Test
    public void test_eval_omega() {
        Assert.assertNull(new Omega().eval(emptyMemoire()));
    }

    @Test
    public void test_eval_binop() {
        ASTExpr e1 = new Nbre(6);
        ASTExpr e2 = new Nbre(2);
        ASTExpr b1 = new BoolVal(false);
        ASTExpr b2 = new BoolVal(true);

        Assert.assertEquals(8, new Binop(Binop.Operandes.ADDITION, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(4, new Binop(Binop.Operandes.SOUSTRACTION, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(12, new Binop(Binop.Operandes.MULTIPLICATION, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(3, new Binop(Binop.Operandes.DIVISION, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(true, new Binop(Binop.Operandes.SUPERIEUR, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(false, new Binop(Binop.Operandes.EGAL, e1, e2).eval(emptyMemoire()));
        Assert.assertEquals(false, new Binop(Binop.Operandes.AND, b1, b2).eval(emptyMemoire()));
        Assert.assertEquals(true, new Binop(Binop.Operandes.OR, b1, b2).eval(emptyMemoire()));
    }

    @Test
    public void test_eval_appelE() {
        ASTClass c = new ASTClass(new Identifiant("classe"), null, null); // set Variable Classe
        Assert.assertEquals(42, new AppelE(new Identifiant("meth"), new ExChain(new Nbre(10), new Exnil())).eval(getMemoire()));
    }

    /* ****************
       Variable
   **************** */
    @Test
    public void test_decl_const(){
        ASTVarConst c1 = new ASTVarConst(new Entier(), new Identifiant("x"), new Nbre(1));

        Memoire m = new Memoire(50);
        c1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("x", q.getID());
        Assert.assertEquals(1,q.getVAL());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(NatureObjet.CST,q.getOBJ());

    }


}
