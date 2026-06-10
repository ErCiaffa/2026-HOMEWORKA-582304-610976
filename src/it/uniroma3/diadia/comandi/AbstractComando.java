package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;


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
    private static final String PREFISSO = "Comando";
    private static final Set<String> CLASSI_ESCLUSE = Set.of("ComandoNonValido");
    private static Set<String> nomiComandiDisponibili;

    protected String parametro;

    /**
     * Restituisce i nomi di tutti i comandi del gioco, ricavati (una sola
     * volta) scandendo le classi {@code Comando*} di questo package: cosi'
     * l'elenco e' completo anche per comandi mai istanziati e resta
     * specificato in un unico posto, il nome delle classi stesse.
     */
    public static synchronized Set<String> getNomiComandiDisponibili() {
        if (nomiComandiDisponibili == null)
            nomiComandiDisponibili = scansionaNomiComandi();
        return Collections.unmodifiableSet(nomiComandiDisponibili);
    }

    private static Set<String> scansionaNomiComandi() {
        Set<String> nomi = new TreeSet<>();
        String pkg = AbstractComando.class.getPackageName().replace('.', '/') + "/";
        try {
            // sorgente del codice: directory delle classi (Eclipse) oppure jar
            Path sorgente = Path.of(AbstractComando.class.getProtectionDomain()
                                    .getCodeSource().getLocation().toURI());
            if (Files.isDirectory(sorgente)) {
                try (Stream<Path> file = Files.list(sorgente.resolve(pkg))) {
                    file.map(p -> p.getFileName().toString())
                        .forEach(n -> aggiungiSeComando(nomi, n));
                }
            } else {
                try (JarFile jar = new JarFile(sorgente.toFile())) {
                    jar.stream().map(JarEntry::getName)
                       .filter(n -> n.startsWith(pkg))
                       .map(n -> n.substring(pkg.length()))
                       .forEach(n -> aggiungiSeComando(nomi, n));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Impossibile elencare i comandi disponibili", e);
        }
        return nomi;
    }

    private static void aggiungiSeComando(Set<String> nomi, String nomeFile) {
        if (!nomeFile.endsWith(".class") || nomeFile.contains("$"))
            return;
        String classe = nomeFile.substring(0, nomeFile.length() - ".class".length());
        if (!classe.startsWith(PREFISSO) || classe.length() == PREFISSO.length()
                || CLASSI_ESCLUSE.contains(classe))
            return;
        String nome = classe.substring(PREFISSO.length());
        nomi.add(Character.toLowerCase(nome.charAt(0)) + nome.substring(1));
    }
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
    public String getNome() {
        String n = this.getClass().getSimpleName(); // "ComandoVai"
        n = n.replaceFirst("Comando", "");          // "Vai"
        return n.substring(0,1).toLowerCase() + n.substring(1); // "vai"
    }
}
