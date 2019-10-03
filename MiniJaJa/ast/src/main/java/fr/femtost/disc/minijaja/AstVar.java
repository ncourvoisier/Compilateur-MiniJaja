package fr.femtost.disc.minijaja;

public class AstVar {
    AstTree typemeth;
    AstTree ident;
    AstTree vexp;


    public AstVar(AstTree typemeth, AstTree ident, AstTree vexp) {
        this.typemeth = typemeth;
        this.ident = ident;
        this.vexp = vexp;
    }

    public void eval(){

    }
    //chaine de caractere
    public String rewrite(){
        StringBuilder sb = new StringBuilder();
        sb.append(typemeth.rewrite());
        sb.append(ident.rewrite());
        sb.append(vexp.rewrite());
        return sb.toString();
    }
    public String toJajaCode(){

        return null;
    }
}
