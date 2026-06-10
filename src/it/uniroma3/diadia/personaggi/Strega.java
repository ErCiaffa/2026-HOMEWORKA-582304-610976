package it.uniroma3.diadia.personaggi;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {

	private static final String MESSAGGIO_RISATA = "AHAHAHAHAH!";
	private static final String MESSAGGIO_SOLITUDINE = "Non c'e' nessuna stanza dove spedirti... per stavolta resti qui!";
	private static final String MESSAGGIO_TRASFERIMENTO = "Ih ih ih, ti ho trasferito in: ";

	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}

	/**
	 * La strega e' permalosa: trasferisce il giocatore in una stanza
	 * adiacente a quella corrente; se NON e' stata ancora salutata, in
	 * quella con meno attrezzi, altrimenti in quella con piu' attrezzi
	 * (comportamento prescritto dalle trasparenze POO-classi-astratte-enum).
	 */
	@Override
	public String agisci(Partita partita) {
		Stanza corrente = partita.getStanzaCorrente();
		Collection<Stanza> adiacenti = corrente.getMapStanzeAdiacenti().values();
		if (adiacenti.isEmpty())
			return MESSAGGIO_SOLITUDINE;
		Comparator<Stanza> perNumeroAttrezzi =
				Comparator.comparingInt(s -> s.getAttrezzi().size());
		Stanza destinazione = this.haSalutato()
				? Collections.max(adiacenti, perNumeroAttrezzi)
				: Collections.min(adiacenti, perNumeroAttrezzi);
		partita.setStanzaCorrente(destinazione);
		return MESSAGGIO_TRASFERIMENTO + destinazione.getNome();
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo == null)
			return null;
		return MESSAGGIO_RISATA;
	}
}
