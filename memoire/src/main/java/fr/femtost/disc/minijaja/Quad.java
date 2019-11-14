package fr.femtost.disc.minijaja;

import java.util.LinkedList;

public class Quad {

    private String ID;
    private Object VAL;
    private NatureObjet OBJ;
    private Sorte SORTE;

    private Quad topQuad;
    private Quad bottomQuad;

    public Quad (String ID, Object VAL, NatureObjet OBJ, Sorte SORTE){
        this.ID = ID;
        this.VAL = VAL;
        this.OBJ = OBJ;
        this.SORTE = SORTE;
    }

    public String getID() {
        return ID;
    }

    public Object getVAL() {
        return VAL;
    }

    public NatureObjet getOBJ() {
        return OBJ;
    }

    public Sorte getSORTE() {
        return SORTE;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setVAL(Object VAL) {
        this.VAL = VAL;
    }

    public void setOBJ(NatureObjet OBJ) {
        this.OBJ = OBJ;
    }

    public void setSORTE(Sorte SORTE) {
        this.SORTE = SORTE;
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
