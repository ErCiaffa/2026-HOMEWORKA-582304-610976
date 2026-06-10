import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.personaggi.Mago;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

/**
 * Test per CaricatoreLabirinto (Es. 15).
 *
 * Filosofia: fixture inline come stringa + StringReader (niente file fisici).
 * Si parte dal caso piu' piccolo (monolocale) e si sale di complessita'.
 * Questi test servono a far EMERGERE i bug della bozza fornita dal corso:
 * se un test e' rosso, NON modificare il test -> trova e correggi il parser.
 *
 * Nota: questi test assumono il formato "a sezioni" della issue #38
 *   Stanze: / Estremi: / Attrezzi: / Uscite:  (un elemento per riga)
 * e che carica() RITORNI un Labirinto.
 */
class CaricatoreLabirintoTest {

	/** Costruisce un caricatore da una specifica testuale inline e carica. */
	private Labirinto caricaDaStringa(String spec) {
		return new CaricatoreLabirinto(new StringReader(spec)).carica();
	}

	/* ===================== CASI CORRETTI ===================== */

	@Test
	void monolocale_unaSolaStanzaIniziale() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +      // iniziale
				"N10\n";       // vincente
		Labirinto l = caricaDaStringa(spec);
		assertEquals("N10", l.getStanzaIniziale().getNome());
	}

	@Test
	void monolocale_riconosceLaStanzaVincente() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n";
		Labirinto l = caricaDaStringa(spec);
		assertEquals("N10", l.getStanzaVincente().getNome());
	}

	@Test
	void bilocale_creaTutteLeStanze_nonSoloLaPrima() {
		// QUESTO test smaschera il bug classico: leggere solo il PRIMO elemento
		// di una sezione invece di iterare su tutti.
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Uscite:\n" +
				"N10 nord Biblioteca\n";
		Labirinto l = caricaDaStringa(spec);
		assertEquals("N10", l.getStanzaIniziale().getNome());
		assertEquals("Biblioteca", l.getStanzaVincente().getNome());
	}

	@Test
	void bilocale_collegaLeStanzeNellaDirezioneGiusta() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Uscite:\n" +
				"N10 nord Biblioteca\n";
		Labirinto l = caricaDaStringa(spec);
		Stanza iniziale = l.getStanzaIniziale();
		assertEquals("Biblioteca", iniziale.getStanzaAdiacente("nord").getNome());
	}

	@Test
	void attrezzo_vieneCollocatoNellaStanzaIndicata() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Attrezzi:\n" +
				"osso 5 N10\n";
		Labirinto l = caricaDaStringa(spec);
		assertTrue(l.getStanzaIniziale().hasAttrezzo("osso"),
				"l'osso deve stare in N10");
	}

	@Test
	void attrezzo_nonFinisceNellaStanzaSbagliata() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Attrezzi:\n" +
				"osso 5 Biblioteca\n";
		Labirinto l = caricaDaStringa(spec);
		assertFalse(l.getStanzaIniziale().hasAttrezzo("osso"),
				"l'osso NON deve stare in N10, ma in Biblioteca");
		assertTrue(l.getStanzaVincente().hasAttrezzo("osso"));
	}

	@Test
	void piuAttrezzi_vengonoLettiTutti() {
		// Altro test anti-"leggo solo il primo": due attrezzi in due righe.
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"Attrezzi:\n" +
				"osso 5 N10\n" +
				"lanterna 3 N10\n";
		Labirinto l = caricaDaStringa(spec);
		Stanza s = l.getStanzaIniziale();
		assertTrue(s.hasAttrezzo("osso"),     "manca osso");
		assertTrue(s.hasAttrezzo("lanterna"), "manca lanterna (seconda riga non letta?)");
	}

	@Test
	void uscite_multipleVengonoLetteTutte() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Uscite:\n" +
				"N10 nord Biblioteca\n" +
				"Biblioteca sud N10\n";
		Labirinto l = caricaDaStringa(spec);
		Stanza n10 = l.getStanzaIniziale();
		Stanza bib = l.getStanzaVincente();
		assertEquals("Biblioteca", n10.getStanzaAdiacente("nord").getNome());
		assertEquals("N10",        bib.getStanzaAdiacente("sud").getNome());
	}

	@Test
	void spaziMultipli_traITokenSonoTollerati() {
		// Trappola della issue: usare split("\\s+"), non split(" ").
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Estremi:\n" +
				"N10\n" +
				"Biblioteca\n" +
				"Uscite:\n" +
				"N10    nord     Biblioteca\n";   // spazi multipli volutamente
		Labirinto l = caricaDaStringa(spec);
		assertEquals("Biblioteca",
				l.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
	}

	/* ===================== CASI D'ERRORE ===================== */

	@Test
	void uscitaVersoStanzaInesistente_lanciaEccezione() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"Uscite:\n" +
				"N10 nord Fantasma\n";   // 'Fantasma' non e' tra le Stanze
		assertThrows(FormatoFileNonValidoException.class,
				() -> caricaDaStringa(spec));
	}

	@Test
	void attrezzoInStanzaInesistente_lanciaEccezione() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"Attrezzi:\n" +
				"osso 5 Fantasma\n";     // stanza inesistente
		assertThrows(FormatoFileNonValidoException.class,
				() -> caricaDaStringa(spec));
	}

	@Test
	void pesoAttrezzoNonNumerico_lanciaEccezione() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"Attrezzi:\n" +
				"osso pesante N10\n";    // 'pesante' non e' un numero
		assertThrows(FormatoFileNonValidoException.class,
				() -> caricaDaStringa(spec));
	}

	@Test
	void estremoRiferitoAStanzaInesistente_lanciaEccezione() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"Fantasma\n" +          // stanza iniziale mai dichiarata
				"N10\n";
		assertThrows(FormatoFileNonValidoException.class,
				() -> caricaDaStringa(spec));
	}

	/* ===================== GRAMMATICA ESTESA (Es 16) ===================== */

	/** Fixture estesa con stanza buia, bloccata e un personaggio. */
	private String specEstesa() {
		return
				"Stanze:\n" +
				"Atrio\n" +
				"Cantina\n" +
				"Cripta\n" +
				"Estremi:\n" +
				"Atrio\n" +
				"Cripta\n" +
				"Attrezzi:\n" +
				"chiave 1 Atrio\n" +
				"StanzeBuie:\n" +
				"Cantina lanterna\n" +
				"StanzeBloccate:\n" +
				"Cripta nord chiave\n" +
				"Personaggi:\n" +
				"Mago Merlino Atrio\n" +
				"Uscite:\n" +
				"Atrio sud Cantina\n" +
				"Cantina nord Atrio\n";
	}

	@Test
	void stanzaBuia_senzaAttrezzo_eBuiaPesto() {
		Labirinto l = caricaDaStringa(specEstesa());
		Stanza cantina = l.getStanzaIniziale().getStanzaAdiacente("sud");
		assertEquals("Qui c'è un buio pesto", cantina.getDescrizione());
	}

	@Test
	void stanzaBloccata_impediscePassaggioSenzaAttrezzo() {
		Labirinto l = caricaDaStringa(specEstesa());
		Stanza cripta = l.getStanzaVincente();
		// senza la chiave dentro la cripta, "nord" resta bloccato (ritorna se stessa)
		assertSame(cripta, cripta.getStanzaAdiacente("nord"));
	}

	@Test
	void personaggio_vieneCollocatoNellaStanzaIndicata() {
		Labirinto l = caricaDaStringa(specEstesa());
		assertTrue(l.getStanzaIniziale().getPersonaggio() instanceof Mago);
		assertEquals("Merlino", l.getStanzaIniziale().getPersonaggio().getNome());
	}

	@Test
	void personaggioDiTipoSconosciuto_lanciaEccezione() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"Personaggi:\n" +
				"Drago Smaug N10\n";   // 'Drago' non e' un tipo valido
		assertThrows(FormatoFileNonValidoException.class,
				() -> caricaDaStringa(spec));
	}

	@Test
	void stanzaMagica_modificaAttrezzoOltreLaSoglia() {
		String spec =
				"Stanze:\n" +
				"N10\n" +
				"Estremi:\n" +
				"N10\n" +
				"N10\n" +
				"StanzeMagiche:\n" +
				"Atrio 0\n";          // soglia 0: il primo attrezzo posato viene gia' modificato
		Labirinto l = new CaricatoreLabirinto(new StringReader(spec)).carica();
		assertEquals("N10", l.getStanzaIniziale().getNome());
	}
}
