package fr.femtost.disc.minijaja;

import java.util.ArrayList;
import java.util.List;

public class TasInfos {

    public class BlocInfos {

        private int adresse;
        private int taille;
        private boolean disponible;

        public BlocInfos(int adresse, int taille, boolean disponible) {
            this.adresse = adresse;
            this.taille = taille;
            this.disponible = disponible;
        }

        public int getAdresse() {
            return adresse;
        }

        public int getTaille() {
            return taille;
        }

        public boolean estDisponible() {
            return disponible;
        }

        @Override
        public String toString() {
            String stringBloc = "[" + adresse + "->" + (adresse+taille-1) + ":";
            if (disponible) {
                stringBloc += "-";
            }
            else {
                stringBloc += "A";
            }
            stringBloc += "]";
            return stringBloc;
        }
    }

    private int taille;
    private int tailleDisponible;
    private int nombreBlocs;
    private int tailleMaximaleAllouable;
    private List<BlocInfos> blocs;

    public TasInfos(ArbreMemoire arbre) {
        blocs = new ArrayList<>();
        extractInfos(arbre);
    }

    private void extractInfos(ArbreMemoire arbre) {
        NoeudMemoire racine = arbre.getRacine();
        taille = racine.getTaille();
        tailleDisponible = racine.getTailleDisponible();
        tailleMaximaleAllouable = 0;
        nombreBlocs = 0;
        extractInfosRecursif(racine);
    }

    private void extractInfosRecursif(NoeudMemoire noeud) {
        if (noeud.estUneFeuille()) {
            if (noeud.tailleDisponible > tailleMaximaleAllouable) {
                tailleMaximaleAllouable = noeud.tailleDisponible;
            }
            nombreBlocs++;
            blocs.add(new BlocInfos(noeud.adresse, noeud.taille, noeud.disponible));
            return;
        }
        extractInfosRecursif(noeud.gauche);
        extractInfosRecursif(noeud.droit);
    }

    public int getTaille() {
        return taille;
    }

    public int getTailleDisponible() {
        return tailleDisponible;
    }

    public int getNombreBlocs() {
        return nombreBlocs;
    }

    public int getTailleMaximaleAllouable() {
        return tailleMaximaleAllouable;
    }

    public List<BlocInfos> getBlocs() {
        return blocs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BlocInfos bloc : blocs) {
            sb.append(bloc.toString());
        }
        return sb.toString();
    }
}
