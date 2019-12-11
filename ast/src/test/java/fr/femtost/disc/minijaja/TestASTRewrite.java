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
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
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

public class TestASTRewrite {

    /* ****************
        Expressions
    **************** */

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



    /* ****************
          Entetes
    **************** */

    @Test
    public void test_rewrite_type() {
        Assert.assertEquals("void", new Void().rewrite());
        Assert.assertEquals("int", new Entier().rewrite());
        Assert.assertEquals("boolean", new Booleen().rewrite());
    }

    @Test
    public void test_rewrite_entete() {
        Assert.assertEquals("int nombre", new ASTEntete(new Identifiant("nombre"), new Entier()).rewrite());
    }

    @Test
    public void test_rewrite_entetes() {
        ASTEntete entete = new ASTEntete(new Identifiant("nombre"), new Entier());
        Assert.assertEquals("int nombre", new EChain(new Enil(), entete).rewrite());
        Assert.assertEquals("int nombre, int nombre", new EChain(new EChain(new Enil(), entete), entete).rewrite());
    }



    /* ****************
         Variables
    **************** */

    @Test
    public void test_rewrite_const() {
        Assert.assertEquals("final int nb", new ASTVarConst(new Entier(), new Identifiant("nb"), new Omega()).rewrite());
        Assert.assertEquals("final int nb = 6", new ASTVarConst(new Entier(), new Identifiant("nb"), new Nbre(6)).rewrite());
    }

    @Test
    public void test_rewrite_var() {
        Assert.assertEquals("int nb", new ASTVarSimple(new Entier(), new Identifiant("nb"), new Omega()).rewrite());
        Assert.assertEquals("int nb = 6", new ASTVarSimple(new Entier(), new Identifiant("nb"), new Nbre(6)).rewrite());
    }

    @Test
    public void test_rewrite_dec_tab() {
        Assert.assertEquals("int nb[6]", new ASTVarTableau(new Entier(), new Identifiant("nb"), new Nbre(6)).rewrite());
    }

    @Test
    public void test_rewrite_varChain() {
        ASTVar var = new ASTVarSimple(new Entier(), new Identifiant("nb"), new Nbre(6));
        Assert.assertEquals("int nb = 6;\n", new VChain(var, new Vnil()).rewrite());
    }



    /* ****************
       Instructions
    **************** */

    @Test
    public void test_rewrite_affectation() {
        Assert.assertEquals("x=6", new Affectation(new Identifiant("x"), new Nbre(6)).rewrite());
    }

    @Test
    public void test_rewrite_appelI() {
        Assert.assertEquals("meth()", new AppelI(new Identifiant("meth"), new Exnil()).rewrite());
    }

    @Test
    public void test_rewrite_ecrire() {
        Assert.assertEquals("write(v)", new Ecrire(new Identifiant("v")).rewrite());
    }

    @Test
    public void test_rewrite_ecrireln() {
        Assert.assertEquals("writeln(v)", new EcrireLn(new Identifiant("v")).rewrite());
    }

    @Test
    public void test_rewrite_increment() {
        Assert.assertEquals("x++", new Increment(new Identifiant("x")).rewrite());
    }

    @Test
    public void test_rewrite_retour() {
        Assert.assertEquals("return x", new Retour(new Identifiant("x")).rewrite());
    }

    @Test
    public void test_rewrite_si() {
        ASTInstr instr = new Increment(new Identifiant("x"));
        Assert.assertEquals("if (true) {\nx++;\n} else {\nx++;\n}", new Si(new BoolVal(true), new IChain(instr, new Inil()), new IChain(instr, new Inil())).rewrite());
    }

    @Test
    public void test_rewrite_somme() {
        Assert.assertEquals("x+=y", new Somme(new Identifiant("x"), new Identifiant("y")).rewrite());
    }

    @Test
    public void test_rewrite_while() {
        Assert.assertEquals("while(true) {\nx++;\n}", new TantQue(new BoolVal(true), new IChain(new Increment(new Identifiant("x")), new Inil())).rewrite());
    }



    /* ****************
          Classe
    **************** */

    @Test
    public void test_rewrite_main() {
        Assert.assertEquals("main {\n}", new ASTMain(new Vnil(), new Inil()).rewrite());
    }

    @Test
    public void test_rewrite_methode() {
        Assert.assertEquals("\nvoid meth() {\n}", new ASTMethode(new Void(), new Identifiant("meth"), new Enil(), new Vnil(), new Inil()).rewrite());
    }

    @Test
    public void test_rewrite_class() {
        Assert.assertEquals("class b {\nint x;\n\nmain {\n}\n}\n", new ASTClass(new Identifiant("b"), new DChain(new Dnil(), new ASTVarSimple(new Entier(), new Identifiant("x"), new Omega())), new ASTMain(new Vnil(), new Inil())).rewrite());
    }
}