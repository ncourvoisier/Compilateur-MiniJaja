package fr.femtost.disc.minijaja.ast.instrs;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;

import java.util.List;

public class IChain extends ASTInstrs {
    private ASTInstrs successor;
    private ASTInstr node;

    public IChain(ASTInstr node, ASTInstrs successor) {
        this.successor = successor;
        this.node = node;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        sb.append(";\n");
        sb.append(successor.rewrite());

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple is = node.compiler(actual);
        CompilationCouple iss = successor.compiler(actual + is.taille);

        return new CompilationCouple(JCodes.concatenate(is.jCodes, iss.jCodes), is.taille + iss.taille);
    }

    @Override
    public void interpreter(Memoire m) {
        node.interpreter(m);
        successor.interpreter(m);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        boolean b1 = node.typeCheck(global, local);
        boolean b2 = successor.typeCheck(global, local);
        return b1 && b2;
    }

    @Override
    public void forwardTypeRetour(Sorte sorte) {
        node.forwardTypeRetour(sorte);
        successor.forwardTypeRetour(sorte);
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        switch(l.get(0).indice)
        {
            case 1:
                l.get(0).indice =2;
                l.add(0, new InterpretationPasAPasCouple(node, 1));
                node.interpreterPasAPas(m,l, leval);
                break;

            case 2:
                l.get(0).indice = 3;
                l.add(0, new InterpretationPasAPasCouple(successor, 1));
                successor.interpreterPasAPas(m,l, leval);
                break;

            default:
                ASTLogger.getInstance().logWarning(this, "Interpretation inconnue :" + l.get(0).indice);
        }
    }

    @Override
    public int getMaxEtape() {
        return 2;
    }
}
