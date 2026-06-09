package it.uniroma3.diadia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Carica le costanti del gioco da "diadia.properties" (sul classpath) una sola
 * volta e le espone come campi statici tipizzati. Se il file manca o una chiave
 * non e' presente vengono usati dei valori di default sicuri.
 */
public class Configurazione {

    private static final String FILE = "diadia.properties";
    private static final Properties PROPS = carica();

    public static final int CFU_INIZIALI = leggiInt("cfu.iniziali", 20);
    public static final int BORSA_PESO_MAX = leggiInt("borsa.peso.max", 10);
    public static final int SOGLIA_MAGICA = leggiInt("stanza.magica.soglia.default", 3);
    public static final String CANE_CIBO_PREFERITO = PROPS.getProperty("cane.cibo.preferito", "osso");

    private static Properties carica() {
        Properties p = new Properties();
        try (InputStream in = Configurazione.class.getClassLoader().getResourceAsStream(FILE)) {
            if (in != null)
                p.load(in);
        } catch (IOException e) {
            // si prosegue con i default
        }
        return p;
    }

    private static int leggiInt(String chiave, int valoreDefault) {
        try {
            return Integer.parseInt(PROPS.getProperty(chiave, String.valueOf(valoreDefault)));
        } catch (NumberFormatException e) {
            return valoreDefault;
        }
    }
}
