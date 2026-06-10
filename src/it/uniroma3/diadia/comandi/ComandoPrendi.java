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
			this.getIO(partita).mostraMessaggio("Quale attrezzo vuoi prendere ?");
		else {
			Attrezzo attrezzo=partita.getStanzaCorrente().getAttrezzo(this.parametro);
			if(attrezzo!=null) {
				if(partita.getBorsa().addAttrezzo(attrezzo)) {
					this.getIO(partita).mostraMessaggio("Attrezzo inserito nella borsa !");
					partita.getStanzaCorrente().removeAttrezzo(attrezzo);
				}else
					this.getIO(partita).mostraMessaggio("La borsa è piena !");
			}else
				this.getIO(partita).mostraMessaggio("Attrezzo non presente nella stanza !");
		}
    }
    @Override
    public String getNome() {
        return "prendi";
    }
}


