package fr.femtost.disc.minijaja.ast.decl.var;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTVar;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.jcode.New;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class ASTVarConst extends ASTVar {

    private ASTType type;

    public ASTVarConst(ASTType type, Identifiant identifiant) {
        super(identifiant);
        this.type = type;
    }

    public ASTVarConst(ASTType type, Identifiant identifiant, ASTExpr expr) {
        super(identifiant, expr);
        this.type = type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("final ");
        sb.append(type.rewrite()).append(" ").append(this.identifiant.rewrite());
        if(this.expr != null)
            sb.append(" = ").append(this.expr.rewrite());
        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e = expr.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(e.jCodes, new New(new JCIdent(identifiant.getName()), type.getType(), JCSorte.CONSTANTE, new JCNbre(0))), e.taille+1);
    }

    @Override
    public void interpreter(Memoire m) {
        Object v = expr.eval(m);
        m.getPile().DeclCst(identifiant.getName(),v,type.getSorte());
    }

    @Override
    public void retirer(Memoire m) {
        try {
            m.getPile().RetirerDecl(identifiant.getName());
        } catch (PileException e) {
            e.printStackTrace();
        }
    }

}