package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Identifiant;
import fr.femtost.disc.minijaja.ast.type.ASTType;
import fr.femtost.disc.minijaja.jcode.New;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;

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
            m.getPile().RetirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
        }
    }

    @Override
    public void retirer(Memoire m) {
        try {
            m.getPile().RetirerDecl(ident.getName());
        } catch (PileException e) {
            ASTLogger.getInstance().logError(e.getMessage());
        }
    }

    @Override
    public void typeCheck(Memoire m) {

    }
}
