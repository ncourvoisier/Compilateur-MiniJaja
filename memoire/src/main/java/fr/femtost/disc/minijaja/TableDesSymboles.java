package fr.femtost.disc.minijaja;

import java.util.HashMap;
import java.util.Map;

public class TableDesSymboles {

    Map<String, Quad> tableSymbole;

    public TableDesSymboles() {
        this.tableSymbole = new HashMap<>();
    }



    Quad creerSymboles(String ID, Object VAL, NatureObjet OBJ, Sorte SORTE) {
        Quad q = new Quad(ID, VAL, OBJ, SORTE);
        tableSymbole.put(ID, q);
        return q;
    }

    Quad chercheQuad(String ID) {
        return tableSymbole.get(ID);
    }



}
