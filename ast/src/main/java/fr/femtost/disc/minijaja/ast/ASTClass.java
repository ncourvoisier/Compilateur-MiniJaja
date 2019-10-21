package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Init;
import fr.femtost.disc.minijaja.jcode.JCStop;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public class ASTClass extends ASTNode {

    private Identifiant ident;
    private ASTDecls decls;
    private ASTMain main;

    public ASTClass(Identifiant ident, ASTDecls decls, ASTMain main) {
        this.ident = ident;
        this.decls = decls;
        this.main = main;
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
}
