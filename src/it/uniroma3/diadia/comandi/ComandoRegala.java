package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoRegala extends AbstractComando {

	private static final String MESSAGGIO_A_CHI = "A chi dovrei fare un regalo?...";
	private static final String MESSAGGIO_COSA = "Cosa dovrei regalare?";

	private String messaggio;

	public ComandoRegala(String nomeAttrezzo) {
		this.parametro = nomeAttrezzo;
	}

	public ComandoRegala() {
		this(null);
	}

	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio = partita.getStanzaCorrente().getPersonaggio();
		Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(this.parametro);
		if (attrezzo == null)
			this.messaggio = MESSAGGIO_COSA;
		else if (personaggio == null)
			this.messaggio = MESSAGGIO_A_CHI;
		else {
			this.messaggio = personaggio.riceviRegalo(attrezzo, partita);
			partita.getGiocatore().getBorsa().removeAttrezzo(this.parametro);
		}
		partita.getIO().mostraMessaggio(this.messaggio);
	}

	public String getMessaggio() {
		return this.messaggio;
	}
}
