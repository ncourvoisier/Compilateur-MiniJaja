package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTTypeMeth;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.Omega;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.New;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class ASTVarSimple extends ASTVar {

    private ASTTypeMeth typeMeth;

    public ASTVarSimple(ASTTypeMeth typeMeth, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.typeMeth = typeMeth;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(typeMeth.rewrite()).append(" ").append(this.identifiant.rewrite());
        if(!(this.expr instanceof Omega)) {
            sb.append(" = ").append(this.expr.rewrite());
        }
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new New(new JCIdent(identifiant.getName()), typeMeth.getType(), JCSorte.VARIABLE, new JCNbre(0))), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        m.getPile().DeclVar(identifiant.getName(),v,typeMeth.getSorte());
    }

    @Override
    public void typeCheck(Memoire m) {
        Object v = expr.eval(m);

        if (m.getPile().getTds().chercheQuad(identifiant.getName(),typeMeth.getSorte()) == null) {
            m.getPile().DeclVar(identifiant.getName(), v, typeMeth.getSorte());
        } else {
            System.out.println("Error " + identifiant.getName() + " already declared");
        }

        if (typeMeth.getSorte() == Sorte.VOID){
            System.out.println("Error " + identifiant.getName() + " void isn't type");
        }
    }


}
