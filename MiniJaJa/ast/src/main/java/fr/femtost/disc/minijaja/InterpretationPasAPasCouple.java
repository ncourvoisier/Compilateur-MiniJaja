package fr.femtost.disc.minijaja;

public class InterpretationPasAPasCouple {

    public final ASTNode node;
    public int indice;

    public InterpretationPasAPasCouple(ASTNode node, int indice) {
        this.node = node;
        this.indice = indice;
    }
}
