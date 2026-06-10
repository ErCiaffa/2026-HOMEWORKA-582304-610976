import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.personaggi.Strega;

/**
 * Comportamento prescritto dalle trasparenze POO-classi-astratte-enum:
 * la strega trasferisce il giocatore in una stanza adiacente; se non
 * l'abbiamo ancora salutata, in quella con MENO attrezzi, altrimenti
 * in quella con PIU' attrezzi.
 */
class StregaTest {

	private Partita partita;
	private Strega strega;

	@BeforeEach
	void setUp() {
		Labirinto labirinto = Labirinto.newBuilder()
				.addStanzaIniziale("atrio")
				.addStanza("povera")                       // 0 attrezzi
				.addStanza("ricca")
					.addAttrezzo("oro", 1)
					.addAttrezzo("gemma", 1)               // 2 attrezzi
				.addStanzaVincente("uscita")
				.addAdiacenza("atrio", "povera", "nord")
				.addAdiacenza("atrio", "ricca", "sud")
				.getLabirinto();
		partita = new Partita(labirinto);
		strega = new Strega("Morgana", "Sono Morgana.");
		partita.getStanzaCorrente().setPersonaggio(strega);
	}

	@Test
	void senzaSaluto_trasferisceNellaStanzaConMenoAttrezzi() {
		strega.agisci(partita);
		assertEquals("povera", partita.getStanzaCorrente().getNome());
	}

	@Test
	void dopoIlSaluto_trasferisceNellaStanzaConPiuAttrezzi() {
		strega.saluta();
		strega.agisci(partita);
		assertEquals("ricca", partita.getStanzaCorrente().getNome());
	}

	@Test
	void senzaStanzeAdiacenti_nonSpostaIlGiocatore() {
		Labirinto monolocale = Labirinto.newBuilder()
				.addStanzaIniziale("isolata")
				.addStanzaVincente("isolata")
				.getLabirinto();
		Partita partitaIsolata = new Partita(monolocale);
		strega.agisci(partitaIsolata);
		assertEquals("isolata", partitaIsolata.getStanzaCorrente().getNome());
	}
}
