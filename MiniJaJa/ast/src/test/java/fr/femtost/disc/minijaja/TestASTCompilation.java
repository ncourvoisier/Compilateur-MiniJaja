package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
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
import fr.femtost.disc.minijaja.jcode.*;
import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;
import fr.femtost.disc.minijaja.jcodes.JChain;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.fail;

public class TestASTCompilation {

    /* ****************
        Expressions
    **************** */

    @Test
    public void test_simple(){
        Nbre n = new Nbre(10);
        CompilationCouple cc = n.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(1, ls.size());
        Assert.assertTrue(ls.get(0) instanceof Push);

        BoolVal b = new BoolVal(true);
        CompilationCouple cc2 = b.compiler(0);
        List<JCode> ls2 = JCodes.asArray(cc2.jCodes);
        Assert.assertEquals(1, ls.size());
        Assert.assertTrue(ls.get(0) instanceof Push);
    }

    @Test
    public void test_identifiant() {
        Identifiant i = new Identifiant("cst");
        CompilationCouple cc = i.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(1,ls.size());
        Assert.assertTrue(ls.get(0) instanceof Load);
    }

    @Test
    public void test_tableau() {
        Tableau t = new Tableau("t",new Nbre(10));
        CompilationCouple cc = t.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(ls.size()-1) instanceof ALoad);
    }

    @Test
    public void test_monop() {

        BoolVal b = new BoolVal(true);
        Not not = new Not(b);
        CompilationCouple cc = not.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(ls.size()-1) instanceof OpUnaire);
    }

    @Test
    public void test_binop() {
        ASTExpr e1 = new Nbre(6);
        ASTExpr e2 = new Nbre(2);
        ASTExpr b1 = new BoolVal(false);
        ASTExpr b2 = new BoolVal(true);

        Binop o1 = new Binop(Binop.Operandes.ADDITION,e1,e2);
        Binop o2 = new Binop(Binop.Operandes.AND,b1,b2);

        CompilationCouple cc1 = o1.compiler(0);
        List<JCode> ls1 = JCodes.asArray(cc1.jCodes);
        Assert.assertEquals(3,ls1.size());
        Assert.assertTrue(ls1.get(ls1.size()-1) instanceof OpBinaire);

        CompilationCouple cc2 = o2.compiler(0);
        List<JCode> ls2 = JCodes.asArray(cc2.jCodes);
        Assert.assertEquals(3,ls2.size());
        Assert.assertTrue(ls2.get(ls2.size()-1) instanceof OpBinaire);
    }

    @Test
    public void test_appelE() {
        AppelE ape = new AppelE(new Identifiant("x"), new ExChain(new Nbre(10), new Exnil()));

        CompilationCouple cc = ape.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(4,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Invoke);
        Assert.assertTrue(ls.get(2) instanceof Swap);
        Assert.assertTrue(ls.get(3) instanceof Pop);

    }


    /* ****************
       Variable
   **************** */

    @Test
    public void test_decl_const(){
        ASTVarConst c1 = new ASTVarConst(new Entier(), new Identifiant("x"), new Nbre(1));

        CompilationCouple cc = c1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(0) instanceof Push);
        Assert.assertTrue(ls.get(1) instanceof New);

    }

    @Test
    public void test_decl_varSimple(){
        ASTVarSimple c1 = new ASTVarSimple(new Entier(), new Identifiant("x"), new Nbre(1));

        CompilationCouple cc = c1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);

        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(0) instanceof Push);
        Assert.assertTrue(ls.get(1) instanceof New);
    }


    @Test
    public void test_declr_meth(){
        ASTMethode methode = new ASTMethode(new Entier(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));
        CompilationCouple cc = methode.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);

        Assert.assertEquals(6,ls.size());
        Assert.assertTrue(ls.get(5) instanceof Return);

        ASTMethode methode2 = new ASTMethode(new Void(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));

        CompilationCouple cc2 = methode.compiler(0);
        List<JCode> ls1 = JCodes.asArray(cc2.jCodes);

        Assert.assertEquals(6,ls.size());
        Assert.assertTrue(ls.get(5) instanceof Return);
    }

    @Test
    public void test_decl_DChain(){
        ASTMethode methode = new ASTMethode(new Entier(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));

        DChain d1 = new  DChain(new DChain(new Dnil(), new ASTVarConst(new Booleen(), new Identifiant("temp"), new BoolVal(true))), methode);

        CompilationCouple cc = d1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(8,ls.size());
        Assert.assertTrue(ls.get(5) instanceof Return);



    }

    @Test
    public void test_decl_varTab(){
        ASTVarTableau v1 = new ASTVarTableau(new Entier(),new Identifiant("t"), new Nbre(1));
        CompilationCouple cc = v1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof NewArray);
    }

    @Test
    public void test_vars_Vchain(){
        ASTVars v1 = new VChain(new ASTVarSimple(new Entier(), new Identifiant("toto"), new Omega()), new Vnil());
        CompilationCouple cc = v1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof New);

    }

    /* ****************
       Instruction
   **************** */

    @Test
    public void test_instr_Ecrireln(){
        ASTInstr i1 = new EcrireLn(new Identifiant("varInt"));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof WriteLn);
    }

    @Test
    public void test_instr_affectation(){
        ASTInstr i1 = new Affectation(new Identifiant("varInt"), new Nbre(2));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Store);
    }

    @Test
    public void test_instr_affectationtab(){
        ASTInstr i1 = new Affectation(new Tableau("t", new Nbre(0)), new Nbre(2));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(3,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Push);
    }

    @Test
    public void test_instr_ecrire(){
        ASTInstr i1 = new Ecrire(new Identifiant("varInt"));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Write);
    }

    @Test
    public void test_instr_incrV(){
        ASTInstr i1 = new Increment(new Tableau("t", new Nbre(0)));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(3,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Push);
    }

    @Test
    public void test_instr_retour(){
        Retour i1 = new Retour(new Nbre(0));
        CompilationCouple cc = i1.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(1,ls.size());
        Assert.assertTrue(ls.get(0) instanceof Push);
    }

    @Test
    public void test_instr_Si(){
        Si s = new Si(new BoolVal(false), new Inil(), new IChain(new Affectation(new Identifiant("varInt"), new Nbre(0)),new Inil()) );
        CompilationCouple cc = s.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(5,ls.size());
        Assert.assertTrue(ls.get(4) instanceof Goto);
    }

    @Test
    public void test_instr_Somme(){
        Somme s = new Somme(new Identifiant("varInt"), new Nbre(10));
        CompilationCouple cc = s.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(2,ls.size());
        Assert.assertTrue(ls.get(1) instanceof Inc);
    }

    @Test
    public void test_instr_SommeT(){
        Somme s = new Somme(new Tableau("t", new Nbre(0)), new Nbre(1));
        CompilationCouple cc = s.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(3,ls.size());
        Assert.assertTrue(ls.get(2) instanceof AInc);
    }

    @Test
    public void test_instr_while(){
        TantQue tq = new TantQue(new Binop(Binop.Operandes.SUPERIEUR,new Nbre(6), new Identifiant("varInt") ), new IChain(new Affectation(new Identifiant("varInt"), new Nbre(7)),new Inil()));
        CompilationCouple cc = tq.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(8,ls.size());
        Assert.assertTrue(ls.get(7) instanceof Goto);
    }

    @Test
    public void test_Class(){
        ASTClass c = new ASTClass(new Identifiant("b"), new DChain(new Dnil(), new ASTVarSimple(new Entier(), new Identifiant("x"), new Omega())), new ASTMain(new Vnil(), new Inil()));
        CompilationCouple cc = c.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(6,ls.size());
        Assert.assertTrue(ls.get(5) instanceof JCStop);
    }

    @Test
    public void test_entete(){
        ASTEntete e = new ASTEntete(new Identifiant("nombre"), new Entier());
        CompilationCouple cc = e.compiler(0);
        List<JCode> ls = JCodes.asArray(cc.jCodes);
        Assert.assertEquals(1,ls.size());
        Assert.assertTrue(ls.get(0) instanceof New);
    }



}
