package it.uniroma3.diadia.ambienti;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;



/**
 * Classe it.uniroma3.diadia.ambienti.Stanza - una stanza in un gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco.
 * E' collegata ad altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 * 
 * @author docente di POO 
 * @see Attrezzo
 * @version base
*/

public class Stanza {
	
	static final private int NUMERO_MASSIMO_DIREZIONI = 4;
	static final private int NUMERO_MASSIMO_ATTREZZI = 10;
	
	private String nome;
    protected Map<String,Attrezzo> attrezzi;

    private Map<Direzione,Stanza> stanzeAdiacenti;

    private AbstractPersonaggio personaggio;

    /**
     * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
     * @param nome il nome della stanza
     */
    public Stanza(String nome) {
        this.nome = nome;
        this.stanzeAdiacenti = new HashMap<>();
        this.attrezzi = new LinkedHashMap<>();
    }

    /**
     * Imposta una stanza adiacente.
     *
     * @param direzione direzione in cui sara' posta la stanza adiacente.
     * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
     */
    public void impostaStanzaAdiacente(Direzione direzione, Stanza stanza) {
        if(this.stanzeAdiacenti.size()<NUMERO_MASSIMO_DIREZIONI)
        	this.stanzeAdiacenti.put(direzione,stanza);
    }

    /**
     * Restituisce la stanza adiacente nella direzione specificata
     * @param direzione
     */
    public Stanza getStanzaAdiacente(String direzione) {
		return this.stanzeAdiacenti.get(direzione);
	}

    /**
     * Restituisce la nome della stanza.
     * @return il nome della stanza
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Restituisce la descrizione della stanza.
     * @return la descrizione della stanza
     */
    public String getDescrizione() {
        return this.toString();
    }

    /**
     * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
     * 		   null se l'attrezzo non e' presente.
	 */
    public Attrezzo getAttrezzo(String nomeAttrezzo) {
        if(nomeAttrezzo==null)
        	return null;
        return this.attrezzi.get(nomeAttrezzo);
    }

    /**
     * Mette un attrezzo nella stanza.
     * @param attrezzo l'attrezzo da mettere nella stanza.
     * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
     */
    public boolean addAttrezzo(Attrezzo attrezzo) {
        if(attrezzo==null)
        	return false;
        if(this.attrezzi.size()<NUMERO_MASSIMO_ATTREZZI) {
        	this.attrezzi.put(attrezzo.getNome(), attrezzo);
        	return true;
        }
        return false;
    }

    public void setPersonaggio(AbstractPersonaggio personaggio) {
        this.personaggio = personaggio;
	}

    public AbstractPersonaggio getPersonaggio() {
        return this.personaggio;
    }

   /**
	* Restituisce una rappresentazione stringa di questa stanza,
	* stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	* @return la rappresentazione stringa
	*/
    public String toString() {
    	StringBuilder risultato = new StringBuilder();
    	risultato.append("Stanza: "+this.nome);
    	risultato.append("\nUscite: "+ this.stanzeAdiacenti.keySet().stream().map(Objects::toString)
                .collect(Collectors.joining(" ")));
        risultato.append("\nAttrezzi nella stanza: ");
        if (this.attrezzi.isEmpty()){
            risultato.append("Nessun Attrezzo");
        } else {
            risultato.append(this.attrezzi.values().stream()
                    .map(Attrezzo::toString)
                    .collect(Collectors.joining(" ")));
        }
        if(this.getPersonaggio()!=null)
        	risultato.append("\nPersonaggio: "+this.getPersonaggio().getClass().getSimpleName()+" "+this.getPersonaggio());
    	return risultato.toString();
    }

    /**
	* Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	* @return true se l'attrezzo esiste nella stanza, false altrimenti.
	*/
	public boolean hasAttrezzo(String nomeAttrezzo) {
		if (nomeAttrezzo == null) {
            return false;
        }
        
        return this.attrezzi.containsKey(nomeAttrezzo);
	}

	 /**
     * Restituisce la lista di attrezzi presenti nella stanza,
     * nell'ordine in cui sono stati aggiunti.
     */
	public List<Attrezzo> getAttrezzi() {
        return new ArrayList<>(this.attrezzi.values());
    }

	/**
	 * Restituisce la mappa delle stanze adiacenti per direzione.
	 */
	public Map<Direzione, Stanza> getMapStanzeAdiacenti() {
		return this.stanzeAdiacenti;
	}

	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param attrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(Attrezzo attrezzo) {
		if (attrezzo == null) {
            return false;
        }
        // .remove(chiave) cancella la coppia e restituisce l'oggetto rimosso.
        // Se restituisce null, significa che l'attrezzo non c'era.
        return this.attrezzi.remove(attrezzo.getNome()) != null;
	}


	public List<Direzione> getDirezioni() {
		return new ArrayList<Direzione>(this.stanzeAdiacenti.keySet());
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Stanza)) return false;
		Stanza s = (Stanza) o;
		return Objects.equals(this.nome, s.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.nome);
	}

}
