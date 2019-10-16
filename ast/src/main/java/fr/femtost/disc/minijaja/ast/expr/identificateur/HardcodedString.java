package fr.femtost.disc.minijaja.ast.expr.identificateur;

public class HardcodedString extends Identifiant {

    public HardcodedString(String chain) {
        super(chain);
    }

    @Override
    public String rewrite() {
        return "\"" + this.name + "\"";
    }
}
