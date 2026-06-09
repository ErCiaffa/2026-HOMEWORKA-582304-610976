package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.Configurazione;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagica extends Stanza {
    final static private int SOGLIA_MAGICA_DEFAULT = Configurazione.SOGLIA_MAGICA;

    private int contatoreAttrezziPosati;
    private int sogliaMagica;

    public StanzaMagica(String nome, int soglia) {
        super(nome);
        this.contatoreAttrezziPosati = 0;
        this.sogliaMagica = soglia;
    }

    public StanzaMagica(String nome) {
        this(nome, SOGLIA_MAGICA_DEFAULT);
    }

    @Override
    public boolean addAttrezzo(Attrezzo attrezzo) {
        this.contatoreAttrezziPosati++;
        if (this.contatoreAttrezziPosati > this.sogliaMagica)
            attrezzo = this.modificaAttrezzo(attrezzo);
        return super.addAttrezzo(attrezzo);
    }

    private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
        String nomeInvertito = new StringBuilder(attrezzo.getNome()).reverse().toString();
        int pesoDoppio = attrezzo.getPeso() * 2;
        return new Attrezzo(nomeInvertito, pesoDoppio);
    }

    public boolean isMagica() {
        return true;
    }
}
