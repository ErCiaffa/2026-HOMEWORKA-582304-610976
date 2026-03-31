import static org.junit.jupiter.api.Assertions.*;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StanzaTest {
	
	Stanza n11;
	Stanza n12;
	Stanza n10;
	private Attrezzo martello;
	private Attrezzo giravite;
	private Attrezzo osso;

	@BeforeEach
	void setUp() throws Exception {
		n10 = new Stanza("N10");
		n11 = new Stanza("N11");
		n12 = new Stanza("N12");
		martello = new Attrezzo("martello",3);
		giravite = new Attrezzo("giravite",1);
		osso = new Attrezzo("osso",2);
		
	}


	@Test
	void test_setStanzaAdiacente_valid() {
		n11.impostaStanzaAdiacente("SUD", n12);
		assertEquals(n12,n11.getStanzaAdiacente("SUD"));
	}

	void test_setStanzaAdiacente_overwrite() {
		n11.impostaStanzaAdiacente("SUD", n12);
		n11.impostaStanzaAdiacente("NORD", n12);
		assertNotEquals(n12,n11.getStanzaAdiacente("SUD"));
		assertEquals(n12,n11.getStanzaAdiacente("NORD"));
	}

	@Test
	void test_getStanzaAdiacente_invalid_direction() {
		n11.impostaStanzaAdiacente("SUD", n12);
		assertNull(n10.getStanzaAdiacente("NORD"), "Errore: restituita una stanza per una direzione vuota.");
	}

	@Test
	void test_getStanzaAdiacente() {
		n11.impostaStanzaAdiacente("SUD", n12);
		assertEquals(n12,n11.getStanzaAdiacente("SUD"));
		assertNull(n10.getStanzaAdiacente("EAST"));
	}

	/* --- TEST ATTREZZI --- */

	@Test
	void test_addAttrezzo_valid() {
		assertTrue(n10.addAttrezzo(martello), "Errore: inserimento attrezzo fallito in stanza vuota.");
		assertTrue(n10.hasAttrezzo("martello"), "Errore: l'attrezzo aggiunto non risulta presente.");
	}

	@Test
	void test_addAttrezzo_full_room() {
		// Riempiamo la stanzo
		for(int i=0; i<10; i++) {
			n10.addAttrezzo(new Attrezzo("attrezzo test" + i, 1));
		}
		assertFalse(n10.addAttrezzo(martello), "Errore: è stato possibile aggiungere un attrezzo in una stanza piena.");
	}

	@Test
	void test_getAttrezzo_has_true() {
		n10.addAttrezzo(martello);
		assertEquals(martello, n10.getAttrezzo("martello"), "Errore: l'attrezzo recuperato non coincide con quello inserito.");
	}

	@Test
	void test_removeAttrezzo_existent() {
		n10.addAttrezzo(martello);
		assertTrue(n10.removeAttrezzo(martello), "Errore: rimozione di un attrezzo esistente fallita.");
		assertFalse(n10.hasAttrezzo("martello"), "Errore: l'attrezzo rimosso è ancora presente in stanza.");
	}

	@Test
	void test_removeAttrezzo_not_existent() {
		assertFalse(n10.removeAttrezzo(osso), "Errore: rimozione di un attrezzo mai inserito deve restituire false.");
	}
}