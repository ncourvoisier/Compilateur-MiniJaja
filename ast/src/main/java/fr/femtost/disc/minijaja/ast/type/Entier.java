package fr.femtost.disc.minijaja.ast.type;

public final class Entier extends ASTType {


    public String rewrite() {
        return "int";
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
