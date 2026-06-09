package it.uniroma3.diadia.comandi;

import java.util.Scanner;

public class FabbricaDiComandiRiflessiva implements FabbricaDiComandi {
    private static final String PACKAGE = "it.uniroma3.diadia.comandi.";
    private static final String PREFISSO        = "Comando";

    @Override
    public Comando costruisciComando(String istruzione) {
        // NB: si lavora sull'istruzione originale per preservare il case del
        // parametro (es. nomi di attrezzi come "chiaveBagno"); solo il nome del
        // comando viene normalizzato per matchare il nome della classe.
        Scanner scannerDiParole = new Scanner(istruzione);
        String nomeComando = null;
        String parametro = null;

        if (scannerDiParole.hasNext())
            nomeComando = scannerDiParole.next(); // prima parola: nome del comando
        if (scannerDiParole.hasNext())
            parametro = scannerDiParole.next(); // seconda parola: eventuale parametro

        if (nomeComando == null)
            return new ComandoNonValido();

        // "vai"/"VAI" -> "Vai" (capitalizzazione necessaria per matchare ComandoVai)
        nomeComando = nomeComando.toLowerCase();
        String capitalizzato = Character.toUpperCase(nomeComando.charAt(0))
                             + nomeComando.substring(1);
        String fqcn = PACKAGE + PREFISSO + capitalizzato;

        try {
            Class<?> cls = Class.forName(fqcn);
            Comando comando = (Comando) cls.getDeclaredConstructor().newInstance();
            comando.setParametro(parametro); // DOPO newInstance
            return comando;
        } catch (ReflectiveOperationException e) {
            return new ComandoNonValido();
        }
    }
}
