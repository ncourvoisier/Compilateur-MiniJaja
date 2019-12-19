package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarConst;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarSimple;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarTableau;
import fr.femtost.disc.minijaja.ast.decls.DChain;
import fr.femtost.disc.minijaja.ast.decls.Dnil;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.ast.expr.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.ast.instr.*;
import fr.femtost.disc.minijaja.ast.instr.ecrire.EcrireLn;
import fr.femtost.disc.minijaja.ast.instrs.IChain;
import fr.femtost.disc.minijaja.ast.instrs.Inil;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;
import fr.femtost.disc.minijaja.ast.listexpr.Exnil;
import fr.femtost.disc.minijaja.ast.type.Booleen;
import fr.femtost.disc.minijaja.ast.type.Entier;
import fr.femtost.disc.minijaja.ast.type.Void;
import fr.femtost.disc.minijaja.ast.vars.VChain;
import fr.femtost.disc.minijaja.ast.vars.Vnil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

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

    @Test
    public void test_decl_varSimple(){
        ASTVarSimple c1 = new ASTVarSimple(new Entier(), new Identifiant("x"), new Nbre(1));

        Memoire m = new Memoire(50);
        c1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("x", q.getID());
        Assert.assertEquals(1,q.getVAL());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
    }

    @Test
    public void test_decl_varTab(){
        ASTVarTableau v1 = new ASTVarTableau(new Entier(),new Identifiant("t"), new Nbre(1));

        Memoire m = new Memoire(50);
        v1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("t", q.getID());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(NatureObjet.TAB,q.getOBJ());
        //getval??
    }

    @Test
    public void test_declr_meth(){
        ASTMethode methode = new ASTMethode(new Entier(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));

        Memoire m = new Memoire(50);
        methode.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("meth", q.getID());
        Assert.assertEquals(NatureObjet.METH,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
    }

    @Test
    public void test_decl_DChain(){
        ASTMethode methode = new ASTMethode(new Entier(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));

        DChain d1 = new  DChain(new DChain(new Dnil(), new ASTVarConst(new Booleen(), new Identifiant("temp"), new BoolVal(true))), methode);


        Memoire m = new Memoire(50);
        d1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("temp", q.getID());
        Assert.assertEquals(NatureObjet.CST,q.getOBJ());
        Assert.assertEquals(Sorte.BOOL,q.getSORTE());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void test_entete_EChain(){
        ASTEntetes e = new Enil();
        e.interpreter(emptyMemoire());
    }

    @Test
    public void test_vars_Vchain(){
        ASTVars v1 = new VChain(new ASTVarSimple(new Entier(), new Identifiant("toto"), new Omega()), new Vnil());

        Memoire m = new Memoire(50);
        v1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("toto", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
    }

    //a revoir

    @Test
    public void test_instr_Ecrireln(){
         ASTInstr i1 = new EcrireLn(new Identifiant("varInt"));

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        i1.interpreter(m);

        Quad q = m.getPile().getStackTop();

    }

    @Test
    public void test_instr_affectation(){
        ASTInstr i1 = new Affectation(new Identifiant("varInt"), new Nbre(2));

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        i1.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
    }

    //probleme coverage
    @Test
    public void test_instr_affectationtab(){
        ASTInstr i1 = new Affectation(new Tableau("t", new Nbre(0)), new Nbre(2));

        Memoire m = new Memoire(50);
        m.getPile().declTab("t", 4, Sorte.INT);
        try{
            m.getPile().affecterValT("t",4,0);
            m.getPile().valT("t",0);
            Quad q = m.getPile().getStackTop();

            i1.interpreter(m);

        }catch (PileException p){
            p.getStackTrace();
        }
    }
    /*
    @Test
    public void test_instr_AppelI(){
        ASTInstr i1 = new AppelI(new Identifiant("meth"), new Exnil());

        Memoire m = new Memoire(50);
        m.getPile().declMeth("meth", new ASTMethode(new Entier(), new Identifiant("meth"),
                new EChain(new Enil(), new ASTEntete(new Identifiant("r0"), new Entier())),
                new Vnil(), new IChain(new Retour(new Nbre(42)), new Inil())), Sorte.INT);
        System.out.println(m.getPile().getStackTop());
        i1.interpreter(m);

        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("meth", q.getID());

    }*/

    @Test
    public void test_instr_ecrire(){
        ASTInstr i1 = new Ecrire(new Identifiant("varInt"));

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        i1.interpreter(m);

        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(10,q.getVAL());
    }

    @Test
    public void test_instr_incrV(){
        ASTInstr i1 = new Increment(new Identifiant("varInt"));

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        i1.interpreter(m);

        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(11,q.getVAL());
    }

    //Tableau probleme coverage, assert
    @Test
    public void test_instr_incrT(){
        //ASTInstr i2 = new Affectation(new Identifiant("t"),)
        ASTInstr i1 = new Increment(new Tableau("t", new Nbre(0)));

        Memoire m = new Memoire(50);
        m.getPile().declTab("t", 4, Sorte.INT);
        try{
            m.getPile().affecterValT("t",4,0);
            m.getPile().valT("t",0);
            Quad q = m.getPile().getStackTop();

            i1.interpreter(m);

        }catch (PileException p){
            p.getStackTrace();
        }
    }


    //return null
    @Test
    public void test_instr_retour(){
        Retour i1 = new Retour(new Nbre(0));

        Memoire m = new Memoire(50);
        i1.interpreter(m);
        Quad q = m.getPile().getStackTop();
    }

    @Test
    public void test_instr_SiFalse(){

        Si s = new Si(new BoolVal(false), new Inil(), new IChain(new Affectation(new Identifiant("varInt"), new Nbre(0)),new Inil()) );

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        s.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(0,q.getVAL());
    }

    @Test
    public void test_instr_SiTrue(){

        Si s = new Si(new BoolVal(true), new Inil(), new IChain(new Affectation(new Identifiant("varInt"), new Nbre(0)),new Inil()) );

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        s.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(10,q.getVAL());
    }

    @Test
    public void test_instr_Somme(){
        Somme s = new Somme(new Identifiant("varInt"), new Nbre(10));

        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 10, Sorte.INT);
        s.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(20,q.getVAL());
    }

    //assert a voir
    @Test
    public void test_instr_SommeT(){
        Somme s = new Somme(new Tableau("t", new Nbre(0)), new Nbre(1));

        Memoire m = new Memoire(50);
        m.getPile().declTab("t", 4, Sorte.INT);
        try{

            m.getPile().affecterValT("t",4,0);
            m.getPile().valT("t",0);
            Quad q = m.getPile().getStackTop();

            s.interpreter(m);

        }catch (PileException p){
            p.getStackTrace();
        }
    }

    @Test
    public void test_instr_while(){

        TantQue tq = new TantQue(new Binop(Binop.Operandes.SUPERIEUR,new Nbre(6), new Identifiant("varInt") ), new IChain(new Affectation(new Identifiant("varInt"), new Nbre(7)),new Inil()));
        Memoire m = new Memoire(50);
        m.getPile().declVar("varInt", 4, Sorte.INT);

        tq.interpreter(m);
        Quad q = m.getPile().getStackTop();

        Assert.assertEquals("varInt", q.getID());
        Assert.assertEquals(NatureObjet.VAR,q.getOBJ());
        Assert.assertEquals(Sorte.INT,q.getSORTE());
        Assert.assertEquals(7,q.getVAL());
    }


    @Test
    public void test_Class(){

        ASTClass c = new ASTClass(new Identifiant("b"), new DChain(new Dnil(), new ASTVarSimple(new Entier(), new Identifiant("x"), new Omega())), new ASTMain(new Vnil(), new Inil()));

        Memoire m = new Memoire(50);
        c.interpreter(m);
        Quad q = m.getPile().getStackTop();

    }

    @Test
    public void test_entete(){
        ASTEntete e = new ASTEntete(new Identifiant("nombre"), new Entier());

        Memoire m = new Memoire(50);
        e.interpreter(m);
        Quad q = m.getPile().getStackTop();

    }














}
