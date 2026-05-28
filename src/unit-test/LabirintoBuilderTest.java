import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.Stanza;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LabirintoBuilderTest {

	@Test
	void testCostruisceLabirintoMinimo() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("a")
				.addStanzaVincente("b")
				.addAdiacenza("a", "b", "nord")
				.getLabirinto();

		assertEquals("a", l.getStanzaIniziale().getNome());
		assertEquals("b", l.getStanzaVincente().getNome());
		assertEquals("b", l.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
	}

	@Test
	void testAddAttrezzoVaSullUltimaStanza() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("a")
					.addAttrezzo("osso", 1)
				.addStanzaVincente("b")
					.addAttrezzo("lanterna", 3)
				.getLabirinto();

		Stanza a = l.getStanzaIniziale();
		Stanza b = l.getStanzaVincente();
		assertTrue(a.hasAttrezzo("osso"), "osso deve stare in a");
		assertFalse(a.hasAttrezzo("lanterna"), "lanterna non deve stare in a");
		assertTrue(b.hasAttrezzo("lanterna"), "lanterna deve stare in b");
	}

	@Test
	void testAdiacenzaSuStanzaInesistenteLanciaEccezione() {
		assertThrows(IllegalArgumentException.class, () ->
			new LabirintoBuilder()
				.addStanzaIniziale("a")
				.addAdiacenza("a", "fantasma", "nord")
		);
	}

	@Test
	void testAddAttrezzoSenzaStanzaLanciaEccezione() {
		assertThrows(IllegalStateException.class, () ->
			new LabirintoBuilder().addAttrezzo("osso", 1)
		);
	}

	@Test
	void testCostruisceConTuttiITipiDiStanza() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("ingresso")
				.addStanzaBuia("cantina", "lanterna")
				.addStanzaBloccata("porta", "nord", "chiave")
				.addStanzaMagica("altare")
				.addStanzaVincente("uscita")
				.addAdiacenza("ingresso", "cantina", "sud")
				.addAdiacenza("ingresso", "porta", "nord")
				.addAdiacenza("ingresso", "altare", "est")
				.addAdiacenza("ingresso", "uscita", "ovest")
				.getLabirinto();

		assertNotNull(l.getStanzaIniziale());
		assertNotNull(l.getStanzaVincente());
		assertEquals("cantina", l.getStanzaIniziale().getStanzaAdiacente("sud").getNome());
		assertEquals("porta",   l.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
		assertEquals("altare",  l.getStanzaIniziale().getStanzaAdiacente("est").getNome());
		assertEquals("uscita",  l.getStanzaIniziale().getStanzaAdiacente("ovest").getNome());
	}

	@Test
	void testStanzaBuiaSenzaAttrezzoMostraBuioPesto() {
		Labirinto l = new LabirintoBuilder()
				.addStanzaIniziale("ingresso")
				.addStanzaBuia("cantina", "lanterna")
				.addAdiacenza("ingresso", "cantina", "sud")
				.getLabirinto();

		Stanza cantina = l.getStanzaIniziale().getStanzaAdiacente("sud");
		assertEquals("Qui c'è un buio pesto", cantina.getDescrizione());
	}
}
