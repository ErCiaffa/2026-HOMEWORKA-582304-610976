package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza {

    private String nomeAttrezzoNecessario;

    public StanzaBuia(String nome, String nomeAttrezzoNecessario) {
        super(nome);
        this.nomeAttrezzoNecessario = nomeAttrezzoNecessario;
    }

    @Override
    public String getDescrizione() {
        if (!this.hasAttrezzo(this.nomeAttrezzoNecessario))
            return "Qui c'è un buio pesto";
        return super.getDescrizione();
    }
}
