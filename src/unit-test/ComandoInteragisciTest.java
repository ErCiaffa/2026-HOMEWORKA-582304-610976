import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoInteragisci;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;

class ComandoInteragisciTest {

	private Partita partita;
	private IOSimulator io;

	@BeforeEach
	void setUp() {
		io = new IOSimulator(List.of());
		partita = new Partita(Labirinto.newBuilder()
				.addStanzaIniziale("atrio")
				.addStanzaVincente("uscita")
				.getLabirinto(), io);
	}

	@Test
	void interagisci_conMago_donaLAttrezzo() {
		partita.getStanzaCorrente().setPersonaggio(
				new Mago("Merlino", "Sono Merlino.", new Attrezzo("bacchetta", 1)));
		new ComandoInteragisci().esegui(partita);
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("bacchetta"));
	}

	@Test
	void interagisci_conCane_perdeUnCfu() {
		partita.getStanzaCorrente().setPersonaggio(new Cane("Rex", "Bau!"));
		int cfuPrima = partita.getGiocatore().getCfu();
		new ComandoInteragisci().esegui(partita);
		assertEquals(cfuPrima - 1, partita.getGiocatore().getCfu());
	}

	@Test
	void interagisci_nessunPersonaggio_chiedeConChi() {
		ComandoInteragisci cmd = new ComandoInteragisci();
		cmd.esegui(partita);
		assertEquals("Con chi dovrei interagire?...", cmd.getMessaggio());
	}
}
