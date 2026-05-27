

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaMagicaTest {
	
	private StanzaMagica stanza;
	private Attrezzo a;
	

	@BeforeEach
	public void setUp() {
		stanza = new StanzaMagica("Magica",2);	
		a = new Attrezzo("TroppiAttrezzi",2);
		stanza.addAttrezzo(new Attrezzo("lanterna",2));
	}

	//aggiunta di un attrezzo esistente
	@Test
	public void testAddAttrezzoEsistente() {
		assertTrue(stanza.addAttrezzo(new Attrezzo("osso",2)));
	}

	//aggiunta di un attrezzo con soglia superata
	@Test
	public void testAddAttrezzoSogliaSuperata() {
		stanza.addAttrezzo(new Attrezzo("osso",2));
		stanza.addAttrezzo(a);
		assertFalse(stanza.hasAttrezzo("TroppiAttrezzi"));
	}
	
	@Test
	public void testModificaAttrezzo_AddAttrezzoSogliaSuperata() {
		stanza.addAttrezzo(new Attrezzo("osso",2));
		stanza.addAttrezzo(a);
		assertNotNull(stanza.getAttrezzo("izzerttAipporT"));
	}

}
