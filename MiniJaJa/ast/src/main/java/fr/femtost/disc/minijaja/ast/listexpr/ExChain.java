package fr.femtost.disc.minijaja.ast.listexpr;


import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;

public class ExChain extends ASTListExpr {
    private ASTListExpr successor;
    private ASTExpr node;

    public ExChain(ASTListExpr successor, ASTExpr node) {
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
}
