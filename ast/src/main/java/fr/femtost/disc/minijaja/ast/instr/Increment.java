package fr.femtost.disc.minijaja.ast.instr;

import fr.femtost.disc.minijaja.CompilationCouple;
import fr.femtost.disc.minijaja.JCIdent;
import fr.femtost.disc.minijaja.JCodes;
import fr.femtost.disc.minijaja.ast.ASTInstr;
import fr.femtost.disc.minijaja.ast.expr.ASTIdentGenerique;
import fr.femtost.disc.minijaja.ast.expr.identificateur.Tableau;
import fr.femtost.disc.minijaja.jcode.AInc;
import fr.femtost.disc.minijaja.jcode.Inc;
import fr.femtost.disc.minijaja.jcode.Push;
import fr.femtost.disc.minijaja.jcodes.JChain;
import fr.femtost.disc.minijaja.jcodes.JNil;
import fr.femtost.disc.minijaja.jcval.JCNbre;

public class Increment extends ASTInstr {

    private ASTIdentGenerique identGenerique;

    public Increment(ASTIdentGenerique identGenerique) {
        this.identGenerique = identGenerique;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append(identGenerique.rewrite());
        sb.append("++");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        if (identGenerique instanceof Tableau) {
            CompilationCouple index = ((Tableau)identGenerique).getIndex(actual);

            return new CompilationCouple(JCodes.concatenate(index.jCodes,
                    new JChain(new Push(new JCNbre(1)), new JChain(new AInc(new JCIdent(identGenerique.getName())), new JNil()))),
                    index.taille + 2);
        }
        return new CompilationCouple(new JChain(new Push(new JCNbre(1)), new JChain(new Inc(new JCIdent(identGenerique.getName())), new JNil())), 2);
    }
}
