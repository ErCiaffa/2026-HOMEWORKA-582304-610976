package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;


/**
 * Classe astratta che fornisce un'implementazione di default
 * della gestione del parametro per tutti i comandi.
 *
 * Le sottoclassi devono definire solo {@link #esegui(Partita)} e
 * {@link #getNome()}; ereditano gratuitamente {@link #setParametro(String)}
 * e {@link #getParametro()}, eliminando le implementazioni "vuote" o
 * ripetute (principio DRY).
 */
public abstract class AbstractComando implements Comando {
    private static final Set<String> nomiComandiDisponibili = new TreeSet<>();
    protected String parametro;
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
        return n.substring(0,1).toLowerCase() + n.substring(1); // "vai"
    }
}
