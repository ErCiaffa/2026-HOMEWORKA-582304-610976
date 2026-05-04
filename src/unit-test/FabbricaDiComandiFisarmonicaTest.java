import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoAiuto;
import it.uniroma3.diadia.comandi.ComandoFine;
import it.uniroma3.diadia.comandi.ComandoGuarda;
import it.uniroma3.diadia.comandi.ComandoNonValido;
import it.uniroma3.diadia.comandi.ComandoPosa;
import it.uniroma3.diadia.comandi.ComandoPrendi;
import it.uniroma3.diadia.comandi.ComandoVai;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

class FabbricaDiComandiFisarmonicaTest {

    private FabbricaDiComandiFisarmonica fabbrica;

    @BeforeEach
    void setUp() {
        fabbrica = new FabbricaDiComandiFisarmonica();
    }

    @Test
    void test_costruisci_vai() {
        assertTrue(fabbrica.costruisciComando("vai nord") instanceof ComandoVai);
    }

    @Test
    void test_costruisci_vai_parametro() {
        assertEquals("nord", fabbrica.costruisciComando("vai nord").getParametro());
    }

    @Test
    void test_costruisci_prendi() {
        assertTrue(fabbrica.costruisciComando("prendi osso") instanceof ComandoPrendi);
    }

    @Test
    void test_costruisci_prendi_parametro() {
        assertEquals("osso", fabbrica.costruisciComando("prendi osso").getParametro());
    }

    @Test
    void test_costruisci_posa() {
        assertTrue(fabbrica.costruisciComando("posa osso") instanceof ComandoPosa);
    }

    @Test
    void test_costruisci_fine() {
        assertTrue(fabbrica.costruisciComando("fine") instanceof ComandoFine);
    }

    @Test
    void test_costruisci_aiuto() {
        assertTrue(fabbrica.costruisciComando("aiuto") instanceof ComandoAiuto);
    }

    @Test
    void test_costruisci_guarda() {
        assertTrue(fabbrica.costruisciComando("guarda") instanceof ComandoGuarda);
    }

    @Test
    void test_costruisci_comando_sconosciuto() {
        assertTrue(fabbrica.costruisciComando("pippo") instanceof ComandoNonValido);
    }

    @Test
    void test_costruisci_stringa_vuota() {
        assertTrue(fabbrica.costruisciComando("") instanceof ComandoNonValido);
    }

    @Test
    void test_getNome_vai() {
        assertEquals("vai", fabbrica.costruisciComando("vai nord").getNome());
    }

    @Test
    void test_costruisci_vai_senza_parametro() {
        assertNull(fabbrica.costruisciComando("vai").getParametro());
    }
}
