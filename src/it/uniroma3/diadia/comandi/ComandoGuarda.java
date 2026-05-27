package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {

    // 1. Aggiungiamo la variabile per salvare la seconda parola digitata dall'utente
    private String parametro;

    @Override
    public void esegui(Partita partita) {
        // Stampa sempre la descrizione della stanza
        partita.getIO().mostraMessaggio(partita.getStanzaCorrente().getDescrizione());

        if (partita.giocatoreIsVivo()) {
            partita.getIO().mostraMessaggio("Il giocatore è vivo ed ha " + partita.getCfu() + " CFU");

            // 2. Usiamo uno switch sul parametro per decidere cosa stampare
            if (this.parametro == null) {
                // Comando base: solo "guarda"
                partita.getIO().mostraMessaggio("Inventario:\n" + partita.getBorsa().toString());
            } else {
                // Comando con parametro: "guarda peso", "guarda nome", ecc.
                switch (this.parametro.toLowerCase()) {
                    case "peso":
                        if (partita.getBorsa().isEmpty())
                            partita.getIO().mostraMessaggio("Inventario (ordinato per peso):\nBorsa Vuota");
                        else
                            partita.getIO().mostraMessaggio("Inventario (ordinato per peso):\n" + partita.getBorsa().getContenutoOrdinatoPerPeso());
                        break;
                    case "nome":
                        if (partita.getBorsa().isEmpty())
                            partita.getIO().mostraMessaggio("Inventario (ordinato per nome):\nBorsa Vuota");
                        else
                            partita.getIO().mostraMessaggio("Inventario (ordinato per nome):\n" + partita.getBorsa().getContenutoOrdinatoPerNome());
                        break;
                    case "gruppi":
                        if (partita.getBorsa().isEmpty())
                            partita.getIO().mostraMessaggio("Inventario (raggruppato per peso):\nBorsa Vuota");
                        else
                            partita.getIO().mostraMessaggio("Inventario (raggruppato per peso):\n" + partita.getBorsa().getContenutoRaggruppatoPerPeso());
                        break;
                    default:
                        // Se l'utente digita "guarda caso" o parole non previste
                        partita.getIO().mostraMessaggio("Inventario:\n" + partita.getBorsa().toString());
                        partita.getIO().mostraMessaggio("(Suggerimento: puoi usare 'guarda peso', 'guarda nome' o 'guarda gruppi')");
                        break;
                }
            }

        } else {
            partita.getIO().mostraMessaggio("Il giocatore ha perso!");
        }
    }

    @Override
    public void setParametro(String parametro) {
        // 3. Ora salviamo il parametro invece di ignorarlo
        this.parametro = parametro;
    }

    @Override
    public String getParametro() {
        return this.parametro;
    }

    @Override
    public String getNome() {
        return "guarda";
    }
}