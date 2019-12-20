package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.Omega;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.New;

import java.util.List;

public class ASTVarSimple extends ASTVar {

    private ASTTypeMeth type;

    public ASTVarSimple(ASTTypeMeth type, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.type = type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.rewrite()).append(" ").append(this.identifiant.rewrite());
        if(!(this.expr instanceof Omega)) {
            sb.append(" = ").append(this.expr.rewrite());
        }
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new New(identifiant.getName(), type.getSorte(), JCSorte.VARIABLE, 0)), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        m.getPile().declVar(identifiant.getName(),v, type.getSorte());
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        if (l.get(0).indice < 4) {
            if (helperPas(m, l, calls, expr.getAllCalls())) {
                l.get(0).indice = 4;
                Object v = expr.tryEval(m, calls);
                m.getPile().declVar(identifiant.getName(),v, type.getSorte());
                if (!expr.getAllCalls().isEmpty()) {
                    cleanEvals(calls);
                }
            }
        }
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        if (type.getSorte() == Sorte.VOID) {
            ASTLogger.getInstance().logError(this, "Type invalide : void");
            return false;
        }
        if (local.containsSymbol(identifiant.getName())) {
            ASTLogger.getInstance().logError(this, "Variable déjà définie " + identifiant.getName());
            return false;
        }
        local.getPile().declVar(identifiant.getName(), null, type.getSorte());
        if (expr.typeCheck(global, local, type.getSorte())) {
            if (global.containsSymbol(identifiant.getName())) {
                ASTLogger.getInstance().logWarning(this, "Local variable shadowing global: " + identifiant.getName());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean firstCheck(Memoire global) {
        if (type.getSorte() == Sorte.VOID) {
            ASTLogger.getInstance().logError(this, "Type invalide : void");
            return false;
        }
        if (global.containsSymbol(identifiant.getName())) {
            ASTLogger.getInstance().logError(this, "Variable déjà définie " + identifiant.getName());
            return false;
        }
        global.getPile().declVar(identifiant.getName(), null, type.getSorte());
        return expr.typeCheck(global, new Memoire(128), type.getSorte());
    }

}
