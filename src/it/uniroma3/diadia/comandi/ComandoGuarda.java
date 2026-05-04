package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {

    @Override
    public void esegui(Partita partita) {
    	partita.getIO().mostraMessaggio(partita.getStanzaCorrente().getDescrizione());
    	if (partita.giocatoreIsVivo()) {
    		partita.getIO().mostraMessaggio("Il giocatore è vivo ed ha " + partita.getCfu() + "CFU");
    		partita.getIO().mostraMessaggio("Inventario:\n" + partita.getBorsa());
    	} else {
    		partita.getIO().mostraMessaggio("Il giocatore ha perso!");
    	}
    }
    @Override
    public void setParametro(String parametro) {

    }
    @Override
    public String getParametro() {
        return null;
    }
    @Override
    public String getNome() {
        return "Guarda";
    }
}


