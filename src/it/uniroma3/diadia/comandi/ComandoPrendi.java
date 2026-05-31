package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi extends AbstractComando {

	public ComandoPrendi(String nomeAttrezzo) {
		this.parametro=nomeAttrezzo;
	}

	public ComandoPrendi() {
		this(null);
	}

    @Override
    public void esegui(Partita partita) {
    	if(this.parametro==null)
			partita.getIO().mostraMessaggio("Quale attrezzo vuoi prendere ?");
		else {
			Attrezzo attrezzo=partita.getStanzaCorrente().getAttrezzo(this.parametro);
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
    public String getNome() {
        return "prendi";
    }
}


