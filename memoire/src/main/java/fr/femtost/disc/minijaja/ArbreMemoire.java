package fr.femtost.disc.minijaja;

public class ArbreMemoire {

    private NoeudMemoire racine;
    private int tailleMax;

    public ArbreMemoire(int tailleMax) {
        this.tailleMax = tailleMax;
        this.racine = new NoeudMemoire(0, tailleMax, true, null);
    }

    public NoeudMemoire getRacine() {
        return racine;
    }

    public int getProfondeurArbre() {
        return profondeurArbre(racine);
    }

    public int profondeurArbre(NoeudMemoire courant) {
        if (courant == null) {
            return 0;
        }
        return Math.max(profondeurArbre(courant.droit), profondeurArbre(courant.gauche))+1;
    }

    public int allouerMemoire(int taille) {
        return racine.allouerMemoire(taille);
    }

    public int libererMemoire(int adresse) {
        return racine.suppressionMemoire(adresse, racine);
    }

    public NoeudMemoire getNoeudRecursif(int adresse, NoeudMemoire courant) {
        if(courant.adresse == adresse && courant.gauche == null && courant.droit == null) {
            return courant;
        }
        if (courant.gauche != null) {
            if (courant.adresse + courant.gauche.taille - adresse > 0) {
                return getNoeudRecursif(adresse, courant.gauche);
            }
        }
        if (courant.droit != null) {
            return getNoeudRecursif(adresse, courant.droit);
        }
        return null;
    }

    public NoeudMemoire getNoeud(int adresse) {
        if (adresse >= tailleMax) {
            return null;
        }
        if (adresse < 0 ) {
            return null;
        }
        return getNoeudRecursif(adresse, racine);
    }

    public String recursif(NoeudMemoire noeud) {
        if (noeud == null) {
            return "(-)";
        }
        return "(" + noeud.adresse + ", " + noeud.taille + ") [" + recursif(noeud.gauche) + ", " + recursif(noeud.droit) + "]";
    }

    @Override
    public String toString() {
        return recursif(racine);
    }
}
