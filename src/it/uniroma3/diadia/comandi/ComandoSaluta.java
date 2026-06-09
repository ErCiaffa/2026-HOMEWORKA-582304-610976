package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoSaluta extends AbstractComando {

	private static final String MESSAGGIO_A_CHI = "Chi dovrei salutare?...";

	private String messaggio;

	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio = partita.getStanzaCorrente().getPersonaggio();
		if (personaggio != null)
			this.messaggio = personaggio.saluta();
		else
			this.messaggio = MESSAGGIO_A_CHI;
		partita.getIO().mostraMessaggio(this.messaggio);
	}

	public String getMessaggio() {
		return this.messaggio;
	}
}
