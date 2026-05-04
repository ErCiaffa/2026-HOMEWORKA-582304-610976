package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.Partita;

public class ComandoVai implements Comando {
    private String direzione;
    private IO io;
    /**
     * Esecuzione del comando
     */
    @Override
    public void esegui(Partita partita) {
        if(direzione==null)
            io.mostraMessaggio("#Game - Dove vuoi andare ?");
        Stanza prossimaStanza = null;
        prossimaStanza = partita.getStanzaCorrente().getStanzaAdiacente(direzione);
        if (prossimaStanza == null)
            io.mostraMessaggio("Direzione inesistente");
        else {
            partita.setStanzaCorrente(prossimaStanza);
            int cfu = partita.getCfu();
            partita.setCfu(cfu--);
        }
        io.mostraMessaggio("Stanza Corrente: "+ partita.getStanzaCorrente().getDescrizione());
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