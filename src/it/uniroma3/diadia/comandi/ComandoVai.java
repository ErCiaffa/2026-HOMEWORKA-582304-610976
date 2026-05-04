package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.Partita;

public class ComandoVai implements Comando {
    private String direzione;

    @Override
    public void esegui(Partita partita) {
        if(direzione==null) {
            partita.getIO().mostraMessaggio("Dove vuoi andare ?");
            return;
        }
        Stanza prossimaStanza = partita.getStanzaCorrente().getStanzaAdiacente(direzione);
        if (prossimaStanza == null)
            partita.getIO().mostraMessaggio("Direzione inesistente");
        else {
            partita.setStanzaCorrente(prossimaStanza);
            partita.setCfu(partita.getCfu() - 1);
        }
        partita.getIO().mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
    }
    @Override
    public void setParametro(String parametro) {
        this.direzione = parametro;
    }
    @Override
    public String getParametro() {
        return this.direzione;
    }

    @Override
    public String getNome() {
        return "vai";
    }

}