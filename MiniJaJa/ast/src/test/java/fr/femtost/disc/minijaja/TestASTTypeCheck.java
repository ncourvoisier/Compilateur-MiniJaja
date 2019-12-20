package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTMain;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarConst;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarSimple;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarTableau;
import fr.femtost.disc.minijaja.ast.decls.DChain;
import fr.femtost.disc.minijaja.ast.decls.Dnil;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.ast.expr.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.ast.instr.*;
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

public class TestASTTypeCheck {

    private static Memoire getMemoire() {
        Memoire r = new Memoire(128);
        r.getPile().declVar("varInt", 10, Sorte.INT);
        r.getPile().declVar("varBool", false, Sorte.BOOL);
        r.getPile().declCst("cst", 9, Sorte.INT);
        r.getPile().declMeth("meth", new ASTMethode(new Entier(), new Identifiant("meth"),
                new EChain(new Enil(), new ASTEntete(new Identifiant("r0"), new Entier())),
                new Vnil(), new Inil()), Sorte.INT);
        r.getPile().declMeth("methVoid", new ASTMethode(new Void(), new Identifiant("methVoid"),
                new Enil(), new Vnil(), new Inil()), Sorte.VOID);
        r.getPile().declTab("t", 1, Sorte.INT);
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
    public void test_typecheck_nbre() {
        Assert.assertTrue(new Nbre(10).typeCheck(null, null, Sorte.INT));
        Assert.assertTrue(new Nbre(10).typeCheck(null, null, Sorte.VOID));
        Assert.assertFalse(new Nbre(10).typeCheck(null, null, Sorte.BOOL));
    }

    @Test
    public void test_typecheck_bool() {
        Assert.assertTrue(new BoolVal(false).typeCheck(null, null, Sorte.BOOL));
        Assert.assertTrue(new BoolVal(false).typeCheck(null, null, Sorte.VOID));
        Assert.assertFalse(new BoolVal(false).typeCheck(null, null, Sorte.INT));
    }

    @Test
    public void test_typecheck_ident_missing() {
        Assert.assertFalse(new Identifiant("x").typeCheck(emptyMemoire(), emptyMemoire(), Sorte.VOID));
    }

    @Test
    public void test_typecheck_ident() {
        Assert.assertTrue(new Identifiant("varInt").typeCheck(emptyMemoire(), getMemoire(), Sorte.INT));
        Assert.assertTrue(new Identifiant("varInt").typeCheck(getMemoire(), emptyMemoire(), Sorte.VOID));
        Assert.assertFalse(new Identifiant("varInt").typeCheck(emptyMemoire(), getMemoire(), Sorte.BOOL));
    }

    @Test
    public void test_typecheck_tableau_missing() {
        Assert.assertFalse(new Tableau("t", new Nbre(0)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.VOID));
    }

    @Test
    public void test_typecheck_tableau() {
        Assert.assertTrue(new Tableau("t", new Nbre(0)).typeCheck(emptyMemoire(), getMemoire(), Sorte.INT));
        Assert.assertTrue(new Tableau("t", new Nbre(0)).typeCheck(getMemoire(), emptyMemoire(), Sorte.VOID));
        Assert.assertFalse(new Tableau("t", new Nbre(0)).typeCheck(emptyMemoire(), getMemoire(), Sorte.BOOL));
    }

    @Test
    public void test_typecheck_tableau_bad_index() {
        Assert.assertFalse(new Tableau("t", new BoolVal(false)).typeCheck(emptyMemoire(), getMemoire(), Sorte.INT));
    }

    @Test
    public void test_typecheck_not() {
        Assert.assertTrue(new Not(new BoolVal(false)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.VOID));
        Assert.assertTrue(new Not(new BoolVal(false)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertFalse(new Not(new BoolVal(false)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.INT));
        Assert.assertFalse(new Not(new Nbre(10)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
    }

    @Test
    public void test_typecheck_neg() {
        Assert.assertTrue(new Negation(new Nbre(10)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.VOID));
        Assert.assertTrue(new Negation(new Nbre(10)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.INT));
        Assert.assertFalse(new Negation(new Nbre(10)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertFalse(new Negation(new BoolVal(false)).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.INT));
    }

    @Test
    public void test_typecheck_omega() {
        Assert.assertTrue(new Omega().typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
    }

    @Test
    public void test_typecheck_binop() {
        ASTExpr e1 = new Nbre(6);
        ASTExpr e2 = new Nbre(8);
        ASTExpr b1 = new BoolVal(false);
        Assert.assertTrue(new Binop(Binop.Operandes.ADDITION, e1, e2).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.INT));
        Assert.assertTrue(new Binop(Binop.Operandes.SOUSTRACTION, e1, e2).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.VOID));
        Assert.assertFalse(new Binop(Binop.Operandes.MULTIPLICATION, e1, e2).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertFalse(new Binop(Binop.Operandes.DIVISION, b1, e2).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertTrue(new Binop(Binop.Operandes.SUPERIEUR, e1, e2).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertTrue(new Binop(Binop.Operandes.AND, b1, b1).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
        Assert.assertTrue(new Binop(Binop.Operandes.EGAL, e1, b1).typeCheck(emptyMemoire(), emptyMemoire(), Sorte.BOOL));
    }

    @Test
    public void test_typecheck_appel() {
        Memoire g = getMemoire();
        ExChain exChain = new ExChain(new Nbre(10), new ExChain(new Nbre(10), new Exnil()));
        Assert.assertFalse(new AppelE(new Identifiant("f"), new Exnil()).typeCheck(g, emptyMemoire(), Sorte.INT));
        Assert.assertFalse(new AppelE(new Identifiant("methVoid"), new Exnil()).typeCheck(g, emptyMemoire(), Sorte.INT));
        Assert.assertFalse(new AppelE(new Identifiant("meth"), exChain).typeCheck(g, emptyMemoire(), Sorte.BOOL));
        Assert.assertFalse(new AppelE(new Identifiant("meth"), new Exnil()).typeCheck(g, emptyMemoire(), Sorte.VOID));
        Assert.assertTrue(new AppelE(new Identifiant("meth"), exChain.getSuccessor()).typeCheck(g, emptyMemoire(), Sorte.INT));
    }

    /* ****************
         Variables
    **************** */

    @Test
    public void test_typecheck_const() {
        Assert.assertTrue(new ASTVarConst(new Entier(), new Identifiant("cst"), new Nbre(3)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new BoolVal(true)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new Nbre(3)).firstCheck(getMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new Omega()).firstCheck(emptyMemoire()));

        Assert.assertTrue(new ASTVarConst(new Entier(), new Identifiant("cst"), new Nbre(3)).typeCheck(getMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new BoolVal(true)).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new Nbre(3)).typeCheck(emptyMemoire(), getMemoire()));
        Assert.assertFalse(new ASTVarConst(new Entier(), new Identifiant("cst"), new Omega()).typeCheck(emptyMemoire(), emptyMemoire()));
    }

    @Test
    public void test_typecheck_var() {
        Assert.assertTrue(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new Nbre(3)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new BoolVal(true)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new Nbre(3)).firstCheck(getMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Void(), new Identifiant("varInt"), new Nbre(3)).firstCheck(emptyMemoire()));

        Assert.assertTrue(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new Nbre(3)).typeCheck(getMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new BoolVal(true)).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Entier(), new Identifiant("varInt"), new Nbre(3)).typeCheck(emptyMemoire(), getMemoire()));
        Assert.assertFalse(new ASTVarSimple(new Void(), new Identifiant("varInt"), new Nbre(3)).typeCheck(emptyMemoire(), emptyMemoire()));
    }

    @Test
    public void test_typecheck_dec_tab() {
        Assert.assertTrue(new ASTVarTableau(new Entier(), new Identifiant("t"), new Nbre(3)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Entier(), new Identifiant("t"), new BoolVal(true)).firstCheck(emptyMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Entier(), new Identifiant("t"), new Nbre(3)).firstCheck(getMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Void(), new Identifiant("t"), new Nbre(3)).firstCheck(emptyMemoire()));

        Assert.assertTrue(new ASTVarTableau(new Entier(), new Identifiant("t"), new Nbre(3)).typeCheck(getMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Entier(), new Identifiant("t"), new BoolVal(true)).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Entier(), new Identifiant("t"), new Nbre(3)).typeCheck(emptyMemoire(), getMemoire()));
        Assert.assertFalse(new ASTVarTableau(new Void(), new Identifiant("t"), new Nbre(3)).typeCheck(emptyMemoire(), emptyMemoire()));
    }

    /* ****************
       Instructions
    **************** */

    @Test
    public void test_typecheck_affectation() {
        Memoire local = getMemoire();
        Assert.assertFalse(new Affectation(new Identifiant("t"), new Nbre(6)).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new Affectation(new Identifiant("t"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Affectation(new Identifiant("cst"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Affectation(new Identifiant("meth"), new Nbre(6)).typeCheck(local, emptyMemoire()));
        Assert.assertTrue(new Affectation(new Identifiant("varInt"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Affectation(new Tableau("varInt", new Nbre(0)), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertTrue(new Affectation(new Tableau("t", new Nbre(0)), new Nbre(6)).typeCheck(emptyMemoire(), local));

    }

    @Test
    public void test_typecheck_appelI() {
        Assert.assertTrue(new AppelI(new Identifiant("methVoid"), new Exnil()).typeCheck(getMemoire(), emptyMemoire()));
        Assert.assertFalse(new AppelI(new Identifiant("methVoid"), new Exnil()).typeCheck(emptyMemoire(), emptyMemoire()));
    }

    @Test
    public void test_typecheck_ecrire() {
        Assert.assertTrue(new Ecrire(new HardcodedString("tot")).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertTrue(new Ecrire(new Identifiant("cst")).typeCheck(emptyMemoire(), getMemoire()));
        Assert.assertTrue(new Ecrire(new Identifiant("cst")).typeCheck(getMemoire(), emptyMemoire()));
        Assert.assertFalse(new Ecrire(new Identifiant("cst")).typeCheck(emptyMemoire(), emptyMemoire()));
    }


    @Test
    public void test_typecheck_increment() {
        Memoire local = getMemoire();
        Assert.assertFalse(new Increment(new Identifiant("t")).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new Increment(new Identifiant("t")).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Increment(new Identifiant("cst")).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Increment(new Identifiant("meth")).typeCheck(local, emptyMemoire()));
        Assert.assertTrue(new Increment(new Identifiant("varInt")).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Increment(new Identifiant("varBool")).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Increment(new Tableau("varInt", new Nbre(0))).typeCheck(emptyMemoire(), local));
        Assert.assertTrue(new Increment(new Tableau("t", new Nbre(0))).typeCheck(emptyMemoire(), local));
    }

    @Test
    public void test_typecheck_retour() {
        Retour r = new Retour(new Nbre(0));
        Assert.assertFalse(r.typeCheck(emptyMemoire(), emptyMemoire()));

        r.forwardTypeRetour(Sorte.INT);
        Assert.assertTrue(r.typeCheck(emptyMemoire(), emptyMemoire()));
    }

    @Test
    public void test_typecheck_si() {
        Assert.assertTrue(new Si(new BoolVal(false), new Inil(), new Inil()).typeCheck(emptyMemoire(), emptyMemoire()));
    }

    @Test
    public void test_typecheck_somme() {
        Memoire local = getMemoire();
        Assert.assertFalse(new Somme(new Identifiant("t"), new Nbre(6)).typeCheck(emptyMemoire(), emptyMemoire()));
        Assert.assertFalse(new Somme(new Identifiant("t"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Somme(new Identifiant("cst"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Somme(new Identifiant("meth"), new Nbre(6)).typeCheck(local, emptyMemoire()));
        Assert.assertTrue(new Somme(new Identifiant("varInt"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Somme(new Identifiant("varBool"), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Somme(new Tableau("varInt", new Nbre(0)), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertTrue(new Somme(new Tableau("t", new Nbre(0)), new Nbre(6)).typeCheck(emptyMemoire(), local));
        Assert.assertFalse(new Somme(new Tableau("tBool", new Nbre(0)), new Nbre(6)).typeCheck(emptyMemoire(), local));
    }

    @Test
    public void test_typecheck_while() {
        Assert.assertTrue(new TantQue(new BoolVal(true), new Inil()).typeCheck(emptyMemoire(), emptyMemoire()));
    }



    /* ****************
          Classe
    **************** */

    @Test
    public void test_typecheck_global() {
        ASTMethode methode = new ASTMethode(new Entier(), new Identifiant("meth"), new Enil(), new Vnil(),
                new IChain(new Retour(new Nbre(10)), new Inil()));
        ASTMain main = new ASTMain(new VChain(new ASTVarSimple(new Entier(), new Identifiant("toto"), new Omega()), new Vnil()),
                new IChain(new Affectation(new Identifiant("toto"), new AppelE(new Identifiant("meth"), new Exnil())), new Inil()));
        ASTClass cl = new ASTClass(new Identifiant("C"),
                new DChain(new DChain(new Dnil(), new ASTVarConst(new Booleen(), new Identifiant("temp"), new BoolVal(true))), methode),
                main);
        Assert.assertTrue(cl.typeCheck());
    }

}
