package fr.femtost.disc.minijaja.ast;

import fr.femtost.disc.minijaja.ASTNode;
import fr.femtost.disc.minijaja.Memoire;

public abstract class ASTExpr extends ASTNode {
abstract public int eval(Memoire m);
}
