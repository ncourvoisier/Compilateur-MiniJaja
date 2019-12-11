package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCode;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.instrs.IChain;
import fr.femtost.disc.minijaja.ast.instrs.Inil;
import fr.femtost.disc.minijaja.jcode.Goto;
import fr.femtost.disc.minijaja.jcode.If;

public class Si extends ASTInstr {

    private ASTExpr expr;
    private ASTInstrs instrsIf;
    private ASTInstrs instrsElse;

    public Si(ASTExpr expr, ASTInstrs instrsIf) {
        this.expr = expr;
        this.instrsIf = instrsIf;
        this.instrsElse = new Inil();
    }

    public Si(ASTExpr expr, ASTInstrs instrsIf, ASTInstrs instrsElse) {
        this.expr = expr;
        this.instrsIf = instrsIf;
        this.instrsElse = instrsElse;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("if");
        sb.append(expr.rewrite()).append(" {\n");
        sb.append(instrsIf.rewrite()).append("}");
        if (instrsElse instanceof IChain) {
            sb.append(" else {\n");
            sb.append(instrsElse.rewrite());
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        CompilationCouple stElse = instrsElse.compiler(actual + e.taille + 1);
        CompilationCouple stThen = instrsIf.compiler(actual + e.taille + stElse.taille + 2);

        JCodes s1 = JCodes.concatLeft(new Goto(actual + e.taille + stElse.taille + stThen.taille + 2), stThen.jCodes);
        JCodes s2 = JCodes.concatenate(stElse.jCodes, s1);
        JCodes s3 = JCodes.concatLeft(new If(actual + e.taille + stElse.taille + 2), s2);

        return new CompilationCouple(JCodes.concatenate(e.jCodes, s3), e.taille + stElse.taille + stThen.taille + 2);
    }

    @Override
    public void interpreter(Memoire m) {
        Boolean ee = (Boolean)expr.eval(m);
        if (ee) {
            instrsIf.interpreter(m);
        } else {
            instrsElse.interpreter(m);
        }
    }

    @Override
    public void retirer(Memoire m) {

    }

    @Override
    public void typeCheck(Memoire m) {

    }
}
