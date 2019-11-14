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

    public void libererMemoire(int adresse) {
        racine.suppressionMemoire(adresse, racine);
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
        System.out.println("Ah merde : Addr courante : " + courant.adresse + ", adresse : " + adresse + ", gauche : " + courant.gauche + ", droit : " + courant.droit + ", taille : " + courant.taille);
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

}
