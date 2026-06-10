import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

/** Test di equals()/hashCode() richiesti dalle trasparenze POO-12. */
class AttrezzoTest {

	@Test
	void equals_stessoNomeEPeso_sonoUguali() {
		assertEquals(new Attrezzo("osso", 1), new Attrezzo("osso", 1));
	}

	@Test
	void equals_pesoDiverso_sonoDiversi() {
		assertNotEquals(new Attrezzo("osso", 1), new Attrezzo("osso", 2));
	}

	@Test
	void equals_nomeDiverso_sonoDiversi() {
		assertNotEquals(new Attrezzo("osso", 1), new Attrezzo("lanterna", 1));
	}

	@Test
	void hashCode_coerenteConEquals() {
		assertEquals(new Attrezzo("osso", 1).hashCode(), new Attrezzo("osso", 1).hashCode());
	}

	@Test
	void equals_conNullEAltroTipo_false() {
		Attrezzo osso = new Attrezzo("osso", 1);
		assertNotEquals(osso, null);
		assertNotEquals(osso, "osso");
	}
}
