package it.uniroma3.diadia.ambienti;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

/**
 * Costruisce un {@link Labirinto} leggendo una specifica testuale "a sezioni"
 * da un qualunque {@link Reader} (StringReader nei test, FileReader in produzione)
 * e delegando la costruzione al {@link LabirintoBuilder}.
 *
 * Grammatica (ogni intestazione termina con ':', un elemento per riga):
 * <pre>
 * Stanze:          NomeStanza
 * StanzeBuie:      NomeStanza attrezzoNecessario
 * StanzeBloccate:  NomeStanza direzione attrezzoSbloccante
 * StanzeMagiche:   NomeStanza [soglia]
 * Estremi:         iniziale (1a riga), vincente (2a riga)
 * Attrezzi:        nomeAttrezzo peso NomeStanza
 * Uscite:          NomeStanzaDa direzione NomeStanzaA
 * Personaggi:      tipo nome NomeStanza   (tipo = Mago|Strega|Cane)
 * </pre>
 */
public class CaricatoreLabirinto {

    private static final String PRESENTAZIONE_DEFAULT = "Sono un personaggio di questo labirinto.";

    private final BufferedReader reader;
    private final LabirintoBuilder builder;

    public CaricatoreLabirinto(Reader reader) {
        this.reader = new BufferedReader(reader);
        this.builder = Labirinto.newBuilder();
    }

    public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
        this(new FileReader(nomeFile));
    }

    /**
     * Esegue il parsing e ritorna il labirinto costruito.
     * Tutte le stanze (anche speciali) vengono create PRIMA di risolvere
     * estremi, attrezzi, uscite e personaggi che vi fanno riferimento.
     */
    public Labirinto carica() {
        Map<String, List<String>> sezioni = leggiSezioni();

        creaStanze(sezioni.get("Stanze"));
        creaStanzeBuie(sezioni.get("StanzeBuie"));
        creaStanzeBloccate(sezioni.get("StanzeBloccate"));
        creaStanzeMagiche(sezioni.get("StanzeMagiche"));

        impostaEstremi(sezioni.get("Estremi"));
        collocaAttrezzi(sezioni.get("Attrezzi"));
        impostaUscite(sezioni.get("Uscite"));
        collocaPersonaggi(sezioni.get("Personaggi"));

        return this.builder.getLabirinto();
    }

    /** Restituisce la stanza iniziale del labirinto costruito (comodita' per i test). */
    public Stanza getStanzaIniziale() {
        return this.builder.getLabirinto().getStanzaIniziale();
    }

    /** Restituisce la stanza vincente del labirinto costruito (comodita' per i test). */
    public Stanza getStanzaVincente() {
        return this.builder.getLabirinto().getStanzaVincente();
    }

    /* ---------- lettura a sezioni ---------- */

    private Map<String, List<String>> leggiSezioni() {
        Map<String, List<String>> sezioni = new LinkedHashMap<>();
        List<String> corrente = null;
        try {
            String riga;
            while ((riga = this.reader.readLine()) != null) {
                riga = riga.trim();
                if (riga.isEmpty())
                    continue;
                if (riga.endsWith(":")) {
                    corrente = new ArrayList<>();
                    sezioni.put(riga.substring(0, riga.length() - 1), corrente);
                } else {
                    check(corrente != null, "riga di dati '" + riga + "' fuori da ogni sezione");
                    corrente.add(riga);
                }
            }
        } catch (IOException e) {
            throw new FormatoFileNonValidoException("Errore di lettura: " + e.getMessage());
        }
        return sezioni;
    }

    /* ---------- creazione stanze ---------- */

    private void creaStanze(List<String> righe) {
        if (righe == null)
            return;
        for (String nome : righe)
            this.builder.addStanza(nome);
    }

    private void creaStanzeBuie(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 2, "formato atteso 'NomeStanza attrezzoNecessario': " + riga);
            this.builder.addStanzaBuia(t[0], t[1]);
        }
    }

    private void creaStanzeBloccate(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 3, "formato atteso 'NomeStanza direzione attrezzoSbloccante': " + riga);
            check(Direzione.fromString(t[1]) != null, "direzione non valida: " + t[1]);
            this.builder.addStanzaBloccata(t[0], t[1], t[2]);
        }
    }

    private void creaStanzeMagiche(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 1 || t.length == 2, "formato atteso 'NomeStanza [soglia]': " + riga);
            if (t.length == 1)
                this.builder.addStanzaMagica(t[0]);
            else
                this.builder.addStanzaMagica(t[0], parseIntero(t[1], "soglia"));
        }
    }

    /* ---------- estremi, attrezzi, uscite, personaggi ---------- */

    private void impostaEstremi(List<String> righe) {
        check(righe != null && righe.size() >= 2, "sezione Estremi assente o incompleta");
        String iniziale = righe.get(0);
        String vincente = righe.get(1);
        check(esiste(iniziale), iniziale + " non definita");
        check(esiste(vincente), vincente + " non definita");
        this.builder.contrassegnaIniziale(iniziale);
        this.builder.contrassegnaVincente(vincente);
    }

    private void collocaAttrezzi(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 3, "formato atteso 'nomeAttrezzo peso NomeStanza': " + riga);
            check(esiste(t[2]), "Attrezzo " + t[0] + " non collocabile: stanza " + t[2] + " inesistente");
            this.builder.addAttrezzo(t[2], t[0], parseIntero(t[1], "peso dell'attrezzo " + t[0]));
        }
    }

    private void impostaUscite(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 3, "formato atteso 'NomeStanzaDa direzione NomeStanzaA': " + riga);
            check(esiste(t[0]), "Stanza di partenza sconosciuta: " + t[0]);
            check(esiste(t[2]), "Stanza di destinazione sconosciuta: " + t[2]);
            check(Direzione.fromString(t[1]) != null, "direzione non valida: " + t[1]);
            this.builder.addAdiacenza(t[0], t[2], t[1]);
        }
    }

    private void collocaPersonaggi(List<String> righe) {
        if (righe == null)
            return;
        for (String riga : righe) {
            String[] t = riga.split("\\s+");
            check(t.length == 3, "formato atteso 'tipo nome NomeStanza': " + riga);
            check(esiste(t[2]), "Personaggio " + t[1] + " non collocabile: stanza " + t[2] + " inesistente");
            this.builder.addPersonaggio(t[2], creaPersonaggio(t[0], t[1]));
        }
    }

    private AbstractPersonaggio creaPersonaggio(String tipo, String nome) {
        switch (tipo.toLowerCase()) {
            case "mago":   return new Mago(nome, PRESENTAZIONE_DEFAULT, null);
            case "strega": return new Strega(nome, PRESENTAZIONE_DEFAULT);
            case "cane":   return new Cane(nome, PRESENTAZIONE_DEFAULT);
            default:
                check(false, "tipo di personaggio sconosciuto: " + tipo);
                return null; // irraggiungibile: check ha gia' lanciato
        }
    }

    /* ---------- helper ---------- */

    private boolean esiste(String nomeStanza) {
        return this.builder.getListaStanze().containsKey(nomeStanza);
    }

    private int parseIntero(String valore, String descrizione) {
        try {
            return Integer.parseInt(valore);
        } catch (NumberFormatException e) {
            throw new FormatoFileNonValidoException("Valore non numerico per " + descrizione + ": " + valore);
        }
    }

    private void check(boolean condizione, String messaggioErrore) {
        if (!condizione)
            throw new FormatoFileNonValidoException("Formato file non valido: " + messaggioErrore);
    }
}
