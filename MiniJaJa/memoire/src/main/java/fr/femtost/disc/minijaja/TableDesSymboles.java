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

    public Quad creerSymboles(String id, Object val, NatureObjet obj, Sorte sorte) {
        Quad q = new Quad(id, val, obj, sorte);
        LinkedList<Quad> linkedList = tableSymbole.get(id);
        if (linkedList != null) {
            linkedList.addFirst(q);
        }
        else {
            linkedList = new LinkedList<>();
            linkedList.addFirst(q);
            tableSymbole.put(id, linkedList);
        }
        return q;
    }

    public void enleverSymbole(String id) {
        LinkedList<Quad> linkedList = tableSymbole.get(id);
        if(linkedList != null) {
            if ((id == null && linkedList.getFirst().getID() == null) || linkedList.getFirst().getID().equals(id)) {
                linkedList.removeFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return;
                }
                for (int i = 1; i < taille; i++) {
                    if (linkedList.get(i).getID().equals(id)) {
                        linkedList.remove(i);
                    }
                }
            }
            if (linkedList.isEmpty()) {
                tableSymbole.remove(id);
            }
        }
    }
    //Overide with Type
    public Quad chercheQuad(String id) {
        LinkedList<Quad> linkedList = tableSymbole.get(id);
        if(linkedList != null) {
            if (linkedList.getFirst().getID().equals(id)) {
                return linkedList.getFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return null;
                }
                for (int i = 1; i < taille; i++) {
                    if (linkedList.get(i).getID().equals(id)) {
                        return linkedList.get(i);
                    }
                }
            }
        }
        return null;
    }

    public Quad chercheQuad(String id,Sorte no) {
        LinkedList<Quad> linkedList = tableSymbole.get(id);
        if(linkedList != null) {
            if (linkedList.getFirst().getID().equals(id) && linkedList.getFirst().getSORTE().equals(no)) {
                return linkedList.getFirst();
            }
            else {
                int taille = linkedList.size();
                if (taille < 2) {
                    return null;
                }
                for (int i = 1; i < taille; i++) {
                    if (linkedList.get(i).getID().equals(id) && linkedList.getFirst().getSORTE().equals(no)) {
                        return linkedList.get(i);
                    }
                }
            }
        }
        return null;
    }
}
