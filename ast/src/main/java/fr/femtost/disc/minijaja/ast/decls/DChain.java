package fr.femtost.disc.minijaja.ast.decls;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.Memoire;
import fr.femtost.disc.minijaja.ast.ASTDecl;
import fr.femtost.disc.minijaja.ast.ASTDecls;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;

public class DChain extends ASTDecls {

    private ASTDecls successor;
    private ASTDecl node;

    public DChain(ASTDecls successor, ASTDecl node) {
        this.successor = successor;
        this.node = node;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();

        sb.append(node.rewrite());
        sb.append(";\n");
        sb.append(successor.rewrite());

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple ds = node.compiler(actual);
        CompilationCouple dss = successor.compiler(actual + ds.taille);

        return new CompilationCouple(JCodes.concatenate(ds.jCodes, dss.jCodes), ds.taille + dss.taille);
    }

    @Override
    public CompilationCouple retirerCompile(int actual) {
        CompilationCouple dss = successor.retirerCompile(actual);
        CompilationCouple ds = successor.retirerCompile(actual + dss.taille);

        return new CompilationCouple(JCodes.concatenate(dss.jCodes, ds.jCodes), ds.taille + dss.taille);
    }
    @Override
    public void interpreter(Memoire m) {
        node.interpreter(m);
        successor.interpreter(m);
    }

    @Override
    public boolean firstCheck(Memoire global) {
        boolean b1 = node.firstCheck(global);
        boolean b2 = successor.firstCheck(global);
        return b1 && b2;
    }

    @Override
    public boolean typeCheck(Memoire global) {
        boolean b1 = true;
        if(node instanceof ASTMethode){
            b1 = node.typeCheck(global, null);
        }
        boolean b2 = successor.typeCheck(global);
        return b1 && b2;
    }

    @Override
    public void retirer(Memoire m) {
        successor.retirer(m);
        node.retirer(m);
    }

}
