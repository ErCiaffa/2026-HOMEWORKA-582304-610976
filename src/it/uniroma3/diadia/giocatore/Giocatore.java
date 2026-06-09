package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Configurazione;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Giocatore {

    static final private int CFU_INIZIALI = Configurazione.CFU_INIZIALI;
    private int cfu;
    final Borsa borsa;
    public Giocatore (){
        this.cfu = CFU_INIZIALI;
        this.borsa = new Borsa();
    }
    
    public int getCfu() {
        return this.cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public boolean addAttrezzo(Attrezzo attrezzo) {
        if (attrezzo == null)
            return false;
        return this.borsa.addAttrezzo(attrezzo);
    }
    
    public Attrezzo removeAttrezzo(String nomeAttrezzo) {
        return this.borsa.removeAttrezzo(nomeAttrezzo);
    }
    
    public Attrezzo getAttrezzo(String nomeAttrezzo) {
        return this.borsa.getAttrezzo(nomeAttrezzo);
    }
    
    public int getPesoMax(){
        return this.borsa.getPesoMax();
    }
    
    public boolean hasAttrezzo(String nomeAttrezzo) {
        return this.borsa.hasAttrezzo(nomeAttrezzo);
    }
    
    public boolean isEmpty(){
        return this.borsa.isEmpty();
    }
    
    public String printInventory(){
        return this.borsa.toString();
    }
    
    public Borsa getBorsa() {
    	return this.borsa;
    }
}
