import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class AttrezzoTest {
	private Attrezzo a1;
	
	@BeforeEach
	void setUp() throws Exception {
		a1 = new Attrezzo("martello",3);
	}
	
	@Test
	void testAttrezzo() {
		assertNotNull(a1);
	}

	@Test
	void testGetNome() {
		assertNotNull(a1.getNome());
		assertEquals("martello", a1.getNome());
		assertNotEquals("cacciavite", a1.getNome());
		assertNotEquals(3, a1.getNome());
	}

	@Test
	void testGetPeso() {
		assertNotNull(a1.getNome());
		assertEquals(3, a1.getPeso());
		assertNotEquals(7, a1.getPeso());
		assertNotEquals("martello", a1.getPeso());
	}

	@Test
    void testToString() {
		assertNotNull(a1.toString());
		assertEquals("martello (3kg)", a1.toString());
		assertEquals("spada (3kg)", a1.toString());
    }

}
