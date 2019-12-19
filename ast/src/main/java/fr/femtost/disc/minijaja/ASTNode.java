package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.jcodes.JNil;

import java.util.List;

public abstract class ASTNode implements Positionable {

    private int line ;
    private int column ;


    @Override
    public int getLine(){
        return this.line;
    }

    @Override
    public int getColumn()
    {
        return this.column;
    }

    public void setLine(int line){
        this.line = line;
    }
    public void setColumn(int column){
        this.column = column;
    }

    public void setPosition (int line , int column){
        this.column = column;
        this.line = line;
    }

    public abstract String rewrite();

    public CompilationCouple compiler(int actual) {
        throw new UnsupportedOperationException("Compilation not implemented in " + this.getClass().getName());
    }

    public CompilationCouple retirerCompile(int actual) {
    return new CompilationCouple(new JNil(), 0);
    }
    public abstract void interpreter(Memoire m);
    public abstract void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<EvaluationCouplePasAPas> leval);
    public abstract int getMaxEtape();

}

