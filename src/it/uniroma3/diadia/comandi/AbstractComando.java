package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractComando implements Comando {

    // L'elenco visibile dal comando aiuto richiesto dal testo
    private static final Set<String> nomiComandiDisponibili = new TreeSet<>();

    protected String parametro;

    // Al momento della creazione degli oggetti istanza, registra il nome nell'elenco
    protected AbstractComando() {
        nomiComandiDisponibili.add(this.getNome());
    }

    public static Set<String> getNomiComandiDisponibili() {
        return Collections.unmodifiableSet(nomiComandiDisponibili);
    }

    @Override
    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    @Override
    public String getParametro() {
        return this.parametro;
    }

    @Override
    public abstract void esegui(Partita partita);

    @Override
    public String getNome() {
        String n = this.getClass().getSimpleName(); // "ComandoVai"
        n = n.replaceFirst("Comando", "");          // "Vai"
        return n.substring(0, 1).toLowerCase() + n.substring(1); // "vai"
    }
}