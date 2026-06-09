package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {

    private Direzione direzioneBloccata;
    private String nomeAttrezzoSbloccante;

    public StanzaBloccata(String nome, String direzioneBloccata, String nomeAttrezzoSbloccante) {
        super(nome);
        this.direzioneBloccata = Direzione.fromString(direzioneBloccata);
        this.nomeAttrezzoSbloccante = nomeAttrezzoSbloccante;
    }

    @Override
    public Stanza getStanzaAdiacente(Direzione direzione) {
        if (this.direzioneBloccata == direzione && !this.hasAttrezzo(this.nomeAttrezzoSbloccante))
            return this;
        return super.getStanzaAdiacente(direzione);
    }

    @Override
    public String getDescrizione() {
        String desc = super.getDescrizione();
        if (!this.hasAttrezzo(this.nomeAttrezzoSbloccante))
            desc += "\n[Passaggio " + this.direzioneBloccata.name().toLowerCase()
                    + " bloccato: serve " + this.nomeAttrezzoSbloccante + "]";
        return desc;
    }
}
