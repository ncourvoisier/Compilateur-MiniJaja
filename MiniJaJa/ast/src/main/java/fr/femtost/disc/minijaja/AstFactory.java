package fr.femtost.disc.minijaja;


import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTDecls;
import fr.femtost.disc.minijaja.ast.ASTMain;

import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;

public class AstFactory {
    public static ASTClass generateClass(Identifiant ident, ASTDecls decls, ASTMain methmain){
        return new ASTClass(ident, decls, methmain);
    }/*
    public static AstTree generateIdent(String identificateur){

        return null;
    }
    public static AstTree generateDecls(AstTree decl, AstTree decls){

        return null;
    }
    public static AstTree generateDecl(){

        return null;
    }
    public static AstTree generateVars(AstTree Var, AstTree Vars){

        return null;
    }
    public static AstTree generateVar(AstTree typemeth, AstTree ident, AstTree vexp){

        return null;
    }
    public static AstTree generateTableau(AstTree typemeth, AstTree ident, AstTree exp){

        return null;
    }
    public static AstTree generateCst( AstTree type, AstTree ident, AstTree vexp){

        return null;
    }
    public static AstTree generateVexp(AstTree exp){

        return null;
    }
    public static AstTree generateMethode(AstTree typemeth, AstTree ident, AstTree entetes, AstTree vars, AstTree instrs){

        return null;
    }
    public static AstTree generateMethmain(String main, AstTree vars, AstTree instrs){

        return null;
    }
    public static AstTree generateEntetes(AstTree entete, AstTree entetes){

        return null;
    }
    public static AstTree generateEntete(AstTree type, AstTree ident){

        return null;
    }
    public static AstTree generateInstrs(AstTree instr, AstTree instrs ){

        return null;
    }
    public static AstTree generateAffectation(AstTree ident1, AstTree exp){

        return null;
    }
    public static AstTree generateSomme(AstTree ident1, AstTree exp){

        return null;
    }
    public static AstTree generateIncrement(AstTree ident1){

        return null;
    }
    public static AstTree generateAppelI(AstTree ident, AstTree listexp){

        return null;
    }

    public static AstTree generateRetour(AstTree exp){

        return null;
    }
    public static AstTree generateEcrire(AstTree ident){

        return null;
    }
    public static AstTree generateEcrireln(AstTree ident){

        return null;
    }
    public static AstTree generateChaine(String chaine){

        return null;
    }
    public static AstTree generateSi(AstTree exp, AstTree instrs, AstTree instrs2){

        return null;
    }
    public static AstTree generateTantque(AstTree exp, AstTree instrs, AstTree instrs2){

        return null;
    }
    public static AstTree generateListexp(AstTree exp, AstTree listexp){

        return null;
    }
    public static AstTree generateNon(AstTree exp){

        return null;
    }
    public static AstTree generateListEt(AstTree exp, AstTree exp1){

        return null;
    }
    public static AstTree generateOu(AstTree exp, AstTree exp1){

        return null;
    }

    public static AstTree generateSuperieur(AstTree exp1, AstTree exp2){

        return null;
    }
    public static AstTree generateAddition(AstTree exp2, AstTree terme){

        return null;
    }
    public static AstTree generateSoustraction(AstTree exp2, AstTree terme){

        return null;
    }
    public static AstTree generateEgalite(AstTree exp2, AstTree terme){

        return null;
    }
    public static AstTree generateMultiplication(AstTree terme, AstTree fact){

        return null;
    }

    public static AstTree generateDivision(AstTree terme, AstTree fact){

        return null;
    }
    public static AstTree generateFact(AstTree ident1, AstTree listexp, AstTree exp){

        return null;
    }
    public static AstTree generateAppelE(AstTree ident, AstTree listexp){

        return null;
    }
    public static AstTree generateFact(){

        return null;
    }
    public static AstTree generateVrai(AstTree vrai){

        return null;
    }
    public static AstTree generateFaux(AstTree faux){

        return null;
    }
    public static AstTree generateNbre(AstTree nombre){

        return null;
    }

    public static AstTree generateIdent1(AstTree ident, AstTree exp){

        return null;
    }
    public static AstTree generateTab(AstTree ident, AstTree exp){

        return null;
    }
    public static AstTree generateTypemeth(){

        return null;
    }
    public static AstTree generateType(AstTree entier){

        return null;
    }
    public static AstTree generateIdent1(AstTree bool){

        return null;
    }

*/
}
