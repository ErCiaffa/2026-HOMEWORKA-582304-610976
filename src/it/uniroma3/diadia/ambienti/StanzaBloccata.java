package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {

    private String direzioneBloccata;
    private String nomeAttrezzoSbloccante;

    public StanzaBloccata(String nome, String direzioneBloccata, String nomeAttrezzoSbloccante) {
        super(nome);
        this.direzioneBloccata = direzioneBloccata;
        this.nomeAttrezzoSbloccante = nomeAttrezzoSbloccante;
    }

    @Override
    public Stanza getStanzaAdiacente(String direzione) {
        if (this.direzioneBloccata.equals(direzione) && !this.hasAttrezzo(this.nomeAttrezzoSbloccante))
            return null;
        return super.getStanzaAdiacente(direzione);
    }
}
