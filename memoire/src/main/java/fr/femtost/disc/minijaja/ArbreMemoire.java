package fr.femtost.disc.minijaja;

public class ArbreMemoire {

    private NoeudMemoire racine;
    private int tailleMax;

    public ArbreMemoire(int tailleMax)
    {
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
        System.out.println("adresse : " + adresse + ", courant : " + courant.adresse);
        //System.out.println("Adresse courante: " + courant.adresse+ ", " + courant.gauche + ", " + courant.droit);
        if(courant.adresse == adresse && courant.gauche == null && courant.droit == null) {
            return courant;
        }
        if (courant.gauche != null) {
            System.out.println("essaye à gauche...");
            //System.out.println("adresse courante : " + courant.adresse  + " ,taille gauche: " + courant.gauche.taille + ", adresse: " + adresse);
            if (courant.adresse + courant.gauche.taille - adresse > 0) {
                System.out.println("Va à gauche car : " + courant.adresse + " + " + courant.gauche.taille + " - " + adresse + " > 0");
                return getNoeudRecursif(adresse, courant.gauche);
            }
        }
        if (courant.droit != null) {
            System.out.println("Va à droite");
            return getNoeudRecursif(adresse, courant.droit);
        }
        return null;
    }

    public NoeudMemoire getNoeud(int adresse) {
        if (adresse >= tailleMax) {
            System.out.println(adresse + " >= " + racine.taille);
            return null;
        }
        if (adresse < 0 ) {
            System.out.println("adresse < 0");
            return null;
        }
        return getNoeudRecursif(adresse, racine);
    }

}
