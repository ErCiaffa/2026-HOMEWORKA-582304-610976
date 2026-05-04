package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Comando "Posa"
 * 
 * Cerca di prendere un oggetto dalla borsa,se presente lo posa nella stanza,
 * altrimenti stampa un messaggio di errore
 *  
 * @param nomeAttrezzo
 */
public class ComandoPosa implements Comando {

	private String nomeAttrezzo;
	
	
	public ComandoPosa(String nomeAttrezzo) {
		this.nomeAttrezzo=nomeAttrezzo;
	}
	
	public ComandoPosa() {
		this(null);
	}
	
	/**
	* esecuzione del comando
	*/
	//gli attrezzi posati vengono rimossi dalla borsa e aggiunti alla stanza
	@Override
	public void esegui(Partita partita) {
		if(this.nomeAttrezzo==null)
			partita.getIO().mostraMessaggio("Quale attrezzo vuoi posare ?");
		else {
			Attrezzo attrezzo=partita.getBorsa().getAttrezzo(this.nomeAttrezzo);
			if(attrezzo!=null) {
				if(partita.getStanzaCorrente().addAttrezzo(attrezzo)) {
					partita.getBorsa().removeAttrezzo(nomeAttrezzo);
					partita.getIO().mostraMessaggio("Attrezzo posato nella stanza !");
				}else
					partita.getIO().mostraMessaggio("Capienza stanza raggiunta,impossibile aggiungere l'attrezzo !");
			}else
				partita.getIO().mostraMessaggio("Attrezzo non presente nella borsa !");
		}
		//inputOutput.mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
	
		
	}

	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo=parametro;
		
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}

	@Override
	public String getNome() {
		return "posa";
	}
	
	
}

