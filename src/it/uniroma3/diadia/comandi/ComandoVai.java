package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;

public class ComandoVai implements Comando {
    private String direzione;
    private IOConsole console;
    /**
     * esecuzione del comando
     */
    @Override
    public void esegui(Partita partita) {
        if(direzione==null)
            console.mostraMessaggio("#Game - Dove vuoi andare ?");
        Stanza prossimaStanza = null;
        prossimaStanza = partita.getStanzaCorrente().getStanzaAdiacente(direzione);
        if (prossimaStanza == null)
            console.mostraMessaggio("Direzione inesistente");
        else {
            partita.setStanzaCorrente(prossimaStanza);
            int cfu = partita.getCfu();
            partita.setCfu(cfu--);
        }
        console.mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
    }
    @Override
    public void setParametro(String parametro) {
        this.direzione = parametro;
    }
}