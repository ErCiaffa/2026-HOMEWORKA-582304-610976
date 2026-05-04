package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.List;

public class IOSimulator implements IO {

    private String[] comandi;
    private int indice;
    private List<String> uscite;

    public IOSimulator(String[] comandi) {
        this.comandi = comandi;
        this.indice = 0;
        this.uscite = new ArrayList<>();
    }

    @Override
    public void mostraMessaggio(String msg) {
        this.uscite.add(msg);
    }

    @Override
    public String leggiRiga() {
        return this.comandi[this.indice++];
    }

    public List<String> getUscite() {
        return this.uscite;
    }
}
