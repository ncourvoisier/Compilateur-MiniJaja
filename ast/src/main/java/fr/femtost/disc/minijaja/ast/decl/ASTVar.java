package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.PileException;
import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcode.Swap;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

public abstract class ASTVar extends ASTDecl {

    protected Identifiant identifiant;
    protected ASTExpr expr;

    public ASTVar(Identifiant identifiant, ASTExpr expr) {
        this.identifiant = identifiant;
        this.expr = expr;
    }

    @Override
    public CompilationCouple retirerCompile(int actual) {
        return new CompilationCouple(new JChain(new Swap(), new JChain(new Pop(), new JNil())), 2);
    }


    @Override
    public void retirer(Memoire m) {
        try {
            m.getPile().RetirerDecl(identifiant.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.getMessage());
        }
    }
}
