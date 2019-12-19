package fr.femtost.disc.minijaja.ast.listexpr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public final class Exnil extends ASTListExpr {

    @Override
    public String rewrite() {
        return "";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JNil(), 0);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, ASTEntetes entetes) {
        if (entetes instanceof Enil) {
            return true;
        } else {
            ASTLogger.getInstance().logError(this, "Manque paramètres pour appel de fonction");
            return false;
        }
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 0;
    }
}
