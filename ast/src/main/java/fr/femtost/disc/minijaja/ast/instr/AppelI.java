package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;
import fr.femtost.disc.minijaja.jcode.Invoke;
import fr.femtost.disc.minijaja.jcode.Pop;

import java.util.List;

public class AppelI extends ASTInstr {

    private Identifiant ident;
    private ASTListExpr listExpr;

    public AppelI(Identifiant ident, ASTListExpr listExpr) {
        this.ident = ident;
        this.listExpr = listExpr;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.rewrite());
        sb.append("(");
        sb.append(listExpr.rewrite());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple lexp = listExpr.compiler(actual);
        CompilationCouple retrait = listExpr.retirerCompile(actual + lexp.taille + 1);

        return new CompilationCouple(JCodes.concatenate(lexp.jCodes, JCodes.concatLeft(new Invoke(ident.getName()), JCodes.concatRight(retrait.jCodes, new Pop()))),
                lexp.taille + retrait.taille + 2);
    }

    @Override
    public void interpreter(Memoire m) {
        ASTMethode meth = (ASTMethode) m.getPile().parametre(ident.getName());
        ASTListExpr ls = listExpr;
        ASTEntetes entetes = meth.getEntetes();
        while(ls instanceof ExChain && entetes instanceof EChain) {
            m.getPile().declVar(((EChain) entetes).getNode().getIdent().getName(),
                    ((ExChain) ls).getNode().eval(m),
                    ((EChain) entetes).getNode().getType().getSorte());
            ls = ((ExChain) ls).getSuccessor();
            entetes = ((EChain) entetes).getSuccessor();
        }
        meth.getVars().interpreter(m);
        meth.getInstrs().interpreter(m);
        meth.getVars().retirer(m);
        entetes = meth.getEntetes();
        while (entetes instanceof EChain) {
            try {
                m.getPile().retirerDecl(((EChain) entetes).getNode().getIdent().getName());
            } catch (PileException e) {
                ASTLogger.getInstance().logError(this,e.toString());
            }
            entetes = ((EChain) entetes).getSuccessor();
        }
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        if (l.get(0).indice < 4) {
            if (helperPas(m, l, calls, listExpr.getAllCalls())) {
                l.get(0).indice = 4;
                ASTMethode meth = (ASTMethode) m.getPile().parametre(ident.getName());
                ASTListExpr ls = listExpr;
                ASTEntetes entetes = meth.getEntetes();
                while(ls instanceof ExChain && entetes instanceof EChain) {
                    m.getPile().declVar(((EChain) entetes).getNode().getIdent().getName(),
                            ((ExChain) ls).getNode().tryEval(m, calls),
                            ((EChain) entetes).getNode().getType().getSorte());
                    ls = ((ExChain) ls).getSuccessor();
                    entetes = ((EChain) entetes).getSuccessor();
                }
                l.add(0, new InterpretationPasAPasCouple(meth.getInstrs(), 1));
                l.add(0, new InterpretationPasAPasCouple(meth.getVars(), 1));
                if (!listExpr.getAllCalls().isEmpty()) {
                    cleanEvals(calls);
                }
            }
        } else if (l.get(0).indice == 4) {
            l.get(0).indice = 5;
            ASTMethode meth = (ASTMethode) m.getPile().parametre(ident.getName());
            meth.getVars().retirer(m);
            meth.getEntetes().retirer(m);
        }
    }

    @Override
    public int getMaxEtape() {
        return 4;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        if (!global.containsSymbol(ident.getName())) {
            ASTLogger.getInstance().logError(this, "Fonction non déclarée : " + ident.getName());
            return false;
        }
        ASTMethode methode = (ASTMethode) global.getPile().parametre(ident.getName());
        return listExpr.typeCheck(global, local, methode.getEntetes());
    }
}
