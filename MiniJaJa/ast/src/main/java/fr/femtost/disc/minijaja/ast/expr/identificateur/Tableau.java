package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.jcode.ALoad;

public class Tableau extends ASTIdentGenerique {

    private String name;
    private ASTExpr index;

    public Tableau(String name, ASTExpr index) {
        this.name = name;
        this.index = index;
    }

    public String rewrite() {
        return name + "[" + index.rewrite() + "]";
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple i = index.compiler(actual);
        return new CompilationCouple(JCodes.concatRight(i.jCodes, new ALoad(new JCIdent(name))), i.taille + 1);
    }

    public CompilationCouple getIndex(int actual) {
        return index.compiler(actual);
    }
    public int evalIndex(Memoire m) {
        return (int)index.eval(m);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object eval(Memoire m) {
        return 0;
    }

    @Override
    public void typeCheck(Memoire m) {

    }
}
