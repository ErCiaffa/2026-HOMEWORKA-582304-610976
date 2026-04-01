package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;


/**
 * Questa classe modella una partita del gioco
 *
 * @author Renda & Ciaffaroni
 * @version 1.2
 */

public class Partita {

	private Labirinto labirinto;
	private Stanza stanzaCorrente;
	
	private boolean finita;
	final Giocatore player;
	
	public Partita(){
		labirinto = new Labirinto();
		stanzaCorrente = labirinto.getStanzaIniziale();
		player = new Giocatore();
		this.finita = false;
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
	
	/**
	 * Restituisce vero se e solo se la partita è stata vinta
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.getStanzaCorrente()== this.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita è finita
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || (player.getCfu() <= 0);
	}

	/**
	 * Imposta la partita come finita
	 *
	 */
	public void setFinita() {
		this.finita = true;
	}

	public int getCfu() {
		return player.getCfu();
	}

	public void setCfu(int cfu) {
		player.setCfu(cfu);
	}

	public void prendi(String nomeAttrezzo) {
		if(nomeAttrezzo==null) {
			System.out.println("Cosa vuoi prendere?");
			return;
		}
		Attrezzo attrezzo = this.stanzaCorrente.getAttrezzo(nomeAttrezzo);
		if(attrezzo!=null) {
			boolean aggiunto = this.player.addAttrezzo(attrezzo);
			if(aggiunto) {
				this.stanzaCorrente.removeAttrezzo(attrezzo);
				System.out.println("Hai preso: " + nomeAttrezzo);
			}
			else
				System.out.println("Borsa piena o limite attrezzi raggiunto");
		}
		else
			System.out.println("L'attrezzo " + nomeAttrezzo + " non è in questa stanza");	
	}
	
	public void posa(String nomeAttrezzo) {
		if(nomeAttrezzo==null) {
			System.out.println("Cosa vuoi posare?");
			return;
		}
		Attrezzo attrezzo = this.player.getAttrezzo(nomeAttrezzo);
		if(attrezzo!=null) {
			boolean aggiunto = this.stanzaCorrente.addAttrezzo(attrezzo);
			if(aggiunto) {
				this.player.removeAttrezzo(nomeAttrezzo);
				System.out.println("Hai posato: " + nomeAttrezzo);
			}
			else
				System.out.println("Stanza piena");
		}
		else
			System.out.println("Il giocatore non possiede l'attrezzo " + nomeAttrezzo);
	}
}
