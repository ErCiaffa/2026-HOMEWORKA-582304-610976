package it.uniroma3.diadia.comandi;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class FabbricaDiComandiRiflessiva implements FabbricaDiComandi {
    private static final String PACKAGE = "it.uniroma3.diadia.comandi.";
    private static final String PREFISSO        = "Comando";
    static {
        try {
            String packageName = "it.uniroma3.diadia.comandi";
            String path = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);

            if (resource != null) {
                File directory = new File(resource.toURI());

                if (directory.exists() && directory.isDirectory() && directory.listFiles() != null) {

                    Arrays.stream(directory.listFiles())
                            .map(File::getName)
                            .filter(name -> name.endsWith(".class") && name.startsWith("Comando"))
                            .filter(name -> !name.equals("Comando.class")
                                    && !name.equals("ComandoNonValido.class")
                                    && !name.equals("AbstractComando.class"))
                            .forEach(className -> {
                                try {
                                    String nomeCompleto = packageName + "." + className.substring(0, className.length() - 6);
                                    // Istanzia la classe: questo attiva il costruttore di AbstractComando!
                                    Class.forName(nomeCompleto).getDeclaredConstructor().newInstance();
                                } catch (Exception e) {
                                    // Ignora classi non istanziabili (es. classi astratte)
                                }
                            });
                }
            }
        } catch (Exception e) {
            System.err.println("Errore nel boot automatico della fabbrica: " + e.getMessage());
        }
    }
    @Override
    public Comando costruisciComando(String istruzione) {
        Scanner scannerDiParole = new Scanner(istruzione.toLowerCase());
        String nomeComando = null;
        String parametro = null;

        if (scannerDiParole.hasNext())
            nomeComando = scannerDiParole.next(); // prima parola: nome del comando
        if (scannerDiParole.hasNext())
            parametro = scannerDiParole.next(); // seconda parola: eventuale parametro

        if (nomeComando == null)
            return new ComandoNonValido();

        // "vai" -> "Vai" (capitalizzazione necessaria per matchare ComandoVai)
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
