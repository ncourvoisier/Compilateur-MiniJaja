package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.NewArray;

public class ASTVarTableau extends ASTVar {

    private ASTTypeMeth typeMeth;

    public ASTVarTableau(ASTTypeMeth typeMeth, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.typeMeth = typeMeth;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(typeMeth.rewrite()).append(" ").append(this.identifiant.rewrite());
        sb.append("[").append(this.expr.rewrite()).append("]");
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new NewArray(new JCIdent(identifiant.getName()), typeMeth.getType())), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        int v = (int)expr.eval(m);
        m.getPile().DeclTab(identifiant.getName(),v,typeMeth.getSorte());
    }

    @Override
    public void typeCheck(Memoire m) {
        Object v = expr.eval(m);

        if (m.getPile().getTds().chercheQuad(identifiant.getName(),typeMeth.getSorte()) == null){
            m.getPile().DeclTab(identifiant.getName(), v, typeMeth.getSorte());
        } else {
            ASTLogger.getInstance().logWarning("Error " + identifiant.getName() + " already declared");
        }

        if (typeMeth.getSorte() == Sorte.VOID){
            ASTLogger.getInstance().logError("Error " + identifiant.getName() + " void isn't type");
        }
    }

}
