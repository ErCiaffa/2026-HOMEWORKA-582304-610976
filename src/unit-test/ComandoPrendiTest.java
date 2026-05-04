import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.ComandoPrendi;

class ComandoPrendiTest {

    private Partita partita;
    private IOSimulator io;

    @BeforeEach
    void setUp() {
        io = new IOSimulator(new String[]{});
        partita = new Partita(new Labirinto(), io);
    }

    @Test
    void test_prendi_null_parametro() {
        ComandoPrendi cmd = new ComandoPrendi();
        cmd.setParametro(null);
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Quale attrezzo vuoi prendere ?")));
    }

    @Test
    void test_prendi_attrezzo_presente() {
        ComandoPrendi cmd = new ComandoPrendi();
        cmd.setParametro("osso");
        cmd.esegui(partita);
        assertTrue(partita.getBorsa().hasAttrezzo("osso"));
        assertFalse(partita.getStanzaCorrente().hasAttrezzo("osso"));
    }

    @Test
    void test_prendi_attrezzo_assente() {
        ComandoPrendi cmd = new ComandoPrendi();
        cmd.setParametro("spada");
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo non presente nella stanza !")));
    }

    @Test
    void test_prendi_inserisce_messaggio_successo() {
        ComandoPrendi cmd = new ComandoPrendi();
        cmd.setParametro("osso");
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo inserito nella borsa !")));
    }

    @Test
    void test_getNome() {
        assertEquals("prendi", new ComandoPrendi().getNome());
    }

    @Test
    void test_getParametro() {
        ComandoPrendi cmd = new ComandoPrendi();
        cmd.setParametro("osso");
        assertEquals("osso", cmd.getParametro());
    }
}
