package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Init;
import fr.femtost.disc.minijaja.jcode.JCStop;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class ASTClass extends ASTNode
{

    private Identifiant ident;
    private ASTDecls decls;
    private ASTMain main;
    private static String variableClasse;

    public static String getVariableClass() {
        return variableClasse;
    }

    public ASTClass(Identifiant ident, ASTDecls decls, ASTMain main) {
        this.ident = ident;
        this.decls = decls;
        this.main = main;
        variableClasse = ident.getName();
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ");
        sb.append(ident.rewrite());
        sb.append(" {\n");
        sb.append(decls.rewrite()).append("\n");
        sb.append(main.rewrite()).append("\n");
        sb.append("}\n");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple dss  = decls.compiler(actual+1);
        CompilationCouple mma = main.compiler(actual + dss.taille + 1);
        CompilationCouple retrait = decls.retirerCompile(actual + dss.taille + mma.taille + 1);

        JCodes instructions = JCodes.concatLeft(new Init(), JCodes.concatenate(dss.jCodes, JCodes.concatenate(mma.jCodes, retrait.jCodes)));
        return new CompilationCouple(JCodes.concatenate(instructions, new JChain(new Pop(), new JChain(new JCStop(), new JNil()))),
                dss.taille + mma.taille + retrait.taille + 3);
    }

    @Override
    public void interpreter(Memoire m) {
        m.getPile().DeclVar(ident.getName(),null,null);
        decls.interpreter(m);
        main.interpreter(m);
        decls.retirer(m);
        try {
            m.getPile().RetirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,"Var not found for removal: " + ident.getName());
        }
    }

    public boolean typeCheck() {
        Memoire global = new Memoire(128);
        if (decls.firstCheck(global)) {
            boolean b1 = decls.typeCheck(global);
            boolean b2 = main.typeCheck(global);
            return b1 && b2;
        }
        return false;
    }

}
