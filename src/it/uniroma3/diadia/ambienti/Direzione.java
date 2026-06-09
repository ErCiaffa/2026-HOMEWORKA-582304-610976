package it.uniroma3.diadia.ambienti;

public enum Direzione {
    NORD, SUD, EST, OVEST;

    public Direzione opposta() {
        return switch (this) {
            case NORD -> SUD;
            case SUD -> NORD;
            case EST -> OVEST;
            case OVEST -> EST;
        };
    }
}
