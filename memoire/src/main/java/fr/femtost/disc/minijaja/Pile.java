package fr.femtost.disc.minijaja;

public class Pile {

    private TableDesSymboles tds;
    private Tas tas;
    private Quad stackTop;

    public Pile(Tas tas) {
        tds = new TableDesSymboles();
        this.tas = tas;
    }

    public int returnTaillePile() {
        int taille = 0;
        if (isEmpty()) {
            return taille;
        }
        taille = 1;
        Quad next = stackTop;
        while (next.getBottomQuad() != null) {
            taille++;
            next = next.getBottomQuad();
        }
        return taille;
    }

    public void Empiler (Quad q) {
        if (stackTop == null) {
            stackTop = q;
            stackTop.setBottomQuad(null);
        }
        else {
            stackTop.setTopQuad(q);
            q.setBottomQuad(stackTop);
            stackTop = q;
        }
    }

    private boolean isEmpty() {
        return stackTop == null;
    }

    public Quad Depiler () throws PileException {
        if (isEmpty()) {
            throw new PileException("Impossible de dépiller un élément la pile est vide.");
        }
        Quad oldTop = stackTop;
        stackTop = stackTop.getBottomQuad();
        return oldTop;
    }

    public void Echanger () throws PileException {
        if (isEmpty()) {
            throw new PileException("Impossible de dépiller un élément la pile est vide.");
        }
        if (stackTop.getBottomQuad() == null) {
            throw new PileException("Impossible d'échanger 2 éléments de la pile.");
        }
        Quad top = Depiler();
        Quad bottom = Depiler();
        Empiler(top);
        Empiler(bottom);
    }

    public void DeclVar (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        Empiler(q);
    }

    public Quad ReturnQuadWithId (String ID) {
        return tds.chercheQuad(ID);
    }

    public void IdentVal (String ID, Sorte SORTE, int S) throws PileException {
        if (isEmpty()) {
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

    public void DeclCst (String ID, Object VAL, Sorte SORTE) {
        Quad q;
        if (VAL == null) {
            q = tds.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        } else {
            q = tds.creerSymboles(ID, VAL, NatureObjet.CST, SORTE);
        }
        Empiler(q);
    }

    public void DeclTab (String ID, Object VAL, Sorte SORTE) {
        int addr = AjouterRef(VAL);
        if (addr >= 0) {
            Quad q = tds.creerSymboles(ID, addr, NatureObjet.TAB, SORTE);
            Empiler(q);
        }
    }


    public void DeclMeth (String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
        Empiler(q);
    }

    public void RetirerDecl (String ID) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q = stackTop;
        if (ID.equals(q.getID())){
            if (q.getOBJ().equals(NatureObjet.TAB)) {
                RetirerTas(q.getVAL());
            }
            Depiler();
        } else {
            Quad qDepile = Depiler();
            RetirerDecl(ID);
            Empiler(qDepile);
        }
    }

    public void AffecterVal (String ID, Object VAL) throws PileException {
        if (isEmpty()) {
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
        } else {  // Meth
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
        if (isEmpty()) {
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
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
//        Quad q = stackTop;
//        if (ID.equals(q.getID())){
//            return q.getVAL();
//        } else {
//            int taille = returnTaillePile();
//            for (int i = 0; i < taille; i++) {
//                if (pile.get(i).getID().equals(ID)) {
//                    return pile.get(i).getVAL();
//                }
//            }
//        }
        Quad quadV = tds.chercheQuad(ID);
        if (quadV != null) {
            return quadV.getVAL();
        }
        return "NOK_";
    }

    public Object ValT(String ID, int INDEX) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
//        int taille = returnTaillePile();
//        for (int i = 0; i < taille; i++) {
//            Quad q = pile.get(i);
//            if (q.getID().equals(ID)) {
//                if (q.getVAL() instanceof Integer) {
//                    return tas.lire((Integer) q.getVAL(), INDEX);
//                }
//                return null;
//            }
//        }
        Quad quadV = tds.chercheQuad(ID);
        if (quadV != null) {
            return tas.lire((Integer) quadV.getVAL(), INDEX);
        }
        return null;
    }

    public NatureObjet Object(String ID) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la nature objet de l'élément " + ID + ".");
        }
//        Quad q = pile.peek();
//        if (ID.equals(q.getID())){
//            return q.getOBJ();
//        } else {
//            int taille = returnTaillePile();
//            for (int i = 0; i < taille; i++) {
//                if (pile.get(i).getID().equals(ID)) {
//                    return pile.get(i).getOBJ();
//                }
//            }
//        }
        Quad quadO = tds.chercheQuad(ID);
        if (quadO != null) {
            return quadO.getOBJ();
        }
        return null;
    }

    public Sorte Sorte(String ID) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la sorte de l'élément " + ID + ".");
        }
//        Quad q = pile.peek();
//        if (ID.equals(q.getID())){
//            return q.getSORTE();
//        } else {
//            int taille = returnTaillePile();
//            for (int i = 0; i < taille; i++) {
//                if (pile.get(i).getID().equals(ID)) {
//                    return pile.get(i).getSORTE();
//                }
//            }
//        }
        Quad quadS = tds.chercheQuad(ID);
        if (quadS != null) {
            return quadS.getSORTE();
        }
        return null;
    }

    public Object Parametre(String s) {
        Quad qID = tds.chercheQuad(s);
        if(qID==null || qID.getOBJ() != NatureObjet.METH) {
            return null;
        }
        return  qID.getVAL();
    }

    public boolean AffecterType(String ID, Sorte SORTE) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'affecter le type de l'ID : " + ID + ".");
        }
        Quad qID = tds.chercheQuad(ID);
        if (qID == null) {
            return false;
        }
        qID.setSORTE(SORTE);
        return true;
    }

    public TableDesSymboles getTds() {
        return tds;
    }

    public Quad getStackTop() {
        return stackTop;
    }

    public Tas getTas() {
        return tas;
    }

    @Override
    public String toString() {
        String pileQuadString = "";
        if (isEmpty()) {
            return pileQuadString;
        }
        pileQuadString += stackTop.toString();
        Quad next = stackTop;
        while (next.getBottomQuad() != null) {
            pileQuadString += ".";
            pileQuadString += next.getBottomQuad();
            next = next.getBottomQuad();
        }
        return pileQuadString;
    }
}
