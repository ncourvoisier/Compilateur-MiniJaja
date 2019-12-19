package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.jcode.New;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public class ASTEntete extends ASTNode {
    private Identifiant ident;
    private ASTType type;

    private int position;

    public ASTEntete(Identifiant ident, ASTType type) {
        this.ident = ident;
        this.type = type;
    }

    public Identifiant getIdent() {
        return ident;
    }

    public ASTType getType() {
        return type;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.rewrite()).append(" ");
        sb.append(ident.rewrite());

        return sb.toString();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public CompilationCouple compiler(int actual) {
        return new CompilationCouple(new JChain(new New(ident.getName(), type.getSorte(), JCSorte.VARIABLE, position), new JNil()), 1);
    }

    @Override
    public void interpreter(Memoire m) {
        try {
            m.getPile().retirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.getMessage());
        }
    }

    public void retirer(Memoire m) {
        try {
            m.getPile().retirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(this,e.getMessage());
        }
    }

    public boolean typeCheck(Memoire globale, Memoire locale) {
        if (locale.containsSymbol(ident.getName())) {
            ASTLogger.getInstance().logError(this, "Duplicated declaration of " + ident.getName());
            return false;
        }
        if (globale.containsSymbol(ident.getName())) {
            ASTLogger.getInstance().logWarning(this, "Local variable shadowing global: " + ident.getName());
        }
        locale.getPile().declVar(ident.getName(), null, type.getSorte());
        return true;
    }

    @Override
    public void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval) {
        interpreter(m);
    }

    @Override
    public int getMaxEtape() {
        return 1;
    }
}
