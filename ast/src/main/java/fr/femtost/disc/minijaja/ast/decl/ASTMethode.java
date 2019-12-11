package fr.femtost.disc.minijaja.ast.decl;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.*;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class ASTMethode extends ASTDecl {

    private ASTVars vars;
    private ASTTypeMeth typeMeth;
    private Identifiant ident;
    private ASTEntetes entetes;
    private ASTInstrs instrs;

    public ASTMethode(ASTTypeMeth typeMeth, Identifiant ident, ASTEntetes entetes, ASTVars vars, ASTInstrs instrs) {
        this.vars = vars;
        this.typeMeth = typeMeth;
        this.ident = ident;
        this.entetes = entetes;
        this.instrs = instrs;
    }

    public ASTVars getVars() {
        return vars;
    }

    public ASTEntetes getEntetes() {
        return entetes;
    }

    public ASTInstrs getInstrs() {
        return instrs;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(typeMeth.rewrite()).append((" "));
        sb.append(ident.rewrite());
        sb.append("(").append(entetes.rewrite()).append(") ");
        sb.append("{\n");
        sb.append(vars.rewrite());
        sb.append(instrs.rewrite());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple ens = entetes.compiler(actual + 3);
        CompilationCouple dvs = vars.compiler(actual + ens.taille + 3);
        CompilationCouple iss = instrs.compiler(actual + ens.taille + dvs.taille + 3);
        CompilationCouple retrait = vars.retirerCompile(actual + ens.taille + dvs.taille + iss.taille + 3);

        int totalSize = typeMeth.getType() == JCType.VOID ? 6 : 5;

        JCodes init0 = JCodes.concatRight(new JNil(), new Push(new JCNbre(actual+3)));
        JCodes init1 = JCodes.concatRight(init0, new New(new JCIdent(ident.getName()), typeMeth.getType(), JCSorte.METHODE, new JCNbre(0)));
        JCodes init2 = JCodes.concatRight(init1, new Goto(actual + ens.taille + dvs.taille + iss.taille + retrait.taille + totalSize));
        JCodes init3;
        if (typeMeth.getType() == JCType.VOID) {
            init3 = JCodes.concatenate(init2, JCodes.concatenate(ens.jCodes, JCodes.concatenate(dvs.jCodes, JCodes.concatenate(iss.jCodes,
                    JCodes.concatenate(JCodes.concatLeft(new Push(new JCNbre(0)), retrait.jCodes), new JChain(new Swap(),
                            new JChain(new Return(), new JNil())))))));
        } else {
            init3 = JCodes.concatenate(init2, JCodes.concatenate(ens.jCodes, JCodes.concatenate(dvs.jCodes, JCodes.concatenate(iss.jCodes,
                    JCodes.concatenate(retrait.jCodes, new JChain(new Swap(), new JChain(new Return(), new JNil())))))));
        }

        return new CompilationCouple(init3, ens.taille + dvs.taille + iss.taille + retrait.taille + totalSize);
    }

    @Override
    public CompilationCouple retirerCompile(int actual) {
        return new CompilationCouple(new JChain(new Swap(), new JChain(new Pop(), new JNil())), 2);
    }

    @Override
    public void interpreter(Memoire m) {
        ASTMethode mth = new ASTMethode(typeMeth, ident, entetes, vars, instrs);
        m.getPile().DeclMeth(ident.getName(), mth, null);
    }

    @Override
    public void retirer(Memoire m) {
        try {
            m.getPile().RetirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError("Impossible de retirer la méthode " + ident.getName());
        }
    }

    @Override
    public void typeCheck(Memoire m) {
        ASTMethode mth = new ASTMethode(typeMeth, ident, entetes, vars, instrs);
        if (m.getPile().getTds().chercheQuad(ident.getName(),typeMeth.getSorte()) == null){
            m.getPile().DeclMeth(ident.getName(), mth, typeMeth.getSorte());
        } else {
            System.out.println("Error " + ident.getName() + " already declared");
        }
    }

}
