package rs.ac.bg.fon.zajednicki.komunikacija;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZahtevTest {

    Zahtev z;
    
    @BeforeEach
    void setUp() throws Exception {
        z = new Zahtev();
    }

    @AfterEach
    void tearDown() throws Exception {
        z = null;
    }

    @Test
    void testKonstruktorSaParametrima() {
        Object parametar = "TestParametar";
        Zahtev z2 = new Zahtev(Operacija.LOGIN, parametar);
        
        assertEquals(Operacija.LOGIN, z2.getOperacija());
        assertEquals(parametar, z2.getParametar());
    }

    @Test
    void testSetOperacija() {
        z.setOperacija(Operacija.LOGIN);
        assertEquals(Operacija.LOGIN, z.getOperacija());
    }

    @Test
    void testSetOperacijaNull() {
        z.setOperacija(null);
        assertNull(z.getOperacija());
    }

    @Test
    void testSetParametar() {
        String testString = "PodaciZaServer";
        z.setParametar(testString);
        assertEquals(testString, z.getParametar());
    }

    @Test
    void testSetParametarObjekat() {
        Object pomocniObjekat = new Object();
        z.setParametar(pomocniObjekat);
        assertEquals(pomocniObjekat, z.getParametar());
    }

    @Test
    void testSetParametarNull() {
        z.setParametar(null);
        assertNull(z.getParametar());
    }

}