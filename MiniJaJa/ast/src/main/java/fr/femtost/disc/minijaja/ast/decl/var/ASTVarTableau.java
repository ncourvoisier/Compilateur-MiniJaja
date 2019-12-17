package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.NewArray;

public class ASTVarTableau extends ASTVar {

    private ASTTypeMeth type;

    public ASTVarTableau(ASTTypeMeth type, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.type = type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.rewrite()).append(" ").append(this.identifiant.rewrite());
        sb.append("[").append(this.expr.rewrite()).append("]");
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new NewArray(identifiant.getName(), type.getSorte())), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        int v = (int)expr.eval(m);
        m.getPile().DeclTab(identifiant.getName(),v, type.getSorte());
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
        if (expr.typeCheck(global, local, Sorte.INT)) {
            if (global.containsSymbol(identifiant.getName())) {
                ASTLogger.getInstance().logWarning(this, "Local variable shadowing global: " + identifiant.getName());
            }
            local.getPile().DeclTab(identifiant.getName(), null, type.getSorte());
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
        if (expr.typeCheck(global, new Memoire(128), Sorte.INT)) {
            global.getPile().DeclTab(identifiant.getName(), null, type.getSorte());
            return true;
        }
        return false;
    }

}
