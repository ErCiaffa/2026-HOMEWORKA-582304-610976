import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.comandi.AbstractComando;

class AbstractComandoTest {

    /**
     * Sottoclasse anonima minimale: definisce solo i metodi astratti,
     * eredita setParametro/getParametro da AbstractComando.
     */
    private AbstractComando creaComandoDiTest() {
        return new AbstractComando() {
            @Override
            public void esegui(Partita partita) { /* noop */ }
            @Override
            public String getNome() { return "test"; }
        };
    }

    @Test
    void abstractComando_setParametroSalvaIlValore() {
        AbstractComando c = creaComandoDiTest();
        c.setParametro("ciao");
        assertEquals("ciao", c.getParametro());
    }

    @Test
    void abstractComando_getParametroNullSeNonImpostato() {
        AbstractComando c = creaComandoDiTest();
        assertNull(c.getParametro());
    }

    @Test
    void abstractComando_setParametroSovrascriveIlValore() {
        AbstractComando c = creaComandoDiTest();
        c.setParametro("primo");
        c.setParametro("secondo");
        assertEquals("secondo", c.getParametro());
    }
}
