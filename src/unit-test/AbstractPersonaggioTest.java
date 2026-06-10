import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

/**
 * Test dei metodi CONCRETI di AbstractPersonaggio, secondo il pattern
 * suggerito dalle trasparenze POO-classi-astratte-enum: una estensione
 * concreta minimale ("FakePersonaggio") che implementa i metodi astratti
 * restituendo costanti, solo per poter istanziare l'oggetto da testare.
 */
class AbstractPersonaggioTest {

	/** Estensione concreta minimale ai soli fini del testing. */
	private static class FakePersonaggio extends AbstractPersonaggio {
		FakePersonaggio(String nome, String presentazione) {
			super(nome, presentazione);
		}
		@Override
		public String agisci(Partita partita) {
			return "done";
		}
		@Override
		public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
			return "ok";
		}
	}

	private AbstractPersonaggio personaggio;

	@BeforeEach
	void setUp() {
		personaggio = new FakePersonaggio("Fake", "Sono il personaggio di prova.");
	}

	@Test
	void primoSaluto_contieneNomeEPresentazione() {
		String risposta = personaggio.saluta();
		assertTrue(risposta.contains("Fake"));
		assertTrue(risposta.contains("Sono il personaggio di prova."));
	}

	@Test
	void secondoSaluto_rispondeGiaPresentati() {
		personaggio.saluta();
		assertTrue(personaggio.saluta().contains("Ci siamo gia' presentati!"));
	}

	@Test
	void haSalutato_falsePrimaDelSaluto_trueDopo() {
		assertFalse(personaggio.haSalutato());
		personaggio.saluta();
		assertTrue(personaggio.haSalutato());
	}

	@Test
	void toString_restituisceIlNome() {
		assertEquals("Fake", personaggio.toString());
	}
}
