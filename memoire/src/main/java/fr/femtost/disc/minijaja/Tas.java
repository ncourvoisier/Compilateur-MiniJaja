package fr.femtost.disc.minijaja;

//import sun.reflect.generics.tree.Tree;

public class Tas {

    private ArbreMemoire arbre;
    private Object[] memoire;

    public Tas(int taille) {
        memoire = new Object[taille];
        arbre = new ArbreMemoire(taille);
    }

    public int allouer(int taille) {
        return arbre.allouerMemoire(taille);
    }

    public void liberer(int adresse) {
        arbre.libererMemoire(adresse);
    }

    public int ecrire(int adresse, int decalage, Object valeur) {
        int emplacement = emplacementValide(adresse, decalage);
        if (emplacement < 0) {
            return emplacement;
        }
        memoire[emplacement] = valeur;
        return 0;
    }

    public Object lire(int adresse, int decalage) {
        int emplacement = emplacementValide(adresse, decalage);
        if (emplacement < 0) {
            return null;
        }
        return memoire[emplacement];
    }

    private int emplacementValide(int adresse, int decalage) {
        NoeudMemoire noeud = arbre.getNoeud(adresse);
        if (noeud == null) {
            return -2;
        }
        if (noeud.estDisponible()) {
            return -3;
        }
        if (decalage < 0) {
            return -1;
        }
        if (decalage >= noeud.getTaille()) {
            return -1;
        }
        return adresse + decalage;
    }

}
