package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.Partita;

public class ComandoVai extends AbstractComando {

    @Override
    public void esegui(Partita partita) {
        if(parametro==null) {
            this.getIO(partita).mostraMessaggio("Dove vuoi andare ?");
            return;
        }
        Stanza prossimaStanza = partita.getStanzaCorrente().getStanzaAdiacente(parametro);
        if (prossimaStanza == null)
            this.getIO(partita).mostraMessaggio("Direzione inesistente");
        else {
            partita.setStanzaCorrente(prossimaStanza);
            partita.setCfu(partita.getCfu() - 1);
        }
        this.getIO(partita).mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
    }
    @Override
    public String getNome() {
        return "vai";
    }

}