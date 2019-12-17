package fr.femtost.disc.minijaja.ast.listexpr;


import fr.femtost.disc.minijaja.ASTLogger;
import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.entetes.Enil;
import fr.femtost.disc.minijaja.jcode.Pop;
import fr.femtost.disc.minijaja.jcode.Swap;

public class ExChain extends ASTListExpr {

    private ASTListExpr successor;
    private ASTExpr node;

    public ExChain(ASTExpr node, ASTListExpr successor) {
        this.successor = successor;
        this.node = node;
    }

    public ASTListExpr getSuccessor() {
        return successor;
    }

    public ASTExpr getNode() {
        return node;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        if(successor instanceof ExChain) {
            sb.append(",");
            sb.append(successor.rewrite());
        }

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple exp = node.compiler(actual);
        CompilationCouple lesp = successor.compiler(actual + exp.taille);

        return new CompilationCouple(JCodes.concatenate(exp.jCodes, lesp.jCodes), exp.taille + lesp.taille);
    }

    @Override
    public CompilationCouple retirerCompile(int actual) {
        CompilationCouple retrait = successor.retirerCompile(actual);

        return new CompilationCouple(JCodes.concatLeft(new Swap(), JCodes.concatLeft(new Pop(), retrait.jCodes)), retrait.taille + 2);
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, ASTEntetes entetes) {
        if (entetes instanceof Enil) {
            ASTLogger.getInstance().logError(this, "Nombre de paramètres excède celui de la fonction appelée");
            return false;
        }
        EChain chain = (EChain) entetes;
        boolean b1 = node.typeCheck(global, local, chain.getNode().getType().getSorte());
        boolean b2 = successor.typeCheck(global, local, chain.getSuccessor());
        return b1 && b2;
    }
}
