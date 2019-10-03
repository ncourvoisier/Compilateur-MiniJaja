package fr.femtost.disc.minijaja;

public class AstDcls implements AstTree{
    AstTree decl;
    AstTree decls;

    public AstDcls(AstTree decl, AstTree decls) {
        this.decl = decl;
        this.decls = decls;
    }

    public void eval(){

    }

    public String rewrite(){
        StringBuilder sb = new StringBuilder();
        sb.append(decl.rewrite());
        sb.append(";");
        sb.append(decls.rewrite());
        return sb.toString();
    }
    public String toJajaCode(){

        return null;
    }

}
