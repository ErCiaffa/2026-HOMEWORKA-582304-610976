import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;

class StanzaBloccataTest {

    private StanzaBloccata stanza;
    private Stanza stanzaNord;

    @BeforeEach
    void setUp() {
        stanza = new StanzaBloccata("Corridoio", "nord", "chiave");
        stanzaNord = new Stanza("Sala");
        stanza.impostaStanzaAdiacente("nord", stanzaNord);
        stanza.impostaStanzaAdiacente("sud", new Stanza("Ingresso"));
    }

    @Test
    void test_stanza_adiacente_bloccata_senza_attrezzo_ritorna_stessa() {
        assertSame(stanza, stanza.getStanzaAdiacente("nord"));
    }

    @Test
    void test_stanza_adiacente_bloccata_con_attrezzo_sblocca() {
        stanza.addAttrezzo(new Attrezzo("chiave", 1));
        assertSame(stanzaNord, stanza.getStanzaAdiacente("nord"));
    }

    @Test
    void test_stanza_adiacente_direzione_non_bloccata() {
        assertNotNull(stanza.getStanzaAdiacente("sud"));
        assertNotSame(stanza, stanza.getStanzaAdiacente("sud"));
    }

    @Test
    void test_descrizione_contiene_info_blocco() {
        assertTrue(stanza.getDescrizione().contains("nord"));
        assertTrue(stanza.getDescrizione().contains("chiave"));
    }

    @Test
    void test_descrizione_sbloccata_non_mostra_blocco() {
        stanza.addAttrezzo(new Attrezzo("chiave", 1));
        assertFalse(stanza.getDescrizione().contains("bloccato"));
    }
}
