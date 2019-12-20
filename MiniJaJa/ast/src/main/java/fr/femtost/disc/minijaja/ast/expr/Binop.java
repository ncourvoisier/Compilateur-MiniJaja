package fr.femtost.disc.minijaja.ast.expr;

import fr.femtost.disc.minijaja.*;
import fr.femtost.disc.minijaja.ast.ASTExpr;
import fr.femtost.disc.minijaja.jcode.oper.OpBinaire;

public class Binop extends ASTExpr {

    public enum Operandes {
        ADDITION("+"),
        AND("&&"),
        DIVISION("/"),
        EGAL("=="),
        MULTIPLICATION("*"),
        OR("||"),
        SOUSTRACTION("-"),
        SUPERIEUR(">");

        private String icon;

        Operandes(String icon) {
            this.icon = icon;
        }

        public OpBinaire.Operandes getOperande() {
            switch (this) {
                case ADDITION:
                    return OpBinaire.Operandes.ADD;
                case AND:
                    return OpBinaire.Operandes.AND;
                case DIVISION:
                    return OpBinaire.Operandes.DIV;
                case EGAL:
                    return OpBinaire.Operandes.CMP;
                case MULTIPLICATION:
                    return OpBinaire.Operandes.MUL;
                case OR:
                    return OpBinaire.Operandes.OR;
                case SOUSTRACTION:
                    return OpBinaire.Operandes.SUB;
                case SUPERIEUR:
                    return OpBinaire.Operandes.SUP;
                default:
                    ASTLogger.getInstance().logWarning("Opérateur non trouvé");
            }
            return OpBinaire.Operandes.CMP;
        }
    }

    private ASTExpr expr1;
    private ASTExpr expr2;
    private Operandes op;

    public Binop(Operandes op, ASTExpr expr1, ASTExpr expr2) {
        this.op = op;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public String rewrite() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(expr1.rewrite());
        sb.append(op.icon);
        sb.append(expr2.rewrite());
        sb.append(")");

        return sb.toString();
    }

    @Override
    public CompilationCouple compiler(int actual) {
        CompilationCouple e1 = expr1.compiler(actual);
        CompilationCouple e2 = expr2.compiler(actual + e1.taille);

        return new CompilationCouple(JCodes.concatenate(e1.jCodes, JCodes.concatRight(e2.jCodes, new OpBinaire(op.getOperande()))), e1.taille + e2.taille + 1);
    }

    @Override
    public Object eval(Memoire m) {
        Object e1 = expr1.eval(m);
        Object e2 = expr2.eval(m);

        switch (op) {
            case ADDITION:
                return (int)e1 + (int)e2;
            case AND:
                return (boolean)e1 && (boolean)e2;
            case DIVISION:
                return (int)e1 / (int)e2;
            case EGAL:
                return e1.equals(e2);
            case MULTIPLICATION:
                return (int)e1 * (int)e2;
            case OR:
                return (boolean)e1 || (boolean)e2;
            case SOUSTRACTION:
                return (int)e1 - (int)e2;
            case SUPERIEUR:
                return (int)e1 > (int)e2;
            default:
                ASTLogger.getInstance().logWarning("Opérateur non supporté");
        }
        return null;
    }

    @Override
    public boolean typeCheck(Memoire global, Memoire local, Sorte expected) {
        Sorte s1;
        Sorte retour;

        switch (op) {
            case ADDITION:
            case SOUSTRACTION:
            case MULTIPLICATION:
            case DIVISION:
                s1 = Sorte.INT;
                retour = Sorte.INT;
                break;
            case OR:
            case AND:
                s1 = Sorte.BOOL;
                retour = Sorte.BOOL;
                break;
            case SUPERIEUR:
                s1 = Sorte.INT;
                retour = Sorte.BOOL;
                break;
            default:
                s1 = Sorte.VOID;
                retour = Sorte.BOOL;
                break;
        }

        boolean b1 = expected == retour || expected == Sorte.VOID;
        if (!b1) {
            ASTLogger.getInstance().logError(this, "Type mismatch: expected " + expected.name() + " got " + retour.name());
        }
        boolean b2 = expr1.typeCheck(global, local, s1);
        boolean b3 = expr2.typeCheck(global, local, s1);

        return b1 && b2 && b3;
    }
}
