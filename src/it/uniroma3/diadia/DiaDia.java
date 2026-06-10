package it.uniroma3.diadia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa è la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO, Ciaffaroni Alessio, Davide Renda
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base test
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	/* Livelli di difficolta' crescente (POO-23): un file di specifica per livello. */
	static final private List<String> LIVELLI =
			List.of("labirinto1.txt", "labirinto2.txt", "labirinto3.txt");

	private Partita partita;
	private Labirinto labirinto;
	private IO io;
	public DiaDia(Labirinto labirinto, IO io) {
		this.io=io;
		this.labirinto = labirinto;
		this.partita = new Partita(labirinto,io);
	}


	public DiaDia(IO io) {
		this(Labirinto.creaLabirintoDiDefault(), io);
	}

	public void gioca() {
		String istruzione;

		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do		
			istruzione = io.leggiRiga();
		while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione è eseguita e il gioco continua, false altrimenti
	 */

	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire;
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva(this.io);
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita);
		if (this.partita.vinta())

			io.mostraMessaggio("Hai vinto!");
		if (!this.partita.giocatoreIsVivo())

			io.mostraMessaggio("Hai esaurito i CFU...");

		return this.partita.isFinita();
	}

	/** Esito dell'ultima partita giocata (per la gestione dei livelli). */
	public boolean isPartitaVinta() {
		return this.partita.vinta();
	}

	public boolean isGiocatoreVivo() {
		return this.partita.giocatoreIsVivo();
	}

	/**
	 * Carica il labirinto di un livello dalla specifica testuale (risorsa nel
	 * classpath, per nome logico: funziona anche dentro il jar). In assenza
	 * della risorsa si ripiega sul labirinto cablato di default.
	 */
	private static Labirinto caricaLivello(String risorsa) {
		InputStream spec = DiaDia.class.getClassLoader().getResourceAsStream(risorsa);
		if (spec == null)
			return Labirinto.creaLabirintoDiDefault();
		try (Reader reader = new InputStreamReader(spec)) {
			return new CaricatoreLabirinto(reader).carica();
		} catch (IOException e) {
			return Labirinto.creaLabirintoDiDefault();
		}
	}

	public static void main(String[] argc) {
		// Lo Scanner su System.in vive per tutta la sessione di gioco e viene
		// chiuso una sola volta, qui, all'uscita del try-with-resources.
		try (Scanner scanner = new Scanner(System.in)) {
			IO io = new IOConsole(scanner);
			// Livelli di difficolta' crescente (POO-23): si vince passando
			// al livello successivo; se si perde si ricomincia dal primo.
			int livello = 0;
			while (livello < LIVELLI.size()) {
				io.mostraMessaggio("=== Livello " + (livello + 1) + " di " + LIVELLI.size() + " ===");
				DiaDia gioco = new DiaDia(caricaLivello(LIVELLI.get(livello)), io);
				gioco.gioca();
				if (gioco.isPartitaVinta()) {
					livello++;
					if (livello < LIVELLI.size())
						io.mostraMessaggio("Livello superato! Si passa al successivo...");
					else
						io.mostraMessaggio("Complimenti, hai completato tutti i livelli del gioco!");
				} else if (!gioco.isGiocatoreVivo()) {
					io.mostraMessaggio("Hai esaurito i CFU: si ricomincia dal primo livello...");
					livello = 0;
				} else {
					break; // comando 'fine': uscita dal gioco
				}
			}
		}
	}
}