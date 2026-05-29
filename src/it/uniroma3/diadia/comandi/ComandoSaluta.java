package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoSaluta implements Comando {
	
	private static final String MESSAGGIO_A_CHI = "Chi dovrei salutare?...";

	private String messaggio;
	private String parametro;
	private IO io;
	
	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio;
		personaggio = partita.getStanzaCorrente().getPersonaggio();
		if (personaggio!=null) {
			this.messaggio = personaggio.saluta();
			io.mostraMessaggio(this.messaggio);
		}
		else 
			io.mostraMessaggio(MESSAGGIO_A_CHI);
	}

	public String getMessaggio() {
		return this.messaggio;
	}
	
	@Override
	public void setParametro(String parametro) {
		this.parametro=parametro;
	}

	@Override
	public String getParametro() {
		return this.parametro;
	}

	@Override
	public String getNome() {
		return "saluta";
	}

}
