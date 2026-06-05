package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoRegala implements Comando{
	
	private static final String MESSAGGIO_A_CHI = "A chi dovrei fare un regalo?...";
	private static final String MESSAGGIO_COSA = "Cosa dovrei regalare?";
	private String nomeAttrezzo;
	private String messaggio;
	private IO io;
	
	public ComandoRegala(String nomeAttrezzo) {
		this.nomeAttrezzo=nomeAttrezzo;
	}
	
	public ComandoRegala() {
		this(null);
	}

	@Override
	public void esegui(Partita partita) {
		this.io=partita.getIO();
		AbstractPersonaggio personaggio;
		personaggio = partita.getStanzaCorrente().getPersonaggio();
			Attrezzo a = partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
			if(a!=null) { 
				if (personaggio!=null) {
						this.messaggio= personaggio.riceviRegalo(a,partita);
						partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
						io.mostraMessaggio(this.messaggio);
					
				} else 
					io.mostraMessaggio(MESSAGGIO_A_CHI);
			}
			else
				io.mostraMessaggio(MESSAGGIO_COSA);
		
	}

	public String getMessaggio() {
		return this.messaggio;
	}
	

	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}
	

	@Override
	public String getNome() {
		return this.getNome();
	}
	

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}

}
