import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagica extends Stanza {
    final static private int SOGLIA_MAGICA_DEFAULT=3;

    private int contatoreAttrezziPosati;
    private int sogliaMagica;

    public StanzaMagica(String nome,int soglia) {
        super(nome);
        this.contatoreAttrezziPosati=0;
        this.sogliaMagica=soglia;
    }

    public StanzaMagica(String nome) {
        this(nome,SOGLIA_MAGICA_DEFAULT);
    }

    @Override
    public boolean addAttrezzo(Attrezzo attrezzo) {
        this.contatoreAttrezziPosati++;
        if (this.contatoreAttrezziPosati>this.sogliaMagica)
            attrezzo = this.modificaAttrezzo(attrezzo);
        return super.addAttrezzo(attrezzo);
    }

    private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
        int pesoEdit = attrezzo.getPeso();
        String nameEdit = new StringBuilder(attrezzo.getNome()).reverse().toString();
        return  new Attrezzo(nameEdit,pesoEdit);
    }
}