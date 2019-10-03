package fr.femtost.disc.minijaja;

public class NoeudMemoire {

    int taille;
    int adresse;

    boolean disponible;

    NoeudMemoire droit;
    NoeudMemoire gauche;

    NoeudMemoire parent;

    public NoeudMemoire(int adresse, int taille, boolean disponible, NoeudMemoire parent) {
        this.taille = taille;
        this.adresse = adresse;
        this.disponible = disponible;
        this.parent = parent;
        this.droit = null;
        this.gauche = null;
    }

    int allouerMemoire (int tailleAAllouer) {

        if (!disponible) {
            return -1;
        }

        if (this.taille < tailleAAllouer) {
            return -1;
        }

        int retAdresse = -1;

        if (gauche != null) {
            retAdresse = gauche.allouerMemoire(tailleAAllouer);
        }
        if (retAdresse > 0) {
            return retAdresse;
        }

        if (droit != null) {
            retAdresse = droit.allouerMemoire(tailleAAllouer);
        }
        if (retAdresse > 0) {
            return retAdresse;
        }

        if (droit == null && gauche == null) {
            NoeudMemoire noeudNouveau = new NoeudMemoire(adresse, tailleAAllouer, false, this);
            NoeudMemoire noeudDisponible = new NoeudMemoire(adresse+tailleAAllouer, taille - tailleAAllouer, true, this);
            if (noeudNouveau.taille <= noeudDisponible.taille) {
                gauche = noeudNouveau;
                droit = noeudDisponible;
            } else {
                noeudNouveau.adresse = adresse + noeudDisponible.taille; // Le noeud mémoire dispo est plus petit que la mémoire alloué, donc il vient à gauche
                noeudDisponible.adresse = adresse;
                gauche = noeudDisponible;
                droit = noeudNouveau;
            }
            propagationTailleDisponile(noeudNouveau.taille);
            return noeudNouveau.adresse;
        } else {
            return -1; // Pas de mémoire disponible (trop fragmentée)
        }
    }

    void propagationTailleDisponile(int tailleAPropapger) {
        taille -= tailleAPropapger;
        if (parent != null) {
            parent.propagationTailleDisponile(tailleAPropapger);
        }
    }

    public void suppressionMemoire(int adresse, NoeudMemoire courant) {
        if (adresse >= taille) {
            return;
        }
        if (adresse < 0 ) {
            return;
        }
        suppessionMemoireReccursive(adresse, courant);
    }

    public void suppessionMemoireReccursive(int adresse, NoeudMemoire courant) {
        if(courant.adresse == adresse && !disponible) {
            disponible = true;
            return;
        }
        if (courant.gauche != null) {
            if (courant.adresse + courant.gauche.taille - adresse > 0) {
                suppessionMemoireReccursive(adresse, courant.gauche);
                return;
            }
        }

        if (courant.droit != null) {
            suppessionMemoireReccursive(adresse, courant.droit);
            return;
        }

    }



}
