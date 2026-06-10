import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Cane;

class CaneTest {

	private Partita partita;
	private Cane cane;

	@BeforeEach
	void setUp() {
		partita = new Partita(Labirinto.newBuilder()
				.addStanzaIniziale("cuccia")
				.addStanzaVincente("uscita")
				.getLabirinto());
		cane = new Cane("Rex", "Bau!");
		partita.getStanzaCorrente().setPersonaggio(cane);
	}

	@Test
	void agisci_mordeETogliUnCfu() {
		int cfuPrima = partita.getGiocatore().getCfu();
		cane.agisci(partita);
		assertEquals(cfuPrima - 1, partita.getGiocatore().getCfu());
	}

	@Test
	void riceviRegalo_ciboPreferito_loAccettaSenzaMordere() {
		int cfuPrima = partita.getGiocatore().getCfu();
		String msg = cane.riceviRegalo(new Attrezzo("osso", 1), partita);
		assertEquals(cfuPrima, partita.getGiocatore().getCfu());
		assertTrue(msg.contains("BAU"));
	}

	@Test
	void riceviRegalo_altroAttrezzo_mordeETogliUnCfu() {
		int cfuPrima = partita.getGiocatore().getCfu();
		cane.riceviRegalo(new Attrezzo("libro", 1), partita);
		assertEquals(cfuPrima - 1, partita.getGiocatore().getCfu());
	}
}
