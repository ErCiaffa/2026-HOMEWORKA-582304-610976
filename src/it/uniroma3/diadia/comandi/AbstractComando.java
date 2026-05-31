package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

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

    protected String parametro;

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
    public abstract String getNome();
}
