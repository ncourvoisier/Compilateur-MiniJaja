package fr.femtost.disc.minijaja;

public class ArbreMemoire {

    private NoeudMemoire racine;

    public ArbreMemoire(int tailleMemoire) {
        this.racine = new NoeudMemoire(0, tailleMemoire, true, null);
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

}
