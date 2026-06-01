package rs.ac.bg.fon.zajednicki.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MestoTest {

    Mesto m;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        m = new Mesto(11000, "Beograd");
    }

    @AfterEach
    void tearDown() {
        m = null;
    }

    @ParameterizedTest
    @CsvSource({
        "Beograd, Beograd, true",   
        "Beograd, Novi Sad, false"   
    })
    void testEquals(String naziv1, String naziv2, boolean jednako) {
        m.setNaziv(naziv1);

        Mesto m2 = new Mesto();
        m2.setNaziv(naziv2);

        assertEquals(jednako, m.equals(m2));
    }

    @Test
    void testSetIdMesto() {
        m.setIdMesto(21000);
        assertEquals(21000, m.getIdMesto());
    }

    @Test
    void testSetNaziv() {
        m.setNaziv("Niš");
        assertEquals("Niš", m.getNaziv());
    }

    @Test
    void testToString() {
        assertEquals("Beograd", m.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("mesto", m.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naziv", m.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        assertEquals("('Beograd')", m.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("mesto.idMesto=11000", m.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        assertEquals("naziv='Beograd'", m.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            m.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testEqualsIgnoreCaseIzuzetak() {
        assertThrows(UnsupportedOperationException.class, () -> {
            m.equalsIgnoreCase("beograd");
        });
    }

    @Test
    void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getInt("mesto.idMesto")).thenReturn(11000, 21000, 18000);
        when(mockResultSet.getString("mesto.naziv")).thenReturn("Beograd", "Novi Sad", "Nis");

        List<ApstraktniDomenskiObjekat> rezultat = m.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(3, rezultat.size());

        Mesto m1 = (Mesto) rezultat.get(0);
        assertEquals(11000, m1.getIdMesto());
        assertEquals("Beograd", m1.getNaziv());

        Mesto m2 = (Mesto) rezultat.get(1);
        assertEquals(21000, m2.getIdMesto());
        assertEquals("Novi Sad", m2.getNaziv());

        Mesto m3 = (Mesto) rezultat.get(2);
        assertEquals(18000, m3.getIdMesto());
        assertEquals("Nis", m3.getNaziv());
    }
}