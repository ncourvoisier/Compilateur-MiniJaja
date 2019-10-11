package fr.femtost.disc.minijaja;

import static org.junit.Assert.assertTrue;

import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarConst;
import fr.femtost.disc.minijaja.ast.decl.var.ASTVarSimple;
import fr.femtost.disc.minijaja.ast.decls.DChain;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.Nbre;
import fr.femtost.disc.minijaja.ast.expr.Soustraction;
import fr.femtost.disc.minijaja.ast.expr.identificateur.HardcodedString;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.instr.Affectation;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.ast.type.Entier;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestAST
{
    @Test
    public void TestMainAST()
    {

        Identifiant identificateur = new Identifiant("C");
        //ASTClass astClass = new ASTClass(new Identifiant("C"),new DChain(null,new ASTVarSimple(new Identifiant("y"),new Entier())),new ASTMain(new Affectation(new Identifiant("y"),new Nbre(4))));
    }
}
