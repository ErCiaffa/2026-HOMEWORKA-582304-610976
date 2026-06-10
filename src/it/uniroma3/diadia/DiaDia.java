package it.uniroma3.diadia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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

	/** Nome logico (risorsa nel classpath) della specifica del labirinto. */
	static final private String FILE_LABIRINTO = "labirinto-universita.txt";

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
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();
		comandoDaEseguire = factory.costruisciComando(istruzione);
		comandoDaEseguire.esegui(this.partita);
		if (this.partita.vinta())

			io.mostraMessaggio("Hai vinto!");
		if (!this.partita.giocatoreIsVivo())

			io.mostraMessaggio("Hai esaurito i CFU...");

		return this.partita.isFinita();
	}

	/**
	 * Es.15: il labirinto del gioco viene letto dalla specifica testuale
	 * {@value #FILE_LABIRINTO}, cercata per nome logico nel classpath
	 * (quindi anche dentro il jar). In sua assenza si ripiega sul
	 * labirinto cablato di default.
	 */
	private static Labirinto caricaLabirintoUniversita() {
		InputStream spec = DiaDia.class.getClassLoader().getResourceAsStream(FILE_LABIRINTO);
		if (spec == null)
			return Labirinto.creaLabirintoDiDefault();
		try (Reader reader = new InputStreamReader(spec)) {
			return new CaricatoreLabirinto(reader).carica();
		} catch (IOException e) {
			return Labirinto.creaLabirintoDiDefault();
		}
	}

	public static void main(String[] argc) {
		// Lo Scanner su System.in vive per tutta la partita e viene chiuso
		// una sola volta, qui, all'uscita del try-with-resources.
		try (Scanner scanner = new Scanner(System.in)) {
			IO io = new IOConsole(scanner);
			DiaDia gioco = new DiaDia(caricaLabirintoUniversita(), io);
			gioco.gioca();
		}
	}
}