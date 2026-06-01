package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Borsa;
import it.uniroma3.diadia.giocatore.Giocatore;


/**
 * Questa classe modella una partita del gioco
 *
 * @author Renda & Ciaffaroni
 * @version 1.3
 */

public class Partita {

	private Labirinto labirinto;
	private Stanza stanzaCorrente;
	private IO io;
	private boolean finita;
	final Giocatore player;

	public Partita(Labirinto labirinto,IO io){
		this.setLabirinto(labirinto);
		this.player = new Giocatore();
		this.finita = false;
		this.io = io;
	}

	public Partita(Labirinto labirinto) {
		this(labirinto, new IOConsole());
	}

	public void setLabirinto(Labirinto labirinto) {
		this.labirinto = labirinto;
		this.stanzaCorrente = labirinto.getStanzaIniziale();
	}

	public IO getIO() {
		return io;
	}

	public Stanza getStanzaVincente() {
		return labirinto.getStanzaVincente();
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

	public boolean vinta() {
		return this.getStanzaCorrente()== this.getStanzaVincente();
	}

	public boolean isFinita() {
		return finita || vinta() || !giocatoreIsVivo();
	}

	public boolean giocatoreIsVivo() { return player.getCfu() > 0; }

	public void setFinita() {
		this.finita = true;
	}

	public int getCfu() {
		return player.getCfu();
	}

	public void setCfu(int cfu) {
		player.setCfu(cfu);
	}
	
	public Borsa getBorsa() {
		return player.getBorsa();
	}
	
}
