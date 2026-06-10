import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.ComandoSaluta;
import it.uniroma3.diadia.personaggi.Strega;

class ComandoSalutaTest {

	private Partita partita;
	private IOSimulator io;

	@BeforeEach
	void setUp() {
		io = new IOSimulator(List.of());
		partita = new Partita(Labirinto.newBuilder()
				.addStanzaIniziale("atrio")
				.addStanzaVincente("uscita")
				.getLabirinto(), io);
	}

	@Test
	void saluta_personaggioPresente_rispondeConPresentazione() {
		partita.getStanzaCorrente().setPersonaggio(new Strega("Morgana", "Sono Morgana."));
		ComandoSaluta cmd = new ComandoSaluta();
		cmd.esegui(partita);
		assertTrue(cmd.getMessaggio().contains("Morgana"));
		assertTrue(cmd.getMessaggio().contains("Sono Morgana."));
	}

	@Test
	void saluta_nessunPersonaggio_chiedeChiSalutare() {
		ComandoSaluta cmd = new ComandoSaluta();
		cmd.esegui(partita);
		assertEquals("Chi dovrei salutare?...", cmd.getMessaggio());
	}

	@Test
	void getNome_esaluta() {
		assertEquals("saluta", new ComandoSaluta().getNome());
	}
}
