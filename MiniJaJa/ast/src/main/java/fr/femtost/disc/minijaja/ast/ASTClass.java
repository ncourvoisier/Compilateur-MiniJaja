package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.jcode.Init;
import fr.femtost.disc.minijaja.jcode.JCStop;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public class ASTClass extends ASTNode
{

    private Identifiant ident;
    private ASTDecls decls;
    private ASTMain main;
    private static String variableClasse;

    public ASTClass(Identifiant ident, ASTDecls decls, ASTMain main) {
        this.ident = ident;
        this.decls = decls;
        this.main = main;
        variableClasse = ident.getName();
    }

    public static String getVariableClass() {
        return variableClasse;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ");
        sb.append(ident.rewrite());
        sb.append(" {\n");
        sb.append(decls.rewrite()).append("\n");
        sb.append(main.rewrite()).append("\n");
        sb.append("}\n");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple dss  = decls.compiler(actual+1);
        CompilationCouple mma = main.compiler(actual + dss.taille + 1);
        CompilationCouple retrait = decls.retirerCompile(actual + dss.taille + mma.taille + 1);

        JCodes instructions = JCodes.concatLeft(new Init(), JCodes.concatenate(dss.jCodes, JCodes.concatenate(mma.jCodes, retrait.jCodes)));
        return new CompilationCouple(JCodes.concatenate(instructions, new JChain(new Pop(), new JChain(new JCStop(), new JNil()))),
                dss.taille + mma.taille + retrait.taille + 3);
    }

    @Override
    public void interpreter(Memoire m) {
        m.getPile().declVar(ident.getName(),null,null);
        decls.interpreter(m);
        main.interpreter(m);
        decls.retirer(m);
        try {
            m.getPile().retirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,"Var not found for removal: " + ident.getName());
        } catch (Exception e) {
            ASTLogger.getInstance().logError(this,"Exception: " + ident.getName());
        }
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        switch (l.get(0).indice)
        {
            case 1:
                l.get(0).indice = 2;
                m.getPile().declVar(ident.getName(),null,null);
                break;

            case 2:
                l.get(0).indice = 3;
                l.add(0, new InterpretationPasAPasCouple(decls,1));
                decls.interpreterPasAPas(m,l, leval);
                break;

            case 3:
                l.get(0).indice = 4;
                l.add(0, new InterpretationPasAPasCouple(main,1));
                main.interpreterPasAPas(m,l, leval);
                break;

            case 4:
                l.get(0).indice = 5;
                decls.retirer(m);
                try {
                    m.getPile().retirerDecl(ident.getName());
                } catch (PileException e) {
                    ASTLogger.getInstance().logError(this,"Var not found for removal: " + ident.getName());
                } catch (Exception e) {
                    ASTLogger.getInstance().logError(this,"Exception: " + ident.getName());
                }
                break;

            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);;
        }
    }

    @Override
    public int getMaxEtape() {
        return 4;
    }

    public boolean typeCheck() {
        Memoire global = new Memoire(128);
        global.getPile().declVar(ident.getName(),null,null);
        if (decls.firstCheck(global)) {
            boolean b1 = decls.typeCheck(global);
            boolean b2 = main.typeCheck(global);
            return b1 && b2;
        }
        return false;
    }

}
