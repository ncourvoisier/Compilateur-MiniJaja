package fr.femtost.disc.minijaja.ast.type;

public final class Booleen extends ASTType {


    public String rewrite() {
        return "boolean";
    }

    public void typeCheck() {
        //TODO : implement
        //Probablement objet de type dictionnaire à passer en paramètre
    }
}
