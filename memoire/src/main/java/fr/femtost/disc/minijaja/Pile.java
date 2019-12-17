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

    public void empiler(Quad q) {
        ASTLogger.getInstance().logDebug("EMPILER & CREATE "+ q);
        if (stackTop == null) {
            stackTop = q;
            stackTop.setBottomQuad(null);
        }
        else {
            stackTop.setTopQuad(q);
            q.setBottomQuad(stackTop);
            stackTop = q;
        }
        tds.creerSymboles(q.getID(), q.getVAL(), q.getOBJ(), q.getSORTE());
    }

    private void empilerNoCreate(Quad q) {
        ASTLogger.getInstance().logDebug("EMPILER "+ q);
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

    public Quad depiler() throws PileException {

        if (isEmpty()) {
            throw new PileException("Impossible de dépiler un élément la pile est vide.");
        }
        Quad oldTop = stackTop;
        ASTLogger.getInstance().logDebug("DEPILER & DELETE " + oldTop);
        stackTop = stackTop.getBottomQuad();
        tds.enleverSymbole(oldTop.getID());
        return oldTop;
    }

    private Quad depilerNoRemove() throws PileException {
        if (isEmpty()) {
            throw new PileException("Impossible de dépiler un élément la pile est vide.");
        }
        Quad oldTop = stackTop;
        ASTLogger.getInstance().logDebug("DEPILER " + oldTop);
        stackTop = stackTop.getBottomQuad();
        return oldTop;
    }

    public void echanger() throws PileException {
        if (isEmpty()) {
            throw new PileException("Impossible de dépiler un élément la pile est vide.");
        }
        if (stackTop.getBottomQuad() == null) {
            throw new PileException("Impossible d'échanger 2 éléments de la pile.");
        }
        Quad top = depilerNoRemove();
        Quad bottom = depilerNoRemove();
        empilerNoCreate(top);
        empilerNoCreate(bottom);
    }

    public void declVar(String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.VAR, SORTE);
        empilerNoCreate(q);
    }

    public Quad returnQuadWithId(String ID) {
        return tds.chercheQuad(ID);
    }

    public void identVal(String ID, Sorte SORTE, int S) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide, impossible d'utiliser la fonction identVal.");
        }
        Quad q = depiler();
        if (S == 0) {
            empiler(new Quad(ID, q.getVAL(), NatureObjet.VAR, SORTE));
        } else {
            identVal(ID, SORTE, S-1);
            empiler(q);
        }
    }

    public void declCst(String ID, Object VAL, Sorte SORTE) {
        Quad q;
        if (VAL == null) {
            q = tds.creerSymboles(ID, VAL, NatureObjet.VCST, SORTE);
        } else {
            q = tds.creerSymboles(ID, VAL, NatureObjet.CST, SORTE);
        }
        empilerNoCreate(q);
    }

    public void declTab(String ID, Object VAL, Sorte SORTE) {
        int addr = ajouterRef(VAL);
        if (addr >= 0) {
            Quad q = tds.creerSymboles(ID, addr, NatureObjet.TAB, SORTE);
            empilerNoCreate(q);
        }
    }


    public void declMeth(String ID, Object VAL, Sorte SORTE) {
        Quad q = tds.creerSymboles(ID, VAL, NatureObjet.METH, SORTE);
        empilerNoCreate(q);
    }

    public void retirerDecl(String ID) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q = stackTop;
        if (ID.equals(q.getID())){
            if (q.getOBJ().equals(NatureObjet.TAB)) {
                retirerTas(q.getVAL());
            }
            depiler();
        } else {
            Quad qDepile = depilerNoRemove();
            retirerDecl(ID);
            empilerNoCreate(qDepile);
        }
    }

    public void affecterVal(String ID, Object VAL) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q1 = depiler();
        if (!ID.equals(q1.getID())) {
            affecterVal(ID, VAL);
            empiler(q1);
        } else if (q1.getOBJ().equals(NatureObjet.VCST)) {
            Quad q2 = tds.creerSymboles(q1.getID(), VAL, NatureObjet.CST, q1.getSORTE());
            empilerNoCreate(q2);
        } else if (!q1.getOBJ().equals(NatureObjet.CST)) {
            Quad q3 = tds.creerSymboles(ID, VAL, q1.getOBJ(), q1.getSORTE());
            empilerNoCreate(q3);
        } else if (q1.getOBJ().equals(NatureObjet.TAB)) {
            int addr = ajouterRef(VAL);
            if (addr >= 0) {
                Quad q4 = tds.creerSymboles(ID, addr, q1.getOBJ(), q1.getSORTE());
                retirerTas(q1.getVAL());
                empilerNoCreate(q4);
            }
        } else {  // Meth
            Quad q5 = tds.creerSymboles(ID, VAL, q1.getOBJ(), q1.getSORTE());
            empilerNoCreate(q5);
        }
    }

    // Ajout d'une référence à un emplacement dans le tas, stockée dans l'attribut VAL d'un Quad de nature TAB
    // Ret: adresse dans le tas
    // Ret Err: -1 = Problème mémoire -2 = VAL pas entier
    public int ajouterRef(Object VAL) {
        if (VAL instanceof Integer) {
            return tas.allouer((Integer) VAL);
        }
        return -2;
    }

    // Désallocation de l'espace mémoire dans le tas associé à l'adresse VAL
    public void retirerTas(Object VAL) {
        if (VAL instanceof Integer) {
            tas.liberer((Integer) VAL);
        }
    }

    // Affectation d'une valeur à une case d'un tableau déjà existant
    public void affecterValT(String ID, Object VAL, int INDEX) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q1 = depilerNoRemove();
        if (!ID.equals(q1.getID())) {
            affecterValT(ID, VAL, INDEX);
        }
        else {
            affecterTas(q1.getVAL(), INDEX, VAL);
        }
        empilerNoCreate(q1);
    }

    // VALT: Adresse du tableau dans le tas | VAL: Valeur à affecter à l'index INDEX du tableau
    public void affecterTas(Object VALT, int INDEX, Object VAL) {
        if (VALT instanceof Integer) {
            tas.ecrire((Integer) VALT, INDEX, VAL);
        }
    }

    public Object val(String ID) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
        Quad quadV = tds.chercheQuad(ID);
        if (quadV != null) {
            return quadV.getVAL();
        }
        return "NOK_";
    }

    public Object valT(String ID, int INDEX) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + ID + ".");
        }
        Quad quadV = tds.chercheQuad(ID);
        if (quadV != null) {
            return tas.lire((Integer) quadV.getVAL(), INDEX);
        }
        return null;
    }

    public NatureObjet object(String ID) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la nature objet de l'élément " + ID + ".");
        }
        Quad quadO = tds.chercheQuad(ID);
        if (quadO != null) {
            return quadO.getOBJ();
        }
        return null;
    }

    public Sorte sorte(String ID) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la sorte de l'élément " + ID + ".");
        }
        Quad quadS = tds.chercheQuad(ID);
        if (quadS != null) {
            return quadS.getSORTE();
        }
        return null;
    }

    public Object parametre(String s) {
        Quad qID = tds.chercheQuad(s);
        if(qID==null || qID.getOBJ() != NatureObjet.METH) {
            return null;
        }
        return  qID.getVAL();
    }

    public boolean affecterType(String ID, Sorte SORTE) throws PileException {
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
        StringBuilder pileQuadString = new StringBuilder();
        if (isEmpty()) {
            System.out.println("IS EMPTY");
            return "";
        }
        pileQuadString.append(stackTop.toString());
        Quad next = stackTop;
        while (next.getBottomQuad() != null) {
            pileQuadString.append(".").append(next.getBottomQuad());
            next = next.getBottomQuad();
        }
        System.out.println("FIN TS");
        return pileQuadString.toString();
    }
}
