package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {
	
	//private static final String MESSAGGIO_POSITIVO = "Sei stato spostato nella stanza con più attrezzi!";
	//private static final String MESSAGGIO_NEGATIVO = "Sei stato spostato nella stanza con meno attrezzi!";
	private static final String MESSAGGIO_RISATA = "AHAHAHAHAH!";
	
	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
    public String agisci(Partita partita) {
        return "Ah ah ah! " + getNome() + " ti deride!";
    }
	
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if(attrezzo==null)
			return null;
		
		return MESSAGGIO_RISATA;
		
	}

}
