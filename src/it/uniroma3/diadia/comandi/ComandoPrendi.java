package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
    
	private String nomeAttrezzo;
	
	public ComandoPrendi(String nomeAttrezzo) {
		this.nomeAttrezzo=nomeAttrezzo;
	}
	
    @Override
    public void esegui(Partita partita) {
    	if(this.nomeAttrezzo==null)
			partita.getIO().mostraMessaggio("Quale attrezzo vuoi prendere ?");
		else {
			Attrezzo attrezzo=partita.getStanzaCorrente().getAttrezzo(this.nomeAttrezzo);
			if(attrezzo!=null) {
				if(partita.getBorsa().addAttrezzo(attrezzo)) {
					partita.getIO().mostraMessaggio("Attrezzo inserito nella borsa !");
					partita.getStanzaCorrente().removeAttrezzo(attrezzo);
				}else
					partita.getIO().mostraMessaggio("La borsa è piena !");
			}else
				partita.getIO().mostraMessaggio("Attrezzo non presente nella stanza !");
		}
    }
    @Override
    public void setParametro(String parametro) {
        this.nomeAttrezzo = parametro;
    }
    @Override
    public String getParametro() {
        return this.nomeAttrezzo;
    }
    @Override
    public String getNome() {
        return "prendi";
    }
}


