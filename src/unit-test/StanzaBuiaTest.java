import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.ambienti.StanzaBuia;

class StanzaBuiaTest {

    private StanzaBuia stanza;

    @BeforeEach
    void setUp() {
        stanza = new StanzaBuia("CaveOscura", "lanterna");
    }

    @Test
    void test_descrizione_senza_attrezzo_necessario() {
        assertEquals("qui c'è un buio pesto", stanza.getDescrizione());
    }

    @Test
    void test_descrizione_con_attrezzo_necessario() {
        stanza.addAttrezzo(new Attrezzo("lanterna", 2));
        assertTrue(stanza.getDescrizione().contains("CaveOscura"));
    }

    @Test
    void test_descrizione_con_altro_attrezzo_non_sufficiente() {
        stanza.addAttrezzo(new Attrezzo("osso", 1));
        assertEquals("qui c'è un buio pesto", stanza.getDescrizione());
    }

    @Test
    void test_rimozione_attrezzo_torna_buio() {
        Attrezzo lanterna = new Attrezzo("lanterna", 2);
        stanza.addAttrezzo(lanterna);
        stanza.removeAttrezzo(lanterna);
        assertEquals("qui c'è un buio pesto", stanza.getDescrizione());
    }
}
