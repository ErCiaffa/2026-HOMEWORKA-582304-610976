import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoPosa;

class ComandoPosaTest {

    private Partita partita;
    private IOSimulator io;

    @BeforeEach
    void setUp() {
        io = new IOSimulator(List.of());
        partita = new Partita(Labirinto.creaLabirintoDiDefault(), io);
    }

    @Test
    void test_posa_null_parametro() {
        ComandoPosa cmd = new ComandoPosa();
        cmd.setParametro(null);
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Quale attrezzo vuoi posare ?")));
    }

    @Test
    void test_posa_attrezzo_presente_in_borsa() {
        Attrezzo chiave = new Attrezzo("chiave", 1);
        partita.getBorsa().addAttrezzo(chiave);
        ComandoPosa cmd = new ComandoPosa();
        cmd.setParametro("chiave");
        cmd.esegui(partita);
        assertTrue(partita.getStanzaCorrente().hasAttrezzo("chiave"));
        assertFalse(partita.getBorsa().hasAttrezzo("chiave"));
    }

    @Test
    void test_posa_attrezzo_assente_dalla_borsa() {
        ComandoPosa cmd = new ComandoPosa();
        cmd.setParametro("ascia");
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo non presente nella borsa !")));
    }

    @Test
    void test_posa_inserisce_messaggio_successo() {
        partita.getBorsa().addAttrezzo(new Attrezzo("chiave", 1));
        ComandoPosa cmd = new ComandoPosa();
        cmd.setParametro("chiave");
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo posato nella stanza !")));
    }

    @Test
    void test_getNome() {
        assertEquals("posa", new ComandoPosa().getNome());
    }

    @Test
    void test_getParametro() {
        ComandoPosa cmd = new ComandoPosa();
        cmd.setParametro("chiave");
        assertEquals("chiave", cmd.getParametro());
    }
}
