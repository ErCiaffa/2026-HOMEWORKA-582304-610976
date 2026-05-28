import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOSimulator;

class IOSimulatorTest {

    @Test
    void test_vinci_andando_nord() {
        IOSimulator io = new IOSimulator(List.of("vai nord"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Hai vinto!")));
    }

    @Test
    void test_fine_termina_gioco() {
        IOSimulator io = new IOSimulator(List.of("fine"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Grazie di aver giocato!")));
    }

    @Test
    void test_comando_non_valido() {
        IOSimulator io = new IOSimulator(List.of("pippo", "fine"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Comando non valido!")));
    }

    @Test
    void test_vai_direzione_inesistente() {
        IOSimulator io = new IOSimulator(List.of("vai est", "vai est", "fine"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Direzione inesistente")));
    }

    @Test
    void test_prendi_attrezzo_non_presente() {
        IOSimulator io = new IOSimulator(List.of("prendi spada", "fine"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo non presente nella stanza !")));
    }

    @Test
    void test_prendi_attrezzo_presente() {
        // osso e' nell'atrio (start)
        IOSimulator io = new IOSimulator(List.of("prendi osso", "fine"));
        new DiaDia(io).gioca();
        assertTrue(io.getUscite().stream().anyMatch(s -> s.contains("Attrezzo inserito nella borsa !")));
    }
}
