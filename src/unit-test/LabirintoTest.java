import it.uniroma3.diadia.ambienti.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LabirintoTest {
	
	private Labirinto labirinto;

	@BeforeEach
	void setUp() throws Exception {
		this.labirinto = new Labirinto();
	}

	@Test
	void testStanzeNull() {
		assertNotNull(labirinto.getStanzaIniziale(),"La stanza iniziale non deve essere null");
		assertNotNull(labirinto.getStanzaVincente(),"La stanza vincente non deve essere null");
		
	}
	
	@Test
	void testStanzaUguale() {
		assertEquals("Atrio",labirinto.getStanzaIniziale().getNome(),"La stanza iniziale deve essere l'atrio");
		assertEquals("Biblioteca",labirinto.getStanzaVincente().getNome(),"La stanza vincente deve essere la biblioteca");
	}
	
	@Test
	void testStanzaDiversa() {
		assertNotEquals(labirinto.getStanzaIniziale().getNome(),labirinto.getStanzaVincente().getNome(),"La stanza iniziale deve essere diversa da quella vincente");
	}

}
