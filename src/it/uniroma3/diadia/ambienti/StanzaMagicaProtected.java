package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaProtected extends StanzaProtected {

    private static final int SOGLIA_MAGICA_DEFAULT = 3;

    private int contatoreAttrezziPosati;
    private int sogliaMagica;

    public StanzaMagicaProtected(String nome, int soglia) {
        super(nome);
        this.contatoreAttrezziPosati = 0;
        this.sogliaMagica = soglia;
    }

    public StanzaMagicaProtected(String nome) {
        this(nome, SOGLIA_MAGICA_DEFAULT);
    }

    @Override
    public boolean addAttrezzo(Attrezzo attrezzo) {
        if (attrezzo == null)
            return false;

        this.contatoreAttrezziPosati++;

        // Se superiamo la soglia, modifichiamo l'attrezzo (inverte nome e raddoppia peso)
        if (this.contatoreAttrezziPosati > this.sogliaMagica) {
            attrezzo = this.modificaAttrezzo(attrezzo);
        }

        return super.addAttrezzo(attrezzo);
    }

    private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
        String nomeInvertito = new StringBuilder(attrezzo.getNome()).reverse().toString();
        int pesoDoppio = attrezzo.getPeso() * 2;
        return new Attrezzo(nomeInvertito, pesoDoppio);
    }
}
