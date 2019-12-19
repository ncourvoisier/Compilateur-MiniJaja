package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.Omega;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.jcode.New;


public class ASTVarConst extends ASTVar {

    private ASTType type;

    public ASTVarConst(ASTType type, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.type = type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("final ");
        sb.append(type.rewrite()).append(" ").append(this.identifiant.rewrite());
        if(!(expr instanceof Omega) ) {
            sb.append(" = ").append(this.expr.rewrite());
        }
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new New(identifiant.getName(), type.getSorte(), JCSorte.CONSTANTE, 0)), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        m.getPile().declCst(identifiant.getName(),v,type.getSorte());
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        if (local.containsSymbol(identifiant.getName())) {
            ASTLogger.getInstance().logError(this, "Variable déjà définie " + identifiant.getName());
            return false;
        }
        if (expr instanceof Omega) {
            ASTLogger.getInstance().logError(this, "Variable constante non-initialisée " + identifiant.getName());
            return false;
        }
        local.getPile().declCst(identifiant.getName(), null, type.getSorte());
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
        if (global.containsSymbol(identifiant.getName())) {
            ASTLogger.getInstance().logError(this, "Variable déjà définie " + identifiant.getName());
            return false;
        }
        if (expr instanceof Omega) {
            ASTLogger.getInstance().logError(this, "Variable constante non-initialisée " + identifiant.getName());
            return false;
        }
        global.getPile().declCst(identifiant.getName(), null, type.getSorte());
        return expr.typeCheck(global, new Memoire(128), type.getSorte());
    }
}
