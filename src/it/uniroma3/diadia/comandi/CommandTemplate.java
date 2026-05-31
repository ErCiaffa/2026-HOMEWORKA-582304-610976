package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class CommandTemplate extends AbstractComando {
    @Override
    public void esegui(Partita partita) {}
    @Override
    public String getNome() {
        return "Nome";
    }
}


