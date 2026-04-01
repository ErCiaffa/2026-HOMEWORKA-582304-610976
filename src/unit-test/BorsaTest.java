import it.uniroma3.diadia.attrezzi.Attrezzo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.giocatore.Borsa;

class BorsaTest {
	
	private Borsa borsa;
	private Attrezzo spada;
	private Attrezzo piuma;
	private Attrezzo negativo;

	@BeforeEach
	void setUp() throws Exception {
		this.borsa = new Borsa();
		this.spada = new Attrezzo("spada",11);
		this.piuma = new Attrezzo("piuma",1);
		this.negativo = new Attrezzo("negativo",-4);
	}

	@Test
	void testAddAttrezzo() {
		assertTrue(borsa.addAttrezzo(piuma),"Attrezzo piuma aggiunto");
		assertEquals(1,borsa.getPeso(),"Peso borsa uguale a 1");
	}
	
	@Test
	void testPesoEccessivo_e_is_empty() {
		assertFalse(borsa.addAttrezzo(spada),"Attrezzo troppo pesante: spada non aggiunta");
		assertTrue(borsa.isEmpty(),"Attrezzo non aggiunto, borsa vuota");
	}
	
	@Test
	void testPesoNegativo() {
		assertFalse(borsa.addAttrezzo(negativo),"Attrezzo negativo non aggiunto");
	}
	
	@Test
	void testRemoveAttrezzo() {
		borsa.addAttrezzo(piuma);
		Attrezzo rimosso = borsa.removeAttrezzo("piuma");
		assertNotNull(rimosso,"Attrezzo piuma rimosso");
	}
	
	@Test
	void testRemoveInesistente() {
		assertNull(borsa.removeAttrezzo("martello"));
	}
	
	@Test
	void testBorsaVuota() {
		assertEquals(0,borsa.getPeso(),"La borsa deve essere vuota");
	}

}
