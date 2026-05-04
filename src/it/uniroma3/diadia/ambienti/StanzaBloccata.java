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
            return this;
        return super.getStanzaAdiacente(direzione);
    }

    @Override
    public String getDescrizione() {
        String desc = super.getDescrizione();
        if (!this.hasAttrezzo(this.nomeAttrezzoSbloccante))
            desc += "\n[Passaggio " + this.direzioneBloccata + " bloccato: serve " + this.nomeAttrezzoSbloccante + "]";
        return desc;
    }
}
