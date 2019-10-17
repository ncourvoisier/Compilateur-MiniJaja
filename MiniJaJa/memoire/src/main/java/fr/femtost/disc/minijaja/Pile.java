package fr.femtost.disc.minijaja;

import java.util.Stack;

public class Pile {

    Stack<Quad> pile; //Objet pile qui stock des quad
    TableDesSymboles tds;

    public Pile(Stack pile) {
        this.pile = pile;
         tds = new TableDesSymboles();
    }

    public Boolean pileEstVide () {
        return pile.isEmpty();
    }

    Stack Empiler (Quad q) {
        pile.push(q);
        return pile;
    }

    Quad Depiler () throws PileException {
        if (pileEstVide()) {
            throw new PileException("Impossible de dépiller un élément la pile est vide.");
        }
        return pile.pop();
    }

    Stack Echanger () throws PileException {
        if (pileEstVide()) {
            throw new PileException("Impossible de dépiller un élément la pile est vide.");
        }
        Quad q1;
        Quad q2;
        q1 = pile.pop();
        q2 = pile.pop();
        Empiler(q1);
        Empiler(q2);
        return pile;
    }

    Stack DeclVar (String ID, Object VAL, String SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        Empiler(q);
        return pile;
    }

    Quad ReturnQuadWithId (String ID) {
        return tds.chercheQuad(ID);
    }

    void IdentVal (String ID, String SORTE, int S) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide, impossible d'utiliser la fonction IdentVal.");
        }
        Quad q = Depiler();
        if (S == 0) {
            Empiler(new Quad(ID, q.VAL, NatureObjet.VAR, SORTE));
        } else {
            IdentVal(ID, SORTE, S-1);
            Empiler(q);
        }
    }

    Stack DeclCst (String ID, Object VAL, String SORTE) {
        Quad q;
        if (VAL == null) {
            q = tds.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        } else {
            q = tds.creerSymboles(ID, VAL, NatureObjet.CST, SORTE);
        }
        Empiler(q);
        return pile;
    }

    Stack DeclTab (String ID, Object VAL, String SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.TAB, SORTE);
        Empiler(q);
        return pile;
    }


    Stack DeclMeth (String ID, Object VAL, String SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
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
