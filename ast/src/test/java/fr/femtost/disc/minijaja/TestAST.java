package fr.femtost.disc.minijaja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarConst;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarSimple;
import fr.femtost.disc.minijaja.ast.decls.DChain;
import fr.femtost.disc.minijaja.ast.decls.Dnil;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.ast.expr.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.Affectation;
import fr.femtost.disc.minijaja.ast.instrs.IChain;
import fr.femtost.disc.minijaja.ast.instrs.Inil;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.ast.type.Booleen;
import fr.femtost.disc.minijaja.ast.type.Entier;
import fr.femtost.disc.minijaja.ast.vars.VChain;
import fr.femtost.disc.minijaja.ast.vars.Vnil;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestAST
{

    private static ASTExpr generateExpr() {
        return new Addition(new Identifiant("x"), new Nbre(8));
    }

    private static ASTInstr generateInstr() {
        return new Affectation(new Identifiant("max"), generateExpr());
    }

    private static ASTInstrs genChainInstrs() {
        ASTInstrs result = new IChain(new Inil(), generateInstr());
        return new IChain(result, generateInstr());
    }

    private static ASTMain genSimpleMain() {
        return new ASTMain(new Vnil(), genChainInstrs());
    }

    private static ASTEntetes genEntetes() {
        return new EChain(new EChain(new Enil(), new ASTEntete(new Identifiant("roger"), new Entier())), new ASTEntete(new Identifiant("max"), new Entier()));
    }

    private static ASTClass genClass(){
        return new ASTClass(new Identifiant("C"), genDecls(), genSimpleMain());
    }

    private static ASTVar generateVar(){
        return new ASTVarConst(new Identifiant("variableC"), new BoolVal(true), new Booleen());
    }

    private static ASTVars genChainVars() {
        ASTVars result = new VChain(generateVar(), new Vnil());
        return new VChain(new ASTVarSimple(new Identifiant("s"), new Entier()), result);
    }

    private static ASTMethode genMethode() {
        return new ASTMethode(genChainVars(), new Entier(), new Identifiant("f"), genEntetes(), genChainInstrs());
    }

    private static ASTDecls genDecls() {
        return new DChain(new DChain(new Dnil(), genMethode()), generateVar());
    }



    @Test
    public void TestMainAST()
    {
        ASTClass astClass = genClass();
        System.out.println(astClass.rewrite());
        assertTrue(true);
    }

    @Test
    public void ASTGetterClass() {
        ASTClass c = genClass();
        assertEquals("C", c.getIdent().getName());
        assertTrue(c.getDecls() instanceof DChain);
        assertTrue(c.getMain().getInstrs() instanceof IChain);
    }
}
