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

    private Quad depilerNoRemove() {
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

    public void declVar(String id, Object val, Sorte sorte) {
        Quad q = tds.creerSymboles(id, val, NatureObjet.VAR, sorte);
        empilerNoCreate(q);
    }

    public Quad returnQuadWithId(String id) {
        return tds.chercheQuad(id);
    }

    public void identVal(String id, Sorte sorte, int s) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide, impossible d'utiliser la fonction identVal.");
        }
        Quad q = depiler();
        if (s == 0) {
            empiler(new Quad(id, q.getVAL(), NatureObjet.VAR, sorte));
        } else {
            identVal(id, sorte, s-1);
            empiler(q);
        }
    }

    public void declCst(String id, Object val, Sorte sorte) {
        Quad q;
        if (val == null) {
            q = tds.creerSymboles(id, val, NatureObjet.VCST, sorte);
        } else {
            q = tds.creerSymboles(id, val, NatureObjet.CST, sorte);
        }
        empilerNoCreate(q);
    }

    public void declTab(String id, Object val, Sorte sorte) {
        int addr = ajouterRef(val);
        if (addr >= 0) {
            Quad q = tds.creerSymboles(id, addr, NatureObjet.TAB, sorte);
            empilerNoCreate(q);
        }
    }


    public void declMeth(String id, Object val, Sorte sorte) {
        Quad q = tds.creerSymboles(id, val, NatureObjet.METH, sorte);
        empilerNoCreate(q);
    }

    public void retirerDecl(String id) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q = stackTop;
        if (id.equals(q.getID())){
            if (q.getOBJ().equals(NatureObjet.TAB)) {
                retirerTas(q.getVAL());
            }
            depiler();
        } else {
            Quad qDepile = depilerNoRemove();
            retirerDecl(id);
            empilerNoCreate(qDepile);
        }
    }

    public void affecterVal(String id, Object val) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q1 = depiler();
        if (!id.equals(q1.getID())) {
            affecterVal(id, val);
            empiler(q1);
        } else if (q1.getOBJ().equals(NatureObjet.VCST)) {
            Quad q2 = tds.creerSymboles(q1.getID(), val, NatureObjet.CST, q1.getSORTE());
            empilerNoCreate(q2);
        } else if (q1.getOBJ().equals(NatureObjet.CST)) {
            Quad q3 = tds.creerSymboles(id, val, q1.getOBJ(), q1.getSORTE());
            empilerNoCreate(q3);
        } else if (q1.getOBJ().equals(NatureObjet.TAB)) {
            int addr = ajouterRef(val);
            if (addr >= 0) {
                Quad q4 = tds.creerSymboles(id, addr, q1.getOBJ(), q1.getSORTE());
                retirerTas(q1.getVAL());
                empilerNoCreate(q4);
            }
        } else {  // Meth
            Quad q5 = tds.creerSymboles(id, val, q1.getOBJ(), q1.getSORTE());
            empilerNoCreate(q5);
        }
    }

    // Ajout d'une référence à un emplacement dans le tas, stockée dans l'attribut VAL d'un Quad de nature TAB
    // Ret: adresse dans le tas
    // Ret Err: -1 = Problème mémoire -2 = VAL pas entier
    public int ajouterRef(Object val) {
        if (val instanceof Integer) {
            return tas.allouer((Integer) val);
        }
        return -2;
    }

    // Désallocation de l'espace mémoire dans le tas associé à l'adresse VAL
    public void retirerTas(Object val) {
        if (val instanceof Integer) {
            tas.liberer((Integer) val);
        }
    }

    // Affectation d'une valeur à une case d'un tableau déjà existant
    public void affecterValT(String id, Object val, int index) throws PileException {
        if (isEmpty()) {
            return;
        }
        Quad q1 = depilerNoRemove();
        if (!id.equals(q1.getID())) {
            affecterValT(id, val, index);
        }
        else {
            affecterTas(q1.getVAL(), index, val);
        }
        empilerNoCreate(q1);
    }

    // VALT: Adresse du tableau dans le tas | VAL: Valeur à affecter à l'index INDEX du tableau
    public void affecterTas(Object valt, int index, Object val) {
        if (valt instanceof Integer) {
            tas.ecrire((Integer) valt, index, val);
        }
    }

    public Object val(String id) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + id + ".");
        }
        Quad quadV = tds.chercheQuad(id);
        if (quadV != null) {
            return quadV.getVAL();
        }
        return "NOK_";
    }

    public Object valT(String id, int index) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la valeur de l'élément " + id + ".");
        }
        Quad quadV = tds.chercheQuad(id);
        if (quadV != null) {
            return tas.lire((Integer) quadV.getVAL(), index);
        }
        return null;
    }

    public NatureObjet object(String id) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la nature objet de l'élément " + id + ".");
        }
        Quad quadO = tds.chercheQuad(id);
        if (quadO != null) {
            return quadO.getOBJ();
        }
        return null;
    }

    public Sorte sorte(String id) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'obtenir la sorte de l'élément " + id + ".");
        }
        Quad quadS = tds.chercheQuad(id);
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

    public boolean affecterType(String id, Sorte sorte) throws PileException {
        if (isEmpty()) {
            throw new PileException("La pile est vide impossible d'affecter le type de l'ID : " + id + ".");
        }
        Quad qID = tds.chercheQuad(id);
        if (qID == null) {
            return false;
        }
        qID.setSORTE(sorte);
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
            return "";
        }
        pileQuadString.append(stackTop.toString());
        Quad next = stackTop;
        while (next.getBottomQuad() != null) {
            pileQuadString.append(".").append(next.getBottomQuad());
            next = next.getBottomQuad();
        }
        return pileQuadString.toString();
    }
}
