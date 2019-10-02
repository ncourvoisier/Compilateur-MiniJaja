package fr.femtost.disc.minijaja;

public class AstIdent implements AstTree{
    String ident = "identificateur";

    public AstIdent(String ident) {
        this.ident = ident;
    }

    public void eval(){

    }
    //chaine de caractere
    public String rewrite(){

        return "identificateur";
    }
    public String toJajaCode(){

        return null;
    }
}
