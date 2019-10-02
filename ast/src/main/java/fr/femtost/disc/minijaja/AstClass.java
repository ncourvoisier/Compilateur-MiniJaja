package fr.femtost.disc.minijaja;

public class AstClass implements AstTree{
    AstTree ident;
    AstTree decls;
    AstTree methmain;

    public AstClass(AstTree ident, AstTree decls, AstTree methmain) {
        this.ident = ident;
        this.decls = decls;
        this.methmain = methmain;
    }

    public void eval(){

    }
    //Appel ident, "{" appeel decls appell methmain "}"
    public String rewrite(){
        StringBuilder sb = new StringBuilder();
        sb.append("class");
        sb.append(ident.rewrite());
        sb.append("{");
        sb.append(decls.rewrite());
        sb.append(methmain.rewrite());
        sb.append("}");
        return sb.toString();
    }
    public String toJajaCode(){

        return null;
    }
}
