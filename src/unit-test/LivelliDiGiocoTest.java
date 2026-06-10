import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.personaggi.Cane;

/**
 * Valida le risorse dei livelli di gioco (POO-23: labirinto1.txt,
 * labirinto2.txt, labirinto3.txt) davvero spedite con l'applicazione.
 */
class LivelliDiGiocoTest {

	private Labirinto caricaRisorsa(String nome) {
		InputStream spec = getClass().getClassLoader().getResourceAsStream(nome);
		assertNotNull(spec, "risorsa " + nome + " non trovata nel classpath");
		return new CaricatoreLabirinto(new InputStreamReader(spec)).carica();
	}

	@Test
	void livello1_eIlLabirintoDelProf() {
		Labirinto l = caricaRisorsa("labirinto1.txt");
		assertEquals("Atrio", l.getStanzaIniziale().getNome());
		assertEquals("Biblioteca", l.getStanzaVincente().getNome());
		assertTrue(l.getStanzaIniziale().hasAttrezzo("osso"));
		// nel livello 1 si vince subito andando a nord, come nel default
		assertEquals("Biblioteca", l.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
	}

	@Test
	void livello2_haStanzeSpeciali() {
		Labirinto l = caricaRisorsa("labirinto2.txt");
		assertEquals("Atrio", l.getStanzaIniziale().getNome());
		// a nord c'e' il corridoio bloccato, non piu' la Biblioteca diretta
		assertEquals("Corridoio", l.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
	}

	@Test
	void livello3_haIPersonaggi() {
		Labirinto l = caricaRisorsa("labirinto3.txt");
		assertTrue(l.getStanzaIniziale().getPersonaggio() instanceof Cane);
	}
}
