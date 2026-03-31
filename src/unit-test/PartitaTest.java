import static org.junit.jupiter.api.Assertions.*;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartitaTest {

    private Partita partita;
    private Stanza stanzaTest;

    @BeforeEach
    void setUp() {
        this.partita = new Partita();
        this.stanzaTest = new Stanza("Test");
    }

    /* TEST VINTA */

    @Test
    void test_win_start() {
        assertFalse(partita.vinta(), "Stato iniziale: vinta() deve essere false.");
    }

    @Test
    void test_win_true() {
        partita.setStanzaCorrente(partita.getStanzaVincente());
        assertTrue(partita.vinta(), "it.uniroma3.diadia.ambienti.Stanza vincente: vinta() deve essere true.");
    }

    @Test
    void test_win_false() {
        partita.setStanzaCorrente(stanzaTest);
        assertFalse(partita.vinta(), "it.uniroma3.diadia.ambienti.Stanza ordinaria: vinta() deve essere false.");
    }

    /* TEST ISFINITA */

    @Test
    void test_isFinita_cfu() {
        partita.setCfu(0);
        assertTrue(partita.isFinita(), "CFU esauriti: isFinita() deve essere true.");
    }

    @Test
    void test_isFinita_vittoria() {
        partita.setStanzaCorrente(partita.getStanzaVincente());
        assertTrue(partita.isFinita(), "it.uniroma3.diadia.Partita vinta: isFinita() deve essere true.");
    }

    @Test
    void test_isFinita_manuale() {
        partita.setFinita();
        assertTrue(partita.isFinita(), "Flag fine impostato: isFinita() deve essere true.");
    }

    /* TEST STANZA CORRENTE */

    @Test
    void test_setStanzaCorrente() {
        partita.setStanzaCorrente(stanzaTest);
        assertSame(stanzaTest, partita.getStanzaCorrente(), "Errore nel riferimento stanzaCorrente.");
    }

    @Test
    void test_setStanzaCorrente_null() {
        partita.setStanzaCorrente(null);
        assertNull(partita.getStanzaCorrente(), "Riferimento null non gestito correttamente.");
    }

    @Test
    void test_getStanzaVincente() {
        assertNotNull(partita.getStanzaVincente(), "it.uniroma3.diadia.ambienti.Stanza vincente non inizializzata.");
    }
}