package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Configurazione;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Cane extends AbstractPersonaggio {

	private static final String MESSAGGIO_MORSO = "Il cane ti ha morso! Hai perso 1 CFU.";
	private static final String MESSAGGIO_APPREZZAMENTO = "BAU, BAU!";

	public Cane(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {
		Giocatore giocatore = partita.getGiocatore();
		giocatore.setCfu(giocatore.getCfu() - 1);
		return MESSAGGIO_MORSO;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo == null || !attrezzo.getNome().equals(Configurazione.CANE_CIBO_PREFERITO))
			return agisci(partita);
		partita.getStanzaCorrente().addAttrezzo(attrezzo);
		return MESSAGGIO_APPREZZAMENTO;
	}
}
