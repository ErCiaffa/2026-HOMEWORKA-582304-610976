package it.uniroma3.diadia.ambienti;

/**
 * Le direzioni possibili di un'uscita di una {@link Stanza}.
 * Sostituisce le stringhe magiche ("nord", "sud", ...) con un tipo finito e type-safe.
 */
public enum Direzione {
    NORD, SUD, EST, OVEST;

    /** Restituisce la direzione opposta (utile per le adiacenze bidirezionali). */
    public Direzione opposta() {
        switch (this) {
            case NORD:  return SUD;
            case SUD:   return NORD;
            case EST:   return OVEST;
            case OVEST: return EST;
            default:    throw new IllegalStateException();
        }
    }

    /**
     * Converte una stringa (case-insensitive) nella Direzione corrispondente.
     * @return la Direzione, oppure null se la stringa non e' una direzione valida.
     */
    public static Direzione fromString(String s) {
        if (s == null)
            return null;
        try {
            return valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
