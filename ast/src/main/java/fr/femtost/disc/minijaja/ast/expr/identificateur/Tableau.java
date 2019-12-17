package fr.femtost.disc.minijaja.ast.expr.identificateur;

import fr.femtost.disc.minijaja.*;
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
        return new CompilationCouple(JCodes.concatRight(i.jCodes, new ALoad(name)), i.taille + 1);
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
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        Quad decl;
        if (local.containsSymbol(name)) {
            decl = local.getPile().returnQuadWithId(name);
        } else {
            if (global.containsSymbol(name)) {
                decl = global.getPile().returnQuadWithId(name);
            } else {
                ASTLogger.getInstance().logError(this, "Variable non déclarée : " + name);
                return false;
            }
        }
        if (checkIndex(global, local)) {
            if (expected != Sorte.VOID && decl.getSORTE() != expected) {
                ASTLogger.getInstance().logError(this, "Type incorrect, attendu " + expected.name() + " trouvé " + decl.getSORTE().name());
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean checkIndex(Memoire global, Memoire local) {
        return index.typeCheck(global, local, Sorte.INT);
    }
}
