package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

import java.util.Set;
import java.util.stream.Collectors;

public class ComandoAiuto extends AbstractComando {

    @Override
    public void esegui(Partita partita) {
        Set<String> nomi = AbstractComando.getNomiComandiDisponibili();
        String elenco = nomi.stream().collect(Collectors.joining(", "));
        partita.getIO().mostraMessaggio("Comandi disponibili: " + elenco);
    }
}


