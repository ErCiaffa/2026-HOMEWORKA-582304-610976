package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.Partita;

public class ComandoVai extends AbstractComando {
	
private Direzione direzione;
	
	public ComandoVai(String direzione) {
		try {
			this.direzione = Direzione.valueOf(direzione.toUpperCase());
		}catch(Exception e) {
			this.direzione=null;
		}
		
	}
	public ComandoVai() {
		this(null);
	}

    @Override
    public void esegui(Partita partita) {
        if(parametro==null) {
            partita.getIO().mostraMessaggio("Dove vuoi andare ?");
            return;
        }
        Stanza prossimaStanza = partita.getStanzaCorrente().getStanzaAdiacente(parametro);
        if (prossimaStanza == null)
            partita.getIO().mostraMessaggio("Direzione inesistente");
        else {
            partita.setStanzaCorrente(prossimaStanza);
            partita.setCfu(partita.getCfu() - 1);
        }
        partita.getIO().mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
    }
    @Override
    public String getNome() {
        return "vai";
    }

}