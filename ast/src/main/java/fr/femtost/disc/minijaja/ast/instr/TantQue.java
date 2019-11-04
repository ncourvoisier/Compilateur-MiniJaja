package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;
import fr.femtost.disc.minijaja.jcode.Goto;
import fr.femtost.disc.minijaja.jcode.If;
import fr.femtost.disc.minijaja.jcode.oper.OpUnaire;

public class TantQue extends ASTInstr {

    ASTInstrs instrs;
    ASTExpr expr;

    public TantQue(ASTExpr expr, ASTInstrs instrs) {
        this.instrs = instrs;
        this.expr = expr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("while");
        sb.append("(").append(expr.rewrite()).append(")");
        sb.append(" {\n");
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        CompilationCouple iss = instrs.compiler(actual + e.taille + 2);

        JCodes builder = JCodes.concatLeft(new OpUnaire(OpUnaire.Operandes.NOT), JCodes.concatLeft(new If(actual + e.taille + iss.taille + 3),
                JCodes.concatRight(iss.jCodes, new Goto(actual))));
        return new CompilationCouple(JCodes.concatenate(e.jCodes, builder), e.taille + iss.taille + 3);
    }

    public void interpreter(Memoire m) {
        Boolean ee = (Boolean)expr.eval(m);
        if (ee) {
            instrs.interpreter(m);
            this.interpreter(m);
        }
    }

    @Override
    public void retirer(Memoire m) {

    }


}
