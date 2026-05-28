import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.ComandoVai;

class ComandoVaiTest {

    private Partita partita;
    private IOSimulator io;

    @BeforeEach
    void setUp() {
        io = new IOSimulator(List.of());
        partita = new Partita(new Labirinto(), io);
    }

    @Test
    void test_vai_null_parametro() {
        ComandoVai cmd = new ComandoVai();
        cmd.setParametro(null);
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Dove vuoi andare ?")));
    }

    @Test
    void test_vai_direzione_valida() {
        ComandoVai cmd = new ComandoVai();
        cmd.setParametro("nord");
        cmd.esegui(partita);
        assertEquals("Biblioteca", partita.getStanzaCorrente().getNome());
    }

    @Test
    void test_vai_direzione_inesistente() {
        ComandoVai cmd = new ComandoVai();
        cmd.setParametro("nord-est");
        cmd.esegui(partita);
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Direzione inesistente")));
    }

    @Test
    void test_vai_direzione_valida_decrementa_cfu() {
        int cfuPrima = partita.getCfu();
        ComandoVai cmd = new ComandoVai();
        cmd.setParametro("est");
        cmd.esegui(partita);
        assertEquals(cfuPrima - 1, partita.getCfu());
    }

    @Test
    void test_getNome() {
        assertEquals("vai", new ComandoVai().getNome());
    }

    @Test
    void test_getParametro() {
        ComandoVai cmd = new ComandoVai();
        cmd.setParametro("sud");
        assertEquals("sud", cmd.getParametro());
    }
}
