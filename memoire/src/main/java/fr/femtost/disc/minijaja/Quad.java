package fr.femtost.disc.minijaja;

import org.omg.CORBA.OBJ_ADAPTER;
import sun.rmi.transport.ObjectTable;

public class Quad {

    String ID;
    Object VAL; // <-- amener a etre modifié
    NatureObjet OBJ;
    String SORTE;


    public Quad (String ID, Object VAL, NatureObjet OBJ, String SORTE){
        this.ID = ID;
        this.VAL = VAL;
        this.OBJ = OBJ;
        this.SORTE = SORTE;
    }





}
