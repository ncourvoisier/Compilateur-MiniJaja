package fr.femtost.disc.minijaja;

public class Quad {

    private String id;
    private Object val;
    private NatureObjet obj;
    private Sorte sorte;

    private Quad topQuad;
    private Quad bottomQuad;

    public Quad (String id, Object val, NatureObjet obj, Sorte sorte){
        this.id = id;
        this.val = val;
        this.obj = obj;
        this.sorte = sorte;
    }

    public String getID() {
        return id;
    }

    public Object getVAL() {
        return val;
    }

    public NatureObjet getOBJ() {
        return obj;
    }

    public Sorte getSORTE() {
        return sorte;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setVAL(Object val) {
        this.val = val;
    }

    public void setOBJ(NatureObjet obj) {
        this.obj = obj;
    }

    public void setSORTE(Sorte sorte) {
        this.sorte = sorte;
    }

    public String toString() {
        String q = "<";
        q += getID() + ", ";
        q += getVAL() + ", ";
        q += getOBJ() + ", ";
        q += getSORTE() + ">";
        return q;
    }

    public Quad getTopQuad() {
        return topQuad;
    }

    public void setTopQuad(Quad topQuad) {
        this.topQuad = topQuad;
    }

    public Quad getBottomQuad() {
        return bottomQuad;
    }

    public void setBottomQuad(Quad bottomQuad) {
        this.bottomQuad = bottomQuad;
    }
}
