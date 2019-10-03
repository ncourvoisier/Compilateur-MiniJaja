package fr.femtost.disc.minijaja;

import java.util.Stack;

public class Pile {

    Stack<Quad> pile; //Objet pile qui stock des quad


    public Pile(Stack pile, Memoire mem) {
        this.pile = pile;
    }

    Stack Empiler (Quad q) {
        pile.push(q);
        return pile;
    }

    Stack Depiler () {
        pile.pop();
        return pile;
    }

    Stack Echanger () {
        Quad q1;
        Quad q2;
        q1 = pile.pop();
        q2 = pile.pop();
        Empiler(q1);
        Empiler(q2);
        return pile;
    }

    Stack DeclVar (String ID, Object VAL, String SORTE) {
        Quad q = TableDesSymboles.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        Empiler(q);
        return pile;
    }

    // identVal pas trop compris sur le cours

    Stack DeclCst (String ID, Object VAL, String SORTE) {
        Quad q = TableDesSymboles.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        Empiler(q);
        return pile;
    }

    Stack DeclTab (String ID, Object VAL, String SORTE) {
        Quad q = TableDesSymboles.creerSymboles(ID, VAL, NatureObjet.TAB, SORTE);
        Empiler(q);
        return pile;
    }


    Stack DeclMeth (String ID, Object VAL, String SORTE) {
        Quad q = TableDesSymboles.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
        Empiler(q);
        return pile;
    }



}
