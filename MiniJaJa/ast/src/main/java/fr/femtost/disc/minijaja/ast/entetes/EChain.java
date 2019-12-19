package fr.femtost.disc.minijaja.ast.entetes;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.InterpretationPasAPasCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTEntete;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.type.Entier;

import java.util.List;

public class EChain extends ASTEntetes {
    private ASTEntetes successor;
    private ASTEntete node;
    private int position = 0;

    public EChain(ASTEntetes successor, ASTEntete node) {
        this.successor = successor;
        this.node = node;
    }

    public ASTEntetes getSuccessor() {
        return successor;
    }

    public ASTEntete getNode() {
        return node;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        if(successor instanceof EChain) {
            sb.append(", ");
            sb.append(successor.rewrite());
        }

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple ens = successor.compiler(actual);
        this.position = successor.getChainPosition()+1;
        node.setPosition(position);
        CompilationCouple en = node.compiler(actual + ens.taille);
        return new CompilationCouple(JCodes.concatenate(ens.jCodes, en.jCodes), en.taille + ens.taille);
    }

    @Override
    public int getChainPosition() {
        return position;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local) {
        boolean b1 = node.typeCheck(global, local);
        boolean b2 = successor.typeCheck(global, local);
        return b1 && b2;
    }

    @Override
    public void interpreter(Memoire m) {
        //noop
    }

    @Override
    public void retirer(Memoire m) {
        successor.retirer(m);
        node.retirer(m);
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l) {
        //noop
    }

    @Override
    public int getMaxEtape() {
        return 1;
    }
}
