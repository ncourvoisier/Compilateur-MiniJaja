package fr.femtost.disc.minijaja;

import java.util.Stack;

public class Pile {

    Stack<Quad> pile; //Objet pile qui stock des quad
    TableDesSymboles tds;
    Tas tas;

    public Pile(Tas tas) {
        pile = new Stack<>();
        tds = new TableDesSymboles();
        this.tas = tas;
    }

    public int returnTaillePile() {
        return pile.size();
    }

    public Boolean pileEstVide () {
        return pile.isEmpty();
    }

    public Stack Empiler (Quad q) {
        pile.push(q);
        return pile;
    }

    public Quad Depiler () throws PileException {
        if (pileEstVide()) {
            throw new PileException("Impossible de dépiller un élément la pile est vide.");
        }
        return pile.pop();
    }

    public Stack Echanger () throws PileException {
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

    public Stack DeclVar (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        Empiler(q);
        return pile;
    }

    public Quad ReturnQuadWithId (String ID) {
        return tds.chercheQuad(ID);
    }

    public void IdentVal (String ID, Sorte SORTE, int S) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide, impossible d'utiliser la fonction IdentVal.");
        }
        Quad q = Depiler();
        if (S == 0) {
            Empiler(new Quad(ID, q.getVAL(), NatureObjet.VAR, SORTE));
        } else {
            IdentVal(ID, SORTE, S-1);
            Empiler(q);
        }
    }

    public Stack DeclCst (String ID, Object VAL, Sorte SORTE) {
        Quad q;
        if (VAL == null) {
            q = tds.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        } else {
            q = tds.creerSymboles(ID, VAL, NatureObjet.CST, SORTE);
        }
        Empiler(q);
        return pile;
    }

    public Stack DeclTab (String ID, Object VAL, Sorte SORTE) {
        int addr = AjouterRef(VAL);
        if (addr >= 0) {
            Quad q = tds.creerSymboles(ID, addr, NatureObjet.TAB, SORTE);
            Empiler(q);
        }
        return pile;
    }


    public Stack DeclMeth (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
        Empiler(q);
        return pile;
    }

    public void RetirerDecl (String ID) throws PileException {
        if (pileEstVide()) {
            return;
        }
        Quad q = pile.peek();
        if (ID.equals(q.getID())){
            if (q.getOBJ().equals(NatureObjet.TAB)) {
                RetirerTas(q.getVAL());
            }
        } else {
            Quad qDepile = Depiler();
            RetirerDecl(ID);
            Empiler(qDepile);
        }
    }

    public void AffecterVal (String ID, Object VAL) throws PileException {
        if (pileEstVide()) {
            return;
        }
        Quad q1 = Depiler();
        if (!ID.equals(q1.getID())) {
            AffecterVal(ID, VAL);
            Empiler(q1);
        } else if (q1.getOBJ().equals(NatureObjet.VCST)) {
            Quad q2 = tds.creerSymboles(q1.getID(), VAL, NatureObjet.CST, q1.getSORTE());
            Empiler(q2);
        } else if (!q1.getOBJ().equals(NatureObjet.CST)) {
            Quad q3 = tds.creerSymboles(ID, VAL, q1.getOBJ(), q1.getSORTE());
            Empiler(q3);
        } else if (q1.getOBJ().equals(NatureObjet.TAB)) {
            int addr = AjouterRef(VAL);
            if (addr >= 0) {
                Quad q4 = tds.creerSymboles(ID, addr, q1.getOBJ(), q1.getSORTE());
                RetirerTas(q1.getVAL());
                Empiler(q4);
            }
        } else {
            Quad q5 = tds.creerSymboles(ID, VAL, q1.getOBJ(), q1.getSORTE());
            Empiler(q5);
        }
    }

    // Ajout d'une référence à un emplacement dans le tas, stockée dans l'attribut VAL d'un Quad de nature TAB
    // Ret: adresse dans le tas
    // Ret Err: -1 = Problème mémoire -2 = VAL pas entier
    public int AjouterRef(Object VAL) {
        if (VAL instanceof Integer) {
            return tas.allouer((Integer) VAL);
        }
        return -2;
    }

    // Désallocation de l'espace mémoire dans le tas associé à l'adresse VAL
    public void RetirerTas(Object VAL) {
        if (VAL instanceof Integer) {
            tas.liberer((Integer) VAL);
        }
    }

    // Affectation d'une valeur à une case d'un tableau déjà existant
    public void AffecterValT (String ID, Object VAL, int INDEX) throws PileException {
        if (pileEstVide()) {
            return;
        }
        Quad q1 = Depiler();
        if (!ID.equals(q1.getID())) {
            AffecterValT(ID, VAL, INDEX);
        }
        else {
            AffecterTas(q1.getVAL(), INDEX, VAL);
        }
        Empiler(q1);
    }

    // VALT: Adresse du tableau dans le tas | VAL: Valeur à affecter à l'index INDEX du tableau
    public void AffecterTas(Object VALT, int INDEX, Object VAL) {
        if (VALT instanceof Integer) {
            tas.ecrire((Integer) VALT, INDEX, VAL);
        }
    }

    public Object Val(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.getID())){
            return q.getVAL();
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).getID().equals(ID)) {
                    return pile.get(i).getVAL();
                }
            }
        }
        return "NOK_";
    }

    public Object ValT(String ID, int INDEX) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
        int taille = returnTaillePile();
        for (int i = 0; i < taille; i++) {
            Quad q = pile.get(i);
            if (q.getID().equals(ID)) {
                if (q.getVAL() instanceof Integer) {
                    return tas.lire((Integer) q.getVAL(), INDEX);
                }
                return null;
            }
        }
        return null;
    }

    public NatureObjet Object(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtenir la nature objet de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.getID())){
            return q.getOBJ();
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).getID().equals(ID)) {
                    return pile.get(i).getOBJ();
                }
            }
        }
        return null;
    }

    public Sorte Sorte(String ID) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'obtenir la sorte de l'élément " + ID + ".");
        }
        Quad q = pile.peek();
        if (ID.equals(q.getID())){
            return q.getSORTE();
        } else {
            int taille = returnTaillePile();
            for (int i = 0; i < taille; i++) {
                if (pile.get(i).getID().equals(ID)) {
                    return pile.get(i).getSORTE();
                }
            }
        }
        return null;
    }

    public boolean AffecterType(String ID, Sorte SORTE) throws PileException {
        if (pileEstVide()) {
            throw new PileException("La pile est vide impossible d'affecter le type de l'ID : " + ID + ".");
        }
        Quad qID = tds.chercheQuad(ID);
        if (qID == null) {
            return false;
        }
        qID.setSORTE(SORTE);
        return true;
    }

}
