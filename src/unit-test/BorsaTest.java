import it.uniroma3.diadia.attrezzi.Attrezzo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.giocatore.Borsa;

class BorsaTest {

	private Borsa borsa;
	private Attrezzo spada;
	private Attrezzo piuma;
	private Attrezzo negativo;

	@BeforeEach
	void setUp() throws Exception {
		this.borsa = new Borsa();
		this.spada = new Attrezzo("spada",11);
		this.piuma = new Attrezzo("piuma",1);
		this.negativo = new Attrezzo("negativo",-4);
	}

	@Test
	void testAddAttrezzo() {
		assertTrue(borsa.addAttrezzo(piuma),"Attrezzo piuma aggiunto");
		assertEquals(1,borsa.getPeso(),"Peso borsa uguale a 1");
	}

	@Test
	void testPesoEccessivo_e_is_empty() {
		assertFalse(borsa.addAttrezzo(spada),"Attrezzo troppo pesante: spada non aggiunta");
		assertTrue(borsa.isEmpty(),"Attrezzo non aggiunto, borsa vuota");
	}

	@Test
	void testPesoNegativo() {
		assertFalse(borsa.addAttrezzo(negativo),"Attrezzo negativo non aggiunto");
	}

	@Test
	void testRemoveAttrezzo() {
		borsa.addAttrezzo(piuma);
		Attrezzo rimosso = borsa.removeAttrezzo("piuma");
		assertNotNull(rimosso,"Attrezzo piuma rimosso");
	}

	@Test
	void testRemoveInesistente() {
		assertNull(borsa.removeAttrezzo("martello"));
	}

	@Test
	void testBorsaVuota() {
		assertEquals(0,borsa.getPeso(),"La borsa deve essere vuota");
	}

	@Test
	void testContenutoOrdinatoPerPeso() {
		
		Borsa b = new Borsa(25);
		b.addAttrezzo(new Attrezzo("piombo", 10));
		b.addAttrezzo(new Attrezzo("ps", 5));
		b.addAttrezzo(new Attrezzo("piuma", 1));
		b.addAttrezzo(new Attrezzo("libro", 5));

		List<Attrezzo> ordinata = b.getContenutoOrdinatoPerPeso();
		List<String> nomi = new ArrayList<>();
		for (Attrezzo a : ordinata) nomi.add(a.getNome());
		assertEquals(List.of("piuma", "libro", "ps", "piombo"), nomi,
				"Ordinato per peso con tiebreak sul nome");
	}

	@Test
	void testContenutoOrdinatoPerNome() {
		Borsa b = new Borsa(25);
		b.addAttrezzo(new Attrezzo("piombo", 10));
		b.addAttrezzo(new Attrezzo("ps", 5));
		b.addAttrezzo(new Attrezzo("piuma", 1));
		b.addAttrezzo(new Attrezzo("libro", 5));

		Set<Attrezzo> ordinato = b.getContenutoOrdinatoPerNome();
		List<String> nomi = new ArrayList<>();
		for (Attrezzo a : ordinato) nomi.add(a.getNome());
		assertEquals(List.of("libro", "piombo", "piuma", "ps"), nomi,
				"Ordinato alfabeticamente per nome");
	}

	@Test
	void testContenutoRaggruppatoPerPeso() {
		Borsa b = new Borsa(25);
		b.addAttrezzo(new Attrezzo("piombo", 10));
		b.addAttrezzo(new Attrezzo("ps", 5));
		b.addAttrezzo(new Attrezzo("piuma", 1));
		b.addAttrezzo(new Attrezzo("libro", 5));

		Map<Integer, Set<Attrezzo>> raggruppati = b.getContenutoRaggruppatoPerPeso();
		assertEquals(3, raggruppati.size(), "tre gruppi di peso distinti");
		assertEquals(1, raggruppati.get(1).size());
		assertEquals(2, raggruppati.get(5).size(), "ps e libro nello stesso gruppo");
		assertEquals(1, raggruppati.get(10).size());
	}

	@Test
	void testSortedSetOrdinatoPerPeso_StessoPesoNonCollassa() {
		// Test richiesto dall'issue #28: il classico tranello del Comparator
		// che ritorna 0 sui pesi uguali → il SortedSet considera gli elementi
		// uguali e ne perde uno. Con il tiebreak sul nome i due attrezzi
		// devono restare distinti.
		Borsa b = new Borsa(25);
		Attrezzo libro = new Attrezzo("libro", 5);
		Attrezzo ps = new Attrezzo("ps", 5);
		b.addAttrezzo(libro);
		b.addAttrezzo(ps);

		SortedSet<Attrezzo> ordinato = b.getSortedSetOrdinatoPerPeso();
		assertEquals(2, ordinato.size(),
				"Due attrezzi con stesso peso ma nomi diversi devono restare distinti");

		Iterator<Attrezzo> it = ordinato.iterator();
		assertEquals("libro", it.next().getNome(), "libro viene prima di ps in ordine alfabetico");
		assertEquals("ps", it.next().getNome());
	}

	@Test
	void testSortedSetOrdinatoPerPeso_OrdinePerPeso() {
		Borsa b = new Borsa(25);
		b.addAttrezzo(new Attrezzo("piombo", 10));
		b.addAttrezzo(new Attrezzo("ps", 5));
		b.addAttrezzo(new Attrezzo("piuma", 1));
		b.addAttrezzo(new Attrezzo("libro", 5));

		SortedSet<Attrezzo> ordinato = b.getSortedSetOrdinatoPerPeso();
		assertEquals(4, ordinato.size(), "tutti gli attrezzi presenti");
		List<String> nomi = new ArrayList<>();
		for (Attrezzo a : ordinato) nomi.add(a.getNome());
		assertEquals(List.of("piuma", "libro", "ps", "piombo"), nomi,
				"Ordinato per peso con tiebreak sul nome");
	}

}
