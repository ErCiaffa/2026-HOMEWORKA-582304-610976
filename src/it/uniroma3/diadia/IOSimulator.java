package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IOSimulator implements IO {

    private final Iterator<String> input;
    private final List<String> uscite;

    public IOSimulator(List<String> comandi) {
        this.input = comandi.iterator();
        this.uscite = new ArrayList<>();
    }

    @Override
    public void mostraMessaggio(String msg) {
        this.uscite.add(msg);
    }

    @Override
    public String leggiRiga() {
        return this.input.next();
    }

    public List<String> getUscite() {
        return this.uscite;
    }
}
