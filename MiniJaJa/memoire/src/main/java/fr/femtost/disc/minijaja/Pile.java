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

    Stack IdentVal (String ID, String SORTE, int S) {
        if (S == 0) {
            Quad q1 = pile.peek();
            Quad q2 = new Quad(ID, q1.VAL, NatureObjet.VAR, SORTE);
            return Empiler(q2);
        }
        return IdentVal(ID, SORTE, S-1);
    }

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

    Stack RetirerDecl (String ID) {
        pile.pop();
        return null; //Tas à retourner ici
    }

    Stack RetirerDecl (String ID, Quad q1) {
         if (ID.equals(q1.ID)) {
             if (q1.OBJ.equals(NatureObjet.TAB)) {
                 // retirer tas
             }
             return pile;
         } else {
            RetirerDecl(ID);
         }
        return  pile;
    }

    Stack AffecterVal (String ID, Object VAL) {
        Quad q1 = pile.peek();
        if (!ID.equals(q1.ID)) {

        } else if (q1.OBJ.equals("VCST")) {
            return pile;
        } else if (q1.OBJ.equals("CST")) {
            return pile;
        } else if (q1.OBJ.equals("TAB")) {
            //AjouterRef(v, t)
            //RetirerTas(v1 , t);
        } else {
            return pile;
        }
        return pile;
    }


}
