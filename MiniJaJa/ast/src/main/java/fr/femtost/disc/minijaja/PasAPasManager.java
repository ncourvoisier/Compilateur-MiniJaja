package fr.femtost.disc.minijaja;

import java.util.HashSet;
import java.util.Set;

public class PasAPasManager {

    private Set<Integer> breakPoints;
    EtatArret arret;
    Memoire memoire;

    public PasAPasManager() {
        this.breakPoints = new HashSet<>();
    }

    public void setBreakPoints(Set<Integer> breakPoints) {
        this.breakPoints = breakPoints;
    }

    public void launch() {
        int currentIndex;
        if (arret == null) {
            currentIndex = 0;
        }
        else {
            currentIndex = arret.getIndex();
        }
        //arret = arret.getNode().interpreterPasAPas(memoire, currentIndex, breakPoints);
    }
}
