
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

class GiocatoreTest {
	
	private Giocatore giocatore;
	private Attrezzo spada;	

	@BeforeEach
	void setUp() throws Exception {
		this.giocatore = new Giocatore();
		this.spada = new Attrezzo("spada",5);
	}

	@Test
	void testAddAttrezzo() {
		assertTrue(giocatore.addAttrezzo(spada),"spada aggiunta");
		assertTrue(giocatore.hasAttrezzo("spada"),"il giocatore ora ha l'attrezzo spada");
	}
	
	void testRemoveEsiste() {
		giocatore.addAttrezzo(spada);
		Attrezzo rimosso = giocatore.removeAttrezzo("spada");
		assertNotNull(rimosso,"L'attrezzo rimosso non deve essere null");
		assertEquals("spada",rimosso.getNome(),"La spada è stata rimossa");
		assertFalse(giocatore.hasAttrezzo("spada"),"il giocatore non ha più la spada");
	}
	
	@Test
	void testRemoveInesistente() {
		assertNull(giocatore.removeAttrezzo("spada"));
	}
	
	@Test
	void testCFU() {
		assertEquals(20,giocatore.getCfu(),"i cfu iniziali devono essere 20");
	}
	
	@Test
	void testSetCfu() {
		giocatore.setCfu(10);
		assertEquals(10,giocatore.getCfu(),"i cfu aggornati a 10");
	}
	
	@Test
	void testAddNull() {
		assertFalse(giocatore.addAttrezzo(null),"aggiunta di attrezzo null fallisce");
	}

}
