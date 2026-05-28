import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;

/**
 * Test di integrazione end-to-end della Partita:
 * costruisce labirinti col LabirintoBuilder, simula sequenze di comandi
 * tramite IOSimulator e verifica i messaggi prodotti dal motore di gioco.
 *
 * I labirinti vanno dal piu' semplice (monolocale, una stanza)
 * al piu' completo (mappa, con stanze speciali e oggetti per stressare
 * tutte le funzionalita').
 */
class PartitaIntegrationTest {

    /* =========================================================
     * Fixture: tre labirinti di complessita' crescente
     * ========================================================= */

    private static Labirinto monolocale(){
        return new LabirintoBuilder()
                .addStanzaIniziale("Salone")
                .getLabirinto();
    }

    private static Labirinto bilocale(){
        return new LabirintoBuilder()
                .addStanzaIniziale("Salone")
                .addStanzaVincente("Cucina")
                .addAdiacenzaAvanzata("Salone","Cucina","est")
                .getLabirinto();
    }

    /*
     *                              [Soffitta] (buia: serve "torcia")
     *                                  | sopra
     *  [Camera] --ovest-- [Corridoio*] --est--> [Bagno] (VINCENTE)
     *                          | nord       (*bloccata est: serve "chiaveBagno" nella stanza)
     *  [Giardino] --ovest-- [Salone] --est--> [Cucina]
     *                          | nord
     *                       [Ingresso] (INIZIALE)
     *                          | sotto
     *                       [Cantina] (magica, soglia=1)
     */
    private static Labirinto mappa(){
        return new LabirintoBuilder()
                .addStanzaIniziale("Ingresso")
                    .addAttrezzo("zerbino", 1)

                .addStanza("Salone")
                    .addAttrezzo("divano", 50)        // troppo pesante: testa il limite della borsa
                    .addAttrezzo("telecomando", 1)

                .addStanza("Cucina")
                    .addAttrezzo("coltello", 2)
                    .addAttrezzo("torcia", 1)         // serve per illuminare la Soffitta

                .addStanza("Giardino")
                    .addAttrezzo("pala", 4)
                    .addAttrezzo("chiaveBagno", 1)    // sblocca il Corridoio verso il Bagno

                .addStanzaBloccata("Corridoio", "est", "chiaveBagno")
                    .addAttrezzo("quadro", 3)

                .addStanza("Camera da letto")
                    .addAttrezzo("cuscino", 1)
                    .addAttrezzo("libro", 2)

                .addStanzaBuia("Soffitta", "torcia")
                    .addAttrezzo("baule", 10)
                    .addAttrezzo("mappaTesoro", 1)

                .addStanzaMagica("Cantina", 1)
                    .addAttrezzo("vino", 2)
                    .addAttrezzo("ragnatela", 1)

                .addStanzaVincente("Bagno")
                    .addAttrezzo("specchio", 3)

                .addAdiacenzaAvanzata("Ingresso", "Salone", "nord")
                .addAdiacenzaAvanzata("Salone", "Cucina", "est")
                .addAdiacenzaAvanzata("Salone", "Giardino", "ovest")
                .addAdiacenzaAvanzata("Salone", "Corridoio", "nord")
                .addAdiacenzaAvanzata("Corridoio", "Camera da letto", "ovest")
                .addAdiacenzaAvanzata("Corridoio", "Soffitta", "sopra")
                .addAdiacenzaAvanzata("Ingresso", "Cantina", "sotto")
                .addAdiacenzaAvanzata("Corridoio", "Bagno", "est")
                .getLabirinto();
    }

    /** Helper: vero se almeno una stringa di output contiene la sottostringa. */
    private static boolean stampato(IOSimulator io, String s) {
        return io.getUscite().stream().anyMatch(out -> out.contains(s));
    }

    /* =========================================================
     * 1) Monolocale: una sola stanza, niente uscite, niente attrezzi
     * ========================================================= */

    @Test
    void monolocale_finePulita(){
        IOSimulator io = new IOSimulator(List.of("fine"));
        new DiaDia(monolocale(), io).gioca();
        assertTrue(stampato(io, "Grazie di aver giocato!"));
    }

    @Test
    void monolocale_vaiInDirezioneInesistente(){
        IOSimulator io = new IOSimulator(List.of("vai nord", "fine"));
        new DiaDia(monolocale(), io).gioca();
        assertTrue(stampato(io, "Direzione inesistente"));
    }

    @Test
    void monolocale_prendiAttrezzoInStanzaVuota(){
        IOSimulator io = new IOSimulator(List.of("prendi qualsiasi", "fine"));
        new DiaDia(monolocale(), io).gioca();
        assertTrue(stampato(io, "Attrezzo non presente nella stanza"));
    }

    /* =========================================================
     * 2) Bilocale: vittoria diretta e adiacenza bidirezionale
     * ========================================================= */

    @Test
    void bilocale_vittoriaInUnaMossa(){
        IOSimulator io = new IOSimulator(List.of("vai est"));
        new DiaDia(bilocale(), io).gioca();
        assertTrue(stampato(io, "Hai vinto!"));
    }

    @Test
    void bilocale_adiacenzaBidirezionale_ritornoIndietro(){
        // est porta a Cucina (vincente)  -> il primo "vai est" vince e termina.
        // Per verificare il ritorno costruisco un bilocale-bis con cucina NON vincente.
        Labirinto bidi = new LabirintoBuilder()
                .addStanzaIniziale("A")
                .addStanza("B")
                .addAdiacenzaAvanzata("A","B","est")
                .getLabirinto();
        IOSimulator io = new IOSimulator(List.of("vai est", "vai ovest", "fine"));
        new DiaDia(bidi, io).gioca();
        // l'output dell'ultima "vai ovest" deve contenere la descrizione di A
        assertTrue(stampato(io, "Stanza: A"));
        assertFalse(stampato(io, "Direzione inesistente"));
    }

    /* =========================================================
     * 3) Mappa: percorso di vittoria e blocco effettivo
     * ========================================================= */

    @Test
    void mappa_vittoriaConChiaveSbloccaCorridoio(){
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",            // Ingresso -> Salone
                "vai ovest",           // Salone   -> Giardino
                "prendi chiaveBagno",
                "vai est",             // Giardino -> Salone
                "vai nord",            // Salone   -> Corridoio
                "posa chiaveBagno",    // sblocca est
                "vai est"              // Corridoio -> Bagno (VINCE)
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Hai vinto!"));
        assertTrue(stampato(io, "Attrezzo inserito nella borsa"));
        assertTrue(stampato(io, "Attrezzo posato nella stanza"));
    }

    @Test
    void mappa_corridoioBloccatoSenzaChiave_nonVince(){
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",   // Salone
                "vai nord",   // Corridoio
                "vai est",    // bloccato: resta nel Corridoio
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertFalse(stampato(io, "Hai vinto!"));
        // l'avviso del passaggio bloccato deve comparire nella descrizione
        assertTrue(stampato(io, "Passaggio est bloccato"));
    }

    /* =========================================================
     * 4) Mappa: stanza buia (Soffitta) con e senza torcia
     * ========================================================= */

    @Test
    void mappa_soffittaBuiaSenzaTorcia(){
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",    // Salone
                "vai nord",    // Corridoio
                "vai sopra",   // Soffitta (buia)
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "buio pesto"));
    }

    @Test
    void mappa_soffittaIlluminataConTorciaPosata(){
        // Porta la torcia dalla Cucina, posala in Soffitta -> descrizione normale.
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",       // Salone
                "vai est",        // Cucina
                "prendi torcia",
                "vai ovest",      // Salone
                "vai nord",       // Corridoio
                "vai sopra",      // Soffitta (ancora buia)
                "posa torcia",    // ora illuminata
                "vai sotto",      // torna in Corridoio
                "vai sopra",      // rientra in Soffitta illuminata
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        // dopo aver posato la torcia, l'ultima entrata deve mostrare il nome stanza
        assertTrue(stampato(io, "Stanza: Soffitta"));
    }

    /* =========================================================
     * 5) Mappa: borsa e attrezzi
     * ========================================================= */

    @Test
    void mappa_borsaPienaConDivanoTroppoPesante(){
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",        // Salone
                "prendi divano",   // 50 kg > 10 kg max
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "La borsa è piena"));
    }

    @Test
    void mappa_prendiOggettoLeggero(){
        IOSimulator io = new IOSimulator(List.of("prendi zerbino", "fine"));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Attrezzo inserito nella borsa"));
    }

    @Test
    void mappa_prendiAttrezzoNonPresente(){
        IOSimulator io = new IOSimulator(List.of("prendi spadaMagica", "fine"));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Attrezzo non presente nella stanza"));
    }

    @Test
    void mappa_posaAttrezzoNonInBorsa(){
        IOSimulator io = new IOSimulator(List.of("posa scettro", "fine"));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Attrezzo non presente nella borsa"));
    }

    @Test
    void mappa_prendiEPosaConCicloCompleto(){
        // Prendi zerbino in Ingresso, vai in Salone, posa zerbino -> conferma.
        IOSimulator io = new IOSimulator(List.of(
                "prendi zerbino",
                "vai nord",
                "posa zerbino",
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Attrezzo inserito nella borsa"));
        assertTrue(stampato(io, "Attrezzo posato nella stanza"));
    }

    /* =========================================================
     * 6) Mappa: navigazione bidirezionale completa
     * ========================================================= */

    @Test
    void mappa_navigazioneBidirezionaleTraStanze(){
        // Ingresso -> Salone -> Cucina -> Salone -> Giardino -> Salone -> Ingresso
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",     // Salone
                "vai est",      // Cucina
                "vai ovest",    // Salone
                "vai ovest",    // Giardino
                "vai est",      // Salone
                "vai sud",      // Ingresso
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        // mai uscito di pista -> niente errori di direzione
        assertFalse(stampato(io, "Direzione inesistente"));
        // l'ultima descrizione e' dell'Ingresso (dove si trova lo zerbino)
        assertTrue(stampato(io, "Stanza: Ingresso"));
    }

    @Test
    void mappa_accessoVerticaleSottoESopra(){
        // Verifica che le adiacenze "sotto"/"sopra" funzionino in entrambi i sensi
        IOSimulator io = new IOSimulator(List.of(
                "vai sotto",    // Ingresso -> Cantina
                "vai sopra",    // Cantina -> Ingresso
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Stanza: Cantina"));
        assertFalse(stampato(io, "Direzione inesistente"));
    }

    /* =========================================================
     * 7) Mappa: comando non valido nel mezzo della partita
     * ========================================================= */

    @Test
    void mappa_comandoNonValidoNonInterrompeLaPartita(){
        IOSimulator io = new IOSimulator(List.of(
                "vai nord",
                "pippo",        // input invalido
                "vai sud",      // si puo' continuare a giocare
                "fine"
        ));
        new DiaDia(mappa(), io).gioca();
        assertTrue(stampato(io, "Comando non valido!"));
        assertTrue(stampato(io, "Grazie di aver giocato!"));
    }
}
