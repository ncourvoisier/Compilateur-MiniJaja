package fr.femtost.disc.minijaja;

public class EtatArret {

    private ASTNode node;
    int index;

    public EtatArret(ASTNode node, int index) {
        this.node = node;
        this.index = index;
    }

    public ASTNode getNode() {
        return node;
    }

    public int getIndex() {
        return index;
    }
}
