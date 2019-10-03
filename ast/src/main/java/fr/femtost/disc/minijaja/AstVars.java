package fr.femtost.disc.minijaja;

public class AstVars {
    AstTree vars;
    AstTree var;


    public AstVars(AstTree astVars, AstTree astVar) {
        this.vars = astVars;
        this.var = astVar;
    }

    public void eval(){

    }
    //chaine de caractere
    public String rewrite(){
        StringBuilder sb = new StringBuilder();
        sb.append(var.rewrite());
        sb.append(";");
        sb.append(vars.rewrite());
        return sb.toString();
    }
    public String toJajaCode(){

        return null;
    }
}
