package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {

    @Override
    public void esegui(Partita partita) {
        // Stampa sempre la descrizione della stanza
        this.getIO(partita).mostraMessaggio(partita.getStanzaCorrente().getDescrizione());

        if (partita.giocatoreIsVivo()) {
            this.getIO(partita).mostraMessaggio("Il giocatore è vivo ed ha " + partita.getCfu() + " CFU");

            // 2. Usiamo uno switch sul parametro per decidere cosa stampare
            if (this.parametro == null) {
                // Comando base: solo "guarda"
                this.getIO(partita).mostraMessaggio("Inventario:\n" + partita.getBorsa().toString());
            } else {
                // Comando con parametro: "guarda peso", "guarda nome", ecc.
                switch (this.parametro.toLowerCase()) {
                    case "peso":
                        if (partita.getBorsa().isEmpty())
                            this.getIO(partita).mostraMessaggio("Inventario (ordinato per peso):\nBorsa Vuota");
                        else
                            this.getIO(partita).mostraMessaggio("Inventario (ordinato per peso):\n" + partita.getBorsa().getContenutoOrdinatoPerPeso());
                        break;
                    case "nome":
                        if (partita.getBorsa().isEmpty())
                            this.getIO(partita).mostraMessaggio("Inventario (ordinato per nome):\nBorsa Vuota");
                        else
                            this.getIO(partita).mostraMessaggio("Inventario (ordinato per nome):\n" + partita.getBorsa().getContenutoOrdinatoPerNome());
                        break;
                    case "gruppi":
                        if (partita.getBorsa().isEmpty())
                            this.getIO(partita).mostraMessaggio("Inventario (raggruppato per peso):\nBorsa Vuota");
                        else
                            this.getIO(partita).mostraMessaggio("Inventario (raggruppato per peso):\n" + partita.getBorsa().getContenutoRaggruppatoPerPeso());
                        break;
                    default:
                        // Se l'utente digita "guarda caso" o parole non previste
                        this.getIO(partita).mostraMessaggio("Inventario:\n" + partita.getBorsa().toString());
                        this.getIO(partita).mostraMessaggio("(Suggerimento: puoi usare 'guarda peso', 'guarda nome' o 'guarda gruppi')");
                        break;
                }
            }

        } else {
            this.getIO(partita).mostraMessaggio("Il giocatore ha perso!");
        }
    }

    @Override
    public String getNome() {
        return "guarda";
    }
}