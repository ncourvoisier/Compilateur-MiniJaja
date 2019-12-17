package fr.femtost.disc.minijaja;

import java.util.HashMap;
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
            if ((ID == null && linkedList.getFirst().getID() == null) || linkedList.getFirst().getID().equals(ID)) {
                linkedList.removeFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return;
                }
                for (int i = 1; i < taille; i++) {
                    if (linkedList.get(i).getID().equals(ID)) {
                        linkedList.remove(i);
                    }
                }
            }
            if (linkedList.isEmpty()) {
                tableSymbole.remove(ID);
            }
        }
    }
    //Overide with Type
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
                    if (linkedList.get(i).getID().equals(ID)) {
                        return linkedList.get(i);
                    }
                }
            }
        }
        return null;
    }

    public Quad chercheQuad(String ID,Sorte No) {
        LinkedList<Quad> linkedList = tableSymbole.get(ID);
        if(linkedList != null) {
            if (linkedList.getFirst().getID().equals(ID) && linkedList.getFirst().getSORTE().equals(No)) {
                return linkedList.getFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return null;
                }
                for (int i = 1; i < taille; i++) {
                    if (linkedList.get(i).getID().equals(ID) && linkedList.getFirst().getSORTE().equals(No)) {
                        return linkedList.get(i);
                    }
                }
            }
        }
        return null;
    }
}
