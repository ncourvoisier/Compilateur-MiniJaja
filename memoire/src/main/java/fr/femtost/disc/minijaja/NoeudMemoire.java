package fr.femtost.disc.minijaja;

public class NoeudMemoire {

    int taille;
    int tailleDisponible;
    int adresse;

    boolean disponible;

    NoeudMemoire droit;
    NoeudMemoire gauche;

    NoeudMemoire parent;

    public NoeudMemoire(int adresse, int taille, boolean disponible, NoeudMemoire parent) {
        this.taille = taille;
        if (disponible) {
            this.tailleDisponible = taille;
        }
        else {
            this.tailleDisponible = 0;
        }
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

        if (this.tailleDisponible < tailleAAllouer) {
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

            NoeudMemoire noeudNouveau;

            if (taille == tailleAAllouer) { // La taille à allouer est exactement celle disponible sur le noeud, pas de création de nouveaux noeuds
                disponible = false;
                tailleDisponible = 0;
                noeudNouveau = this;
            }
            else {
                noeudNouveau = new NoeudMemoire(adresse, tailleAAllouer, false, this);
                NoeudMemoire noeudDisponible = new NoeudMemoire(adresse + tailleAAllouer, taille - tailleAAllouer, true, this);
                if (noeudNouveau.taille <= noeudDisponible.taille) {
                    gauche = noeudNouveau;
                    droit = noeudDisponible;
                } else {
                    noeudNouveau.adresse = adresse + noeudDisponible.taille; // Le noeud mémoire dispo est plus petit que la mémoire alloué, donc il vient à gauche
                    noeudDisponible.adresse = adresse;
                    gauche = noeudDisponible;
                    droit = noeudNouveau;
                }
            }
            if (parent != null) {  // On lance la propagation à partir du parent, si pas racine
                parent.propagationTailleDisponile(-noeudNouveau.taille);
            }
            else { // si racine, propagation directe
                propagationTailleDisponile(-noeudNouveau.taille);
            }
            return noeudNouveau.adresse;
        } else {
            return -1; // Pas de mémoire disponible (trop fragmentée)
        }
    }

    void propagationTailleDisponile(int tailleAPropapger) {
        tailleDisponible += tailleAPropapger;
        if (parent != null) {
            parent.propagationTailleDisponile(tailleAPropapger);
        }
    }

    public void suppressionMemoire(int adresse, NoeudMemoire courant) {
        if (adresse >= taille) {
            return;
        }
        if (adresse < 0) {
            return;
        }
        suppressionMemoireReccursive(adresse, courant);
    }

    public void suppressionMemoireReccursive(int adresse, NoeudMemoire courant) {
        if(courant.adresse == adresse && !courant.disponible) {
            courant.disponible = true;
            courant.tailleDisponible = courant.taille;
            if (courant.parent != null) {  // On lance la propagation à partir du parent, si pas racine
                courant.parent.propagationTailleDisponile(courant.taille);
                courant.parent.fusionNoeudsDisponibles();
            }
            else { // si racine, propagation directe
                propagationTailleDisponile(courant.taille);
            }
            return;
        }
        if (courant.gauche != null) {
            if (courant.adresse + courant.gauche.taille - adresse > 0) {
                suppressionMemoireReccursive(adresse, courant.gauche);
                return;
            }
        }

        if (courant.droit != null) {
            suppressionMemoireReccursive(adresse, courant.droit);
            return;
        }

    }

    public boolean estUneFeuille() {
        return gauche == null && droit == null;
    }

    public void fusionNoeudsDisponibles() {
        if (gauche == null || droit == null) {
            return;  // Corrupted tree exception ?
        }
        if (gauche.estUneFeuille() && gauche.disponible && droit.estUneFeuille() && droit.disponible) {
            gauche = null;
            droit = null;
            if (parent != null) {
                parent.fusionNoeudsDisponibles();
            }
        }
    }

    public int getTaille() {
        return taille;
    }

    public boolean estDisponible() {
        return disponible;
    }

}
