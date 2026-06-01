package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoFine extends AbstractComando {
    
    @Override
    public void esegui(Partita partita) {
    	partita.getIO().mostraMessaggio("Grazie di aver giocato!");
    	partita.setFinita();
    }
}


