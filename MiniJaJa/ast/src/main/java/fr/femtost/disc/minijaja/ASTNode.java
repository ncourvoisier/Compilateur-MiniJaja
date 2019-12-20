package fr.femtost.disc.minijaja;

import fr.femtost.disc.minijaja.ast.ASTClass;
import fr.femtost.disc.minijaja.ast.ASTEntetes;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.ast.ASTListExpr;
import fr.femtost.disc.minijaja.ast.decl.ASTMethode;
import fr.femtost.disc.minijaja.ast.entetes.EChain;
import fr.femtost.disc.minijaja.ast.expr.AppelE;
import fr.femtost.disc.minijaja.ast.listexpr.ExChain;
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
    public abstract void interpreterPasAPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls);
    public abstract int getMaxEtape();

    protected static void cleanEvals(List<MethodeEvalTuple> calls) {
        if (calls.isEmpty())
            return;
        int startP = calls.get(0).profondeur;
        while(!calls.isEmpty() && calls.get(0).profondeur == startP) {
            calls.remove(0);
        }
    }

    /**
     *
     * @param m .
     * @param l .
     * @param calls .
     * @return si une etape a ete faite
     */
    protected static boolean helperPas(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls, List<AppelE> appels) {
        switch (l.get(0).indice) {
            case 1 :
                if (appels.isEmpty()) {
                    return true;
                }
                int profondeur = calls.isEmpty() ? 0 : calls.get(0).profondeur + 1;
                for (int i=appels.size()-1; i>=0; i--) {
                    calls.add(0, new MethodeEvalTuple(appels.get(i), profondeur));
                }
            case 2 :
                if (execCall(m, l, calls)) {
                    break;
                } else {
                    return true;
                }
            case 3 :
                try {
                    for (MethodeEvalTuple tuple : calls) {
                        if (!tuple.traite) {
                            ASTMethode meth = (ASTMethode) m.getPile().parametre(tuple.appel.getIdent());
                            meth.getVars().retirer(m);
                            ASTEntetes entetes = meth.getEntetes();
                            while (entetes instanceof EChain) {
                                try {
                                    m.getPile().retirerDecl(((EChain) entetes).getNode().getIdent().getName());
                                } catch (PileException e) {
                                    ASTLogger.getInstance().logError(e.toString());
                                }
                                entetes = ((EChain) entetes).getSuccessor();
                            }
                            tuple.retour = m.getPile().val(ASTClass.getVariableClass());
                            tuple.traite = true;
                            l.get(0).indice = 2;
                            break;
                        }
                    }
                } catch (PileException e) {
                    ASTLogger.getInstance().logError(e.toString());
                    l.get(0).indice = Integer.MAX_VALUE;
                }
                break;
        }
        return false;
    }

    private static boolean execCall(Memoire m, List<InterpretationPasAPasCouple> l, List<MethodeEvalTuple> calls) {
        if (calls.isEmpty())
            return false;
        int startP = calls.get(0).profondeur;
        for (MethodeEvalTuple tuple : calls) {
            if (!tuple.traite && startP == tuple.profondeur) {
                ASTMethode meth = (ASTMethode) m.getPile().parametre(tuple.appel.getIdent());
                ASTListExpr ls = tuple.appel.getListExpr();
                ASTEntetes entetes = meth.getEntetes();
                while(ls instanceof ExChain && entetes instanceof EChain) {
                    m.getPile().declVar(((EChain) entetes).getNode().getIdent().getName(),
                            ((ExChain) ls).getNode().tryEval(m, calls),
                            ((EChain) entetes).getNode().getType().getSorte());
                    ls = ((ExChain) ls).getSuccessor();
                    entetes = ((EChain) entetes).getSuccessor();
                }
                l.get(0).indice = 3;
                l.add(0, new InterpretationPasAPasCouple(meth.getInstrs(), 1));
                l.add(0, new InterpretationPasAPasCouple(meth.getVars(), 1));
                return true;
            }
        }
        return false;
    }
}

