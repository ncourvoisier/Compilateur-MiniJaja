package fr.femtost.disc.minijaja;

public class Memoire {

    private Tas tas;
    private Pile pile;

    public Memoire(int tailleTas) {
        tas = new Tas(tailleTas);
        pile = new Pile(tas);
    }

    public Pile getPile() {
        return pile;
    }

    public Tas getTas() {
        return tas;
    }
}
