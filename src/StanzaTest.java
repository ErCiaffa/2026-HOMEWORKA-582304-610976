import static org.junit.jupiter.api.Assertions.*;

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
	void testImpostaStanzaAdiacente() {
		n11.impostaStanzaAdiacente("SUD", n12);
		assertEquals(n12,n11.getStanzaAdiacente("SUD"));
		n12.impostaStanzaAdiacente("NORD",n11);
		n10.impostaStanzaAdiacente("NORD", n12);
		
	}

	@Test
	void testGetStanzaAdiacente() {
		n11.impostaStanzaAdiacente("SUD", n12);
		assertEquals(n12,n11.getStanzaAdiacente("SUD"));
		assertEquals(null,n10.getStanzaAdiacente("EAST"));
	}

	@Test
	void testAddAttrezzo() {
		//Attrezzo martello = new Attrezzo("martello",3);
		//Attrezzo giravite = new Attrezzo("giravite",1);
		assertTrue(n11.addAttrezzo(martello));
		assertTrue(n12.addAttrezzo(giravite));
	}

	@Test
	void testHasAttrezzo() {
		n11.addAttrezzo(martello);
		assertTrue(n11.hasAttrezzo("martello"));
	}

	@Test
	void testGetAttrezzo() {
		//Attrezzo martello = new Attrezzo("martello",3);
		n11.addAttrezzo(martello);
		assertEquals(martello,n11.getAttrezzo("martello"));
	}

	@Test
	void testRemoveAttrezzo() {
		Attrezzo torcia = new Attrezzo("torcia",2);
		n11.addAttrezzo(torcia);
		//Attrezzo osso = new Attrezzo("osso",2);
		assertTrue(n11.removeAttrezzo(torcia));
		assertFalse(n11.hasAttrezzo("torcia"));
		assertFalse(n11.removeAttrezzo(osso));
	}

}
