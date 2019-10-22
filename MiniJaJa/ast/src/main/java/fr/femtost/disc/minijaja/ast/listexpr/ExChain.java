package fr.femtost.disc.minijaja.ast.listexpr;


import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;

public class ExChain extends ASTListExpr {

    private ASTListExpr successor;
    private ASTExpr node;

    public ExChain(ASTExpr node, ASTListExpr successor) {
        this.successor = successor;
        this.node = node;
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
}
