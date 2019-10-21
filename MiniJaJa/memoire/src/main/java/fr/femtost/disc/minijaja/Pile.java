package fr.femtost.disc.minijaja;

import java.util.Stack;

public class Pile {

    Stack<Quad> pile; //Objet pile qui stock des quad
    TableDesSymboles tds;

    public Pile(Stack pile) {
        this.pile = pile;
         tds = new TableDesSymboles();
    }

    public int returnTaillePile() {
        return pile.size();
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

    Stack DeclVar (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        Empiler(q);
        return pile;
    }

    Quad ReturnQuadWithId (String ID) {
        return tds.chercheQuad(ID);
    }

    void IdentVal (String ID, Sorte SORTE, int S) throws PileException {
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

    Stack DeclCst (String ID, Object VAL, Sorte SORTE) {
        Quad q;
        if (VAL == null) {
            q = tds.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        } else {
            q = tds.creerSymboles(ID, VAL, NatureObjet.CST, SORTE);
        }
        Empiler(q);
        return pile;
    }

    Stack DeclTab (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.TAB, SORTE);
        Empiler(q);
        return pile;
    }


    Stack DeclMeth (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
        Empiler(q);
        return pile;
    }

    void RetirerDecl (String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtrenir la nature objet de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.ID)){
            if (q.OBJ.equals(NatureObjet.TAB)) {
                // RetirerTas(q.VAL, q.SORTE);
            }
        } else {
            Quad qDepile = Depiler();
            RetirerDecl(ID);
            Empiler(qDepile);
        }
    }

    void AffecterVal (String ID, Object VAL) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide, impossible de dépiler un élément.");
        }
        Quad q1 = Depiler();
        if (!ID.equals(q1.ID)) {
            Empiler(q1);
        } else if (q1.OBJ.equals(NatureObjet.VCST)) {
            Quad q2 = new Quad(q1.ID, VAL, NatureObjet.CST, q1.SORTE);
            Empiler(q2);
        } else if (!q1.OBJ.equals(NatureObjet.CST)) {
            Quad q3 = new Quad(ID, VAL, q1.OBJ, q1.SORTE);
            Empiler(q3);
        } else if (q1.OBJ.equals(NatureObjet.TAB)) {
            //AjouterRef(v, t)
            //RetirerTas(v1 , t);
        } else {
            Quad q5 = new Quad(ID, VAL, q1.OBJ, q1.SORTE);
            Empiler(q5);
        }
    }



    Object Val(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtrenir la valeur de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.ID)){
            return q.VAL;
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).ID.equals(ID)) {
                    return pile.get(i).VAL;
                }
            }
        }
        return "NOK_";
    }

    NatureObjet Object(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtrenir la nature objet de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.ID)){
            return q.OBJ;
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).ID.equals(ID)) {
                    return pile.get(i).OBJ;
                }
            }
        }
        return null;
    }

    Sorte Sorte(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtrenir la sorte de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.ID)){
            return q.SORTE;
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).ID.equals(ID)) {
                    return pile.get(i).SORTE;
                }
            }
        }
        return null;
    }


}
