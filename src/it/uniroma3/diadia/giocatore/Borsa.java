package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa {
    // TODO: Giocatore ha la responsabilità di gestire i CFU del giocatore e du memorizzare gli attrezzi in un oggetto istanza della classe Borsa
    // e aggiungere un riferimento ad un'istanza di Giocatore nella classe Partita (che ovviamente dovrà essere liberata dalla responsabilità spostate nella nuova classe)
    // >>(vedi codice nel pdf)
    /**
     * Questa classe modella una l'invetario del giocatore.
     * @author Ciaffaroni
     * @version 1.0
     */
    public final static int DEFAULT_PESO_MAX_BORSA = 10;
    private Attrezzo[] attrezzi;
    private int numeroAttrezzi;
    private int pesoMax;
    public Borsa() {
        this(DEFAULT_PESO_MAX_BORSA);
    }
    
    public Borsa(int pesoMax) {
        this.pesoMax = pesoMax;
        this.attrezzi = new Attrezzo[10]; // speriamo bastino...
        this.numeroAttrezzi = 0;
    }
    
    
    public boolean addAttrezzo(Attrezzo attrezzo) {
        if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
            return false;
        if (this.numeroAttrezzi==10)
            return false;
        if(attrezzo.getPeso()<0)
        	return false;
        this.attrezzi[this.numeroAttrezzi] = attrezzo;
        this.numeroAttrezzi++;
        return true;
    }
    
    
    public int getPesoMax() {
        return pesoMax;
    }
    
    public Attrezzo getAttrezzo(String nomeAttrezzo) {
        Attrezzo a = null;
        for (int i= 0; i<this.numeroAttrezzi; i++)
            if (this.attrezzi[i].getNome().equals(nomeAttrezzo))
                a = attrezzi[i];
        return a;
    }
    
    public int getPeso() {
        int peso = 0;
        for (int i= 0; i<this.numeroAttrezzi; i++)
            peso += this.attrezzi[i].getPeso();
        return peso;
    }
    
    public boolean isEmpty() {
        return this.numeroAttrezzi == 0;
    }
    
    public boolean hasAttrezzo(String nomeAttrezzo) {
        return this.getAttrezzo(nomeAttrezzo)!=null;
    }
    
    public Attrezzo removeAttrezzo(String nomeAttrezzo) {
        Attrezzo a = null;
        if(nomeAttrezzo!=null) {
        	for(int i=0; i<this.numeroAttrezzi; i++) {
        		if(this.attrezzi[i].getNome().equals(nomeAttrezzo)) {
        			a=this.attrezzi[i];
        			this.attrezzi[i]=this.attrezzi[this.numeroAttrezzi-1];
        			this.attrezzi[this.numeroAttrezzi-1]=null;
        			this.numeroAttrezzi--;
        			break;
        		}
        	}
        }
        return a;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        if (!this.isEmpty()) {
            s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
            for (int i= 0; i<this.numeroAttrezzi; i++)
                s.append(attrezzi[i].toString()+" ");
        }
        else
            s.append("Borsa vuota");
        return s.toString();
    }
}
