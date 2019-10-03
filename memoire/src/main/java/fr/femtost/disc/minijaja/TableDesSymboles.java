package fr.femtost.disc.minijaja;

import java.util.HashMap;
import java.util.Map;

public class TableDesSymboles {

    static Map<String, Quad> tableSymbole;

    public TableDesSymboles() {
        this.tableSymbole = new HashMap<>();
    }



    static Quad creerSymboles(String ID, Object VAL, NatureObjet OBJ, String SORTE) {
        Quad q = new Quad(ID, VAL, OBJ, SORTE);
        tableSymbole.put(ID, q);
        return q;
    };




}
