package fr.femtost.disc.minijaja.ast.instrs;

import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.ASTInstrs;

public class IChain extends ASTInstrs {
    private ASTInstrs successor;
    private ASTInstr node;

    public IChain(ASTInstrs successor, ASTInstr node) {
        this.successor = successor;
        this.node = node;
    }

    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        sb.append(";\n");
        sb.append(successor.rewrite());

        return sb.toString();
    }
}
