package fr.femtost.disc.minijaja;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class TableDesSymboles {

    private Map<String, LinkedList<Quad>> tableSymbole;

    public Map<String, LinkedList<Quad>> getTableSymbole() {
        return tableSymbole;
    }

    public TableDesSymboles() {
        this.tableSymbole = new HashMap<>();
    }

    public Quad creerSymboles(String ID, Object VAL, NatureObjet OBJ, Sorte SORTE) {
        Quad q = new Quad(ID, VAL, OBJ, SORTE);
        LinkedList<Quad> linkedList = tableSymbole.get(ID);
        if (linkedList != null) {
            linkedList.addFirst(q);
        }
        else {
            linkedList = new LinkedList<>();
            linkedList.addFirst(q);
            tableSymbole.put(ID, linkedList);
        }
        return q;
    }

    public void enleverSymbole(String ID) {
        LinkedList<Quad> linkedList = tableSymbole.get(ID);
        if(linkedList != null) {
            if (linkedList.getFirst().getID().equals(ID)) {
                linkedList.removeFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return;
                }
                for (int i = 1; i < taille; i++) {
                    Quad q = linkedList.get(i);
                    if (q.equals(ID)) {
                        linkedList.remove(i);
                    }
                }
            }
        }
    }

    public Quad chercheQuad(String ID) {
        LinkedList<Quad> linkedList = tableSymbole.get(ID);
        if(linkedList != null) {
            if (linkedList.getFirst().getID().equals(ID)) {
                return linkedList.getFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return null;
                }
                for (int i = 1; i < taille; i++) {
                    Quad q = linkedList.get(i);
                    if (q.equals(ID)) {
                        return linkedList.get(i);
                    }
                }
            }
        }
        return null;
    }


}
