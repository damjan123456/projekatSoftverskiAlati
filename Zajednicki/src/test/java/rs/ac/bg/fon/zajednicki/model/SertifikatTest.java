package rs.ac.bg.fon.zajednicki.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SertifikatTest {

    Sertifikat s;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        s = new Sertifikat("Spring Boot", "Oracle");
        s.setIdSertifikat(1);
    }

    @AfterEach
    void tearDown() {
        s = null;
    }

    @Test
    void testPodrazumevaniKonstruktor() {
        Sertifikat prazan = new Sertifikat();
        assertNotNull(prazan);
        assertEquals(0, prazan.getIdSertifikat());
        assertNull(prazan.getNaziv());
        assertNull(prazan.getInstitucija());
    }

    @ParameterizedTest
    @CsvSource({
        "Spring Boot, Spring Boot, true",   
        "Spring Boot, Maven, false" 
    })
    void testEquals(String naziv1, String naziv2, boolean jednako) {
        s.setNaziv(naziv1);

        Sertifikat s2 = new Sertifikat();
        s2.setNaziv(naziv2);

        assertEquals(jednako, s.equals(s2));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(s.equals(s));
    }

    @Test
    void testEqualsNull() {
        assertFalse(s.equals(null));
    }

    @Test
    void testEqualsRazlicitaKlasa() {
        assertFalse(s.equals((Object)new String("Spring Boot")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\n", "\t"})
    void testSetNazivIzuzetak(String neispravanNaziv) {
        assertThrows(IllegalArgumentException.class, () -> {
            s.setNaziv(neispravanNaziv);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\n", "\t"})
    void testSetInstitucijaIzuzetak(String neispravnaInstitucija) {
        assertThrows(IllegalArgumentException.class, () -> {
            s.setInstitucija(neispravnaInstitucija);
        });
    }

    @Test
    void testSetIdSertifikat() {
        s.setIdSertifikat(15);
        assertEquals(15, s.getIdSertifikat());
    }

    @Test
    void testSetNaziv() {
        s.setNaziv("Spring Boot");
        assertEquals("Spring Boot", s.getNaziv());
    }

    @Test
    void testSetInstitucija() {
        s.setInstitucija("Amazon");
        assertEquals("Amazon", s.getInstitucija());
    }

    @Test
    void testToString() {
        assertEquals("Spring Boot", s.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("sertifikat", s.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naziv,institucija", s.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "('Spring Boot','Oracle')";
        assertEquals(ocekivano, s.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("sertifikat.idSertifikat=1", s.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "naziv='Spring Boot',institucija='Oracle'";
        assertEquals(ocekivano, s.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            s.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("sertifikat.idSertifikat")).thenReturn(10, 20);
        when(mockResultSet.getString("sertifikat.naziv")).thenReturn("Spring Boot", "Maven");
        when(mockResultSet.getString("sertifikat.institucija")).thenReturn("Oracle", "Amazon");

        List<ApstraktniDomenskiObjekat> rezultat = s.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());

        Sertifikat s1 = (Sertifikat) rezultat.get(0);
        assertEquals(10, s1.getIdSertifikat());
        assertEquals("Spring Boot", s1.getNaziv());
        assertEquals("Oracle", s1.getInstitucija());

        Sertifikat s2 = (Sertifikat) rezultat.get(1);
        assertEquals(20, s2.getIdSertifikat());
        assertEquals("Maven", s2.getNaziv());
        assertEquals("Amazon", s2.getInstitucija());
    }
}