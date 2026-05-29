package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Cane extends AbstractPersonaggio {
	
	private static final String MESSAGGIO_MORSO = "Il cane ti ha morso! Hai perso 1 CFU.";
	private static final String MESSAGGIO_APPREZZAMENTO = "BAU,BAU!";

	public Cane(String nome, String presentaz) {
		super(nome, presentaz);
	}

	@Override
	public String agisci(Partita partita) {
		// 1. Recuperiamo il giocatore dalla partita
		Giocatore giocatore = partita.getGiocatore();
		
		// 2. Prendiamo i CFU attuali del giocatore
		int cfuAttuali = giocatore.getCfu();
		
		// 3. Decrementiamo i CFU (ad esempio di 1) e aggiorniamo il giocatore
		giocatore.setCfu(cfuAttuali - 1);
		
		// 4. Restituiamo la stringa che notifica l'azione al giocatore
		return MESSAGGIO_MORSO;
	}
	
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if(attrezzo==null || !attrezzo.getNome().equals("osso"))
			return agisci(partita);
		partita.getStanzaCorrente().addAttrezzo(attrezzo);
		return MESSAGGIO_APPREZZAMENTO;
		
	}

}
