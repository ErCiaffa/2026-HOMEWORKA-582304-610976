package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.IOConsole;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa è la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
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
	
	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;
	private IOConsole console;
	public DiaDia(IOConsole console) {
		this.console = console;
		this.partita = new Partita();
	}

	public void gioca() {
		String istruzione;

		console.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do		
			istruzione = console.leggiRiga();
		while (!processaIstruzione(istruzione));
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione è eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		if (istruzione.isEmpty()) {
			console.mostraMessaggio("it.uniroma3.diadia.Comando - comando vuoto");
			return false;
		}
		Comando comandoDaEseguire = new Comando(istruzione);

		if (comandoDaEseguire.getNome().equals("fine")) {
			this.fine(); 
			return true;
		} else if (comandoDaEseguire.getNome().equals("vai"))
			this.vai(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("aiuto"))
			this.aiuto();
		else if(comandoDaEseguire.getNome().equals("prendi"))
			this.prendi(comandoDaEseguire.getParametro());
		else if(comandoDaEseguire.getNome().equals("posa"))
			this.posa(comandoDaEseguire.getParametro());
		else
			console.mostraMessaggio("it.uniroma3.diadia.Comando - comando sconosciuto");
		if (this.partita.vinta()) {
			console.mostraMessaggio("#Game - Hai vinto!");
			return true;
		} else
			return false;
	} 
	
	

	// implementazioni dei comandi dell'utente:
	
	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		console.mostraMessaggio("# Lista Comandi #");
		for(int i=0; i< elencoComandi.length; i++)
			console.mostraMessaggio((i+1)+"- "+elencoComandi[i]+" ");
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if(direzione==null)
			console.mostraMessaggio("#Game - Dove vuoi andare ?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			console.mostraMessaggio("Direzione inesistente");
		else {
			this.partita.setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getCfu();
			this.partita.setCfu(cfu--);
		}
		console.mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
	}
	public void prendi(String nomeAttrezzo) {
		if(nomeAttrezzo==null) {
			console.mostraMessaggio("Cosa vuoi prendere?");
			return;
		}
		Attrezzo attrezzo = partita.getStanzaCorrente().getAttrezzo(nomeAttrezzo);
		if(attrezzo!=null) {
			boolean aggiunto = partita.player.addAttrezzo(attrezzo);
			if(aggiunto) {
				partita.getStanzaCorrente().removeAttrezzo(attrezzo);
				console.mostraMessaggio("Hai preso: " + nomeAttrezzo);
			}
			else
				console.mostraMessaggio("Borsa piena o limite attrezzi raggiunto");
		}
		else
			console.mostraMessaggio("L'attrezzo " + nomeAttrezzo + " non è in questa stanza");
	}

	public void posa(String nomeAttrezzo) {
		if(nomeAttrezzo==null) {
			console.mostraMessaggio("Cosa vuoi posare?");
			return;
		}
		Attrezzo attrezzo = partita.player.getAttrezzo(nomeAttrezzo);
		if(attrezzo!=null) {
			boolean aggiunto = partita.getStanzaCorrente().addAttrezzo(attrezzo);
			if(aggiunto) {
				partita.player.removeAttrezzo(nomeAttrezzo);
				console.mostraMessaggio("Hai posato: " + nomeAttrezzo);
			}
			else
				console.mostraMessaggio("Stanza piena");
		}
		else
			console.mostraMessaggio("Il giocatore non possiede l'attrezzo " + nomeAttrezzo);
	}
	/**
	 * it.uniroma3.diadia.Comando "Fine".
	 */
	private void fine() {
		console.mostraMessaggio("#Game - Grazie di aver giocato! (fine)");  // si desidera smettere
	}

	public static void main(String[] argc) {
		IOConsole console = new IOConsole();
		DiaDia gioco = new DiaDia(console);
		gioco.gioca();
	}
}