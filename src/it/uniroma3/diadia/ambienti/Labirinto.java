package it.uniroma3.diadia.ambienti;

import java.util.LinkedHashMap;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

/**
 * Un labirinto e' definito dalla sua stanza iniziale e dalla sua stanza vincente.
 * Si costruisce esclusivamente tramite il {@link LabirintoBuilder} (Builder pattern,
 * Effective Java): il costruttore e' privato e l'unico punto d'ingresso e'
 * {@link #newBuilder()}.
 */
public class Labirinto {

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;

	private Labirinto() {
		// l'inizializzazione e' delegata al LabirintoBuilder
	}

	/** Unico punto di accesso pubblico per costruire un Labirinto. */
	public static LabirintoBuilder newBuilder() {
		return new LabirintoBuilder();
	}

	/** Labirinto di default del gioco, cablato tramite il Builder. */
	public static Labirinto creaLabirintoDiDefault() {
		return newBuilder()
				.addStanzaIniziale("Atrio")
					.addAttrezzo("osso", 1)
				.addStanza("Aula N11")
				.addStanza("Aula N10")
					.addAttrezzo("lanterna", 3)
				.addStanza("Laboratorio Campus")
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("Atrio", "Biblioteca", "nord")
				.addAdiacenza("Atrio", "Aula N11", "est")
				.addAdiacenza("Atrio", "Aula N10", "sud")
				.addAdiacenza("Atrio", "Laboratorio Campus", "ovest")
				.addAdiacenza("Aula N11", "Atrio", "ovest")
				.addAdiacenza("Aula N10", "Atrio", "nord")
				.addAdiacenza("Aula N10", "Aula N11", "est")
				.addAdiacenza("Aula N10", "Laboratorio Campus", "ovest")
				.addAdiacenza("Laboratorio Campus", "Atrio", "est")
				.addAdiacenza("Laboratorio Campus", "Aula N11", "ovest")
				.addAdiacenza("Biblioteca", "Atrio", "sud")
				.getLabirinto();
	}

	public Stanza getStanzaIniziale() {
		return stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}

	/**
	 * Costruttore fluente di {@link Labirinto}.
	 * E' una classe statica nidificata: il legame con Labirinto e' esplicito
	 * (Labirinto.LabirintoBuilder) e puo' accedere ai membri privati di Labirinto.
	 */
	public static class LabirintoBuilder {

		private final Labirinto labirinto;
		private final Map<String, Stanza> stanze;   // tutte le stanze gia' create, per nome
		private Stanza ultimaStanza;                 // ultima stanza aggiunta (per addAttrezzo)

		private LabirintoBuilder() {
			this.labirinto = new Labirinto();
			this.stanze = new LinkedHashMap<>();
		}

		/* ---------- stanze ---------- */

		public LabirintoBuilder addStanza(String nome) {
			return aggiungi(new Stanza(nome));
		}

		public LabirintoBuilder addStanzaIniziale(String nome) {
			aggiungi(new Stanza(nome));
			this.labirinto.stanzaIniziale = this.ultimaStanza;
			return this;
		}

		public LabirintoBuilder addStanzaVincente(String nome) {
			aggiungi(new Stanza(nome));
			this.labirinto.stanzaVincente = this.ultimaStanza;
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

		/** Contrassegna come iniziale una stanza gia' aggiunta. */
		public LabirintoBuilder contrassegnaIniziale(String nome) {
			this.labirinto.stanzaIniziale = richiediStanza(nome);
			return this;
		}

		/** Contrassegna come vincente una stanza gia' aggiunta. */
		public LabirintoBuilder contrassegnaVincente(String nome) {
			this.labirinto.stanzaVincente = richiediStanza(nome);
			return this;
		}

		/* ---------- adiacenze, attrezzi e personaggi ---------- */

		/**
		 * Collega due stanze già aggiunte. Una direzione non valida viene
		 * ignorata senza errori (semantica dei test di riferimento del corso:
		 * l'adiacenza semplicemente non viene aggiunta).
		 */
		public LabirintoBuilder addAdiacenza(String da, String a, String direzione) {
			richiediStanza(da).impostaStanzaAdiacente(Direzione.fromString(direzione), richiediStanza(a));
			return this;
		}

		/** Collegamento bidirezionale tra due stanze. */
		public LabirintoBuilder addAdiacenzaAvanzata(String da, String a, String direzione) {
			Direzione dir = parseDirezione(direzione);
			Stanza sDa = richiediStanza(da);
			Stanza sA = richiediStanza(a);
			sDa.impostaStanzaAdiacente(dir, sA);
			sA.impostaStanzaAdiacente(dir.opposta(), sDa);
			return this;
		}

		/** Aggiunge un attrezzo all'ultima stanza creata. */
		public LabirintoBuilder addAttrezzo(String nome, int peso) {
			if (this.ultimaStanza == null)
				throw new IllegalStateException("Aggiungi prima una stanza");
			this.ultimaStanza.addAttrezzo(new Attrezzo(nome, peso));
			return this;
		}

		/** Aggiunge un attrezzo a una stanza specifica (gia' aggiunta). */
		public LabirintoBuilder addAttrezzo(String nomeStanza, String nomeAttrezzo, int peso) {
			richiediStanza(nomeStanza).addAttrezzo(new Attrezzo(nomeAttrezzo, peso));
			return this;
		}

		/** Colloca un personaggio in una stanza gia' aggiunta. */
		public LabirintoBuilder addPersonaggio(String nomeStanza, AbstractPersonaggio personaggio) {
			richiediStanza(nomeStanza).setPersonaggio(personaggio);
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

		/* ---------- helper privati ---------- */

		private LabirintoBuilder aggiungi(Stanza s) {
			this.stanze.put(s.getNome(), s);
			this.ultimaStanza = s;
			return this;
		}

		private Stanza richiediStanza(String nome) {
			Stanza s = this.stanze.get(nome);
			if (s == null)
				throw new IllegalArgumentException("Stanza non trovata: " + nome);
			return s;
		}

		private Direzione parseDirezione(String direzione) {
			Direzione d = Direzione.fromString(direzione);
			if (d == null)
				throw new IllegalArgumentException("Direzione non valida: " + direzione);
			return d;
		}
	}
}
