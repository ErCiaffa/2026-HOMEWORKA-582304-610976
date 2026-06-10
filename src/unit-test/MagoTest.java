import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Mago;

class MagoTest {

	private Partita partita;

	@BeforeEach
	void setUp() {
		partita = new Partita(Labirinto.newBuilder()
				.addStanzaIniziale("torre")
				.addStanzaVincente("uscita")
				.getLabirinto());
	}

	@Test
	void agisci_conAttrezzo_loDonaNellaStanza() {
		Mago mago = new Mago("Merlino", "Sono Merlino.", new Attrezzo("bacchetta", 1));
		mago.agisci(partita);
		assertTrue(partita.getStanzaCorrente().hasAttrezzo("bacchetta"));
	}

	@Test
	void agisci_secondaVolta_nonHaPiuNulla() {
		Mago mago = new Mago("Merlino", "Sono Merlino.", new Attrezzo("bacchetta", 1));
		mago.agisci(partita);
		String msg = mago.agisci(partita);
		assertTrue(msg.contains("non ho piu' nulla"));
	}

	@Test
	void riceviRegalo_dimezzaIlPesoELoLasciaNellaStanza() {
		Mago mago = new Mago("Merlino", "Sono Merlino.", null);
		mago.riceviRegalo(new Attrezzo("piombo", 10), partita);
		Attrezzo posato = partita.getStanzaCorrente().getAttrezzo("piombo");
		assertNotNull(posato);
		assertEquals(5, posato.getPeso());
	}
}
