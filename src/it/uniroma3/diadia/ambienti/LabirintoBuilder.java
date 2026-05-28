package it.uniroma3.diadia.ambienti;
import java.util.LinkedHashMap;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class LabirintoBuilder {

    private final Labirinto labirinto;
    private final Map<String, Stanza> stanze;   // tutte le stanze gia' create, per nome
    private Stanza ultimaStanza;                // ultima stanza aggiunta (per addAttrezzo)

    public LabirintoBuilder() {
        this.labirinto = new Labirinto(true);
        this.stanze = new LinkedHashMap<>();
    }

    /* ---------- stanze ---------- */

    public LabirintoBuilder addStanza(String nome) {
        return aggiungi(new Stanza(nome));
    }

    public LabirintoBuilder addStanzaIniziale(String nome) {
        aggiungi(new Stanza(nome));
        this.labirinto.setStanzaIniziale(this.ultimaStanza);
        return this;
    }

    public LabirintoBuilder addStanzaVincente(String nome) {
        aggiungi(new Stanza(nome));
        this.labirinto.setStanzaVincente(this.ultimaStanza);
        return this;
    }

    public LabirintoBuilder addStanzaBuia(String nome, String attrezzoNecessario) {
        return aggiungi(new StanzaBuia(nome, attrezzoNecessario));
    }

    public LabirintoBuilder addStanzaBloccata(String nome, String direzioneBloccata, String attrezzoSbloccante) {
        return aggiungi(new StanzaBloccata(nome, direzioneBloccata, attrezzoSbloccante));
    }

    public LabirintoBuilder addStanzaMagica(String nome) {
        return aggiungi(new StanzaMagica(nome));
    }

    public LabirintoBuilder addStanzaMagica(String nome, int soglia) {
        return aggiungi(new StanzaMagica(nome, soglia));
    }

    /* ---------- adiacenze e attrezzi ---------- */

    /** Collega due stanze già aggiunte. */
    public LabirintoBuilder addAdiacenza(String da, String a, String direzione) {
        Stanza sDa = stanze.get(da);
        Stanza sA  = stanze.get(a);
        if (sDa == null || sA == null)
            throw new IllegalArgumentException("Stanza non trovata: " + da + " o " + a);
        sDa.impostaStanzaAdiacente(direzione, sA);
        return this;
    }

    /** Aggiunge un attrezzo all'ultima stanza creata. */
    public LabirintoBuilder addAttrezzo(String nome, int peso) {
        if (this.ultimaStanza == null)
            throw new IllegalStateException("Aggiungi prima una stanza");
        this.ultimaStanza.addAttrezzo(new Attrezzo(nome, peso));
        return this;
    }

    /* ---------- risultato ---------- */

    public Labirinto getLabirinto() {
        return this.labirinto;
    }

    /** Mappa di tutte le stanze finora aggiunte, indicizzate per nome. */
    public Map<String, Stanza> getListaStanze() {
        return this.stanze;
    }

    /* ---------- helper privato ---------- */

    private LabirintoBuilder aggiungi(Stanza s) {
        this.stanze.put(s.getNome(), s);
        this.ultimaStanza = s;
        return this;
    }
}
