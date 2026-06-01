package it.uniroma3.diadia.ambienti;

public class Labirinto {

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;

	public Labirinto() {
		Labirinto def = new LabirintoBuilder()
				.addStanzaIniziale("Atrio")
					.addAttrezzo("osso", 1)
				.addStanza("Aula N11")
				.addStanza("Aula N10")
					.addAttrezzo("lanterna", 3)
				.addStanza("Laboratorio Campus")
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("Atrio", "Biblioteca", "nord")
				.addAdiacenza("Atrio", "Aula N11", "est")
				.addAdiacenza("Atrio", "Aula N10", "sud")
				.addAdiacenza("Atrio", "Laboratorio Campus", "ovest")
				.addAdiacenza("Aula N11", "Laboratorio Campus", "est")
				.addAdiacenza("Aula N11", "Atrio", "ovest")
				.addAdiacenza("Aula N10", "Atrio", "nord")
				.addAdiacenza("Aula N10", "Aula N11", "est")
				.addAdiacenza("Aula N10", "Laboratorio Campus", "ovest")
				.addAdiacenza("Laboratorio Campus", "Atrio", "est")
				.addAdiacenza("Laboratorio Campus", "Aula N11", "ovest")
				.addAdiacenza("Biblioteca", "Atrio", "sud")
				.getLabirinto();
		this.stanzaIniziale = def.getStanzaIniziale();
		this.stanzaVincente = def.getStanzaVincente();
	}

	Labirinto(boolean vuoto) {
		// usato dal LabirintoBuilder: non popola le stanze
	}

	public Stanza getStanzaIniziale() {
		return stanzaIniziale;
	}

	public Stanza getStanzaVincente() { return stanzaVincente; }

	void setStanzaIniziale(Stanza stanza) { this.stanzaIniziale = stanza; }
	void setStanzaVincente(Stanza stanza) { this.stanzaVincente = stanza; }
	
}
