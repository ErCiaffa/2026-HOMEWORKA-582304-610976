package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Configurazione;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Borsa {
    /**
     * Questa classe modella una l' inventario del giocatore.
     * @author Ciaffaroni & Renda
     * @version 1.3
     */
    public final static int DEFAULT_PESO_MAX_BORSA = Configurazione.BORSA_PESO_MAX;
    private Map<String, Attrezzo> attrezzi;
    private int pesoMax;
    public Borsa() {
        this(DEFAULT_PESO_MAX_BORSA);
    }
    
    public Borsa(int pesoMax) {
        this.pesoMax = pesoMax;
        this.attrezzi = new HashMap<>();
    }
    
    public boolean addAttrezzo(Attrezzo attrezzo) {
        if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
            return false;
        if(attrezzo.getPeso()<0)
            return false;
        attrezzi.put(attrezzo.getNome(), attrezzo);
        return true;
    }
    
    public int getPesoMax() {
        return pesoMax;
    }
    
    public Attrezzo getAttrezzo(String nomeAttrezzo) {
        return attrezzi.get(nomeAttrezzo);
    }

    public int getPeso() {
        return this.attrezzi.values().stream()
                .mapToInt(Attrezzo::getPeso)
                .sum();
    }
    
    public boolean isEmpty() {
        return attrezzi.isEmpty();
    }
    
    public boolean hasAttrezzo(String nomeAttrezzo) {
        return this.attrezzi.containsKey(nomeAttrezzo);
    }
    
    public Attrezzo removeAttrezzo(String nomeAttrezzo) {
        return this.attrezzi.remove(nomeAttrezzo);
    }
    
    public String toString() {
        if (this.isEmpty()) {
            return "Borsa Vuota";
        }
        StringBuilder s = new StringBuilder();
        s.append("Contenuto borsa (")
                .append(this.getPeso())
                .append("kg/")
                .append(this.getPesoMax())
                .append("kg): ");

        String elenco = this.attrezzi.values().stream()
                .map(Attrezzo::toString)
                .collect(Collectors.joining(" "));
        s.append(elenco);

        return s.toString();
    }
    public List<Attrezzo> getContenutoOrdinatoPerPeso() {
        List<Attrezzo> listaOrdinata = new ArrayList<>(this.attrezzi.values());
        listaOrdinata.sort(Comparator.comparingInt(Attrezzo::getPeso)
                .thenComparing(Attrezzo::getNome));
        return listaOrdinata;
    }

    public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
        SortedSet<Attrezzo> setOrdinato = new TreeSet<>(Comparator.comparing(Attrezzo::getNome));
        setOrdinato.addAll(this.attrezzi.values());
        return setOrdinato;
    }

    public Map<Integer, Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
        return this.attrezzi.values().stream()
                .collect(Collectors.groupingBy(
                        Attrezzo::getPeso,
                        Collectors.toSet()
                ));
    }

    // Tiebreaker sul nome: senza, il TreeSet considererebbe uguali due attrezzi
    // di pari peso e ne perderebbe uno.
    public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
        SortedSet<Attrezzo> ordinato = new TreeSet<>(
                Comparator.comparingInt(Attrezzo::getPeso)
                        .thenComparing(Attrezzo::getNome));
        ordinato.addAll(this.attrezzi.values());
        return ordinato;
    }
}
