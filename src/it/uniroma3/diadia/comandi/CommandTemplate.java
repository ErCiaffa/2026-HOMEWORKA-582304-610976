package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class CommandTemplate implements Comando {
    private String param;
    @Override
    public void esegui(Partita partita) {}
    @Override
    public void setParametro(String parametro) {
        this.param = parametro;
    }
    @Override
    public String getParametro() {
        return this.param;
    }
    @Override
    public String getNome() {
        return "Nome";
    }
}


