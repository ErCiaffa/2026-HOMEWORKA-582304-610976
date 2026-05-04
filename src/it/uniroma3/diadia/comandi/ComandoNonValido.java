package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {
   
    @Override
    public void esegui(Partita partita) {
    	partita.getIO().mostraMessaggio("Comando non valido!");
    }
    @Override
    public void setParametro(String parametro) {
    
    }
    @Override
    public String getParametro() {
        return null;
    }
    @Override
    public String getNome() {
        return "Comando non valido";
    }
}


