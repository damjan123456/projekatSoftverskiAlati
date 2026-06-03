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
public class KnjigaTest {

    Knjiga k;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        k = new Knjiga(1, "Na Drini cuprija", "Ivo Andric", 1500.0);
    }

    @AfterEach
    void tearDown() {
        k = null;
    }

    @Test
    void testPodrazumevaniKonstruktor() {
        Knjiga prazan = new Knjiga();
        assertNotNull(prazan);
        assertEquals(0, prazan.getIdKnjiga());
        assertNull(prazan.getNaslov());
        assertNull(prazan.getAutor());
        assertEquals(0.0, prazan.getCenaZaNepovracaj());
    }

    @ParameterizedTest
    @CsvSource({
        "Na Drini cuprija, Ivo Andric, Na Drini cuprija, Ivo Andric, true",   
        "Na Drini cuprija, Ivo Andric, Prokleta avlija, Ivo Andric, false",  
        "Na Drini cuprija, Ivo Andric, Na Drini cuprija, Mesa Selimovic, false", 
        "Na Drini cuprija, Ivo Andric, Tvrdjava, Mesa Selimovic, false"      
    })
    void testEquals(String naslov1, String autor1, String naslov2, String autor2, boolean jednako) {
        k.setNaslov(naslov1);
        k.setAutor(autor1);

        Knjiga k2 = new Knjiga();
        k2.setNaslov(naslov2);
        k2.setAutor(autor2);

        assertEquals(jednako, k.equals(k2));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(k.equals(k));
    }

    @Test
    void testEqualsNull() {
        assertFalse(k.equals(null));
    }

    @Test
    void testEqualsRazlicitaKlasa() {
        assertFalse(k.equals(((Object)new String("Na Drini cuprija Ivo Andric"))));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\n", "\t"})
    void testSetNaslovIzuzetak(String neispravanNaslov) {
        assertThrows(IllegalArgumentException.class, () -> {
            k.setNaslov(neispravanNaslov);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\n", "\t"})
    void testSetAutorIzuzetak(String neispravanAutor) {
        assertThrows(IllegalArgumentException.class, () -> {
            k.setAutor(neispravanAutor);
        });
    }

    @Test
    void testSetCenaZaNepovracajNegativnaIzuzetak() {
        assertThrows(IllegalArgumentException.class, () -> {
            k.setCenaZaNepovracaj(-100.0);
        });
    }

    @Test
    void testSetIdKnjiga() {
        k.setIdKnjiga(42);
        assertEquals(42, k.getIdKnjiga());
    }

    @Test
    void testSetNaslov() {
        k.setNaslov("Zlocin i kazna");
        assertEquals("Zlocin i kazna", k.getNaslov());
    }

    @Test
    void testSetAutor() {
        k.setAutor("Fjodor Dostojevski");
        assertEquals("Fjodor Dostojevski", k.getAutor());
    }

    @Test
    void testSetCenaZaNepovracaj() {
        k.setCenaZaNepovracaj(2500.50);
        assertEquals(2500.50, k.getCenaZaNepovracaj());
    }

    @Test
    void testToString() {
        String ocekivano = "Na Drini cuprija Ivo Andric";
        assertEquals(ocekivano, k.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("knjiga", k.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("naslov,autor,cenaZaNepovracaj", k.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "('Na Drini cuprija','Ivo Andric',1500.0)";
        assertEquals(ocekivano, k.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("knjiga.idKnjiga=1", k.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "naslov='Na Drini cuprija',autor='Ivo Andric',cenaZaNepovracaj=1500.0";
        assertEquals(ocekivano, k.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            k.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, true, false);

        when(mockResultSet.getInt("knjiga.idKnjiga")).thenReturn(1, 2, 3);
        when(mockResultSet.getString("knjiga.naslov")).thenReturn("Knjiga A", "Knjiga B", "Knjiga C");
        when(mockResultSet.getString("knjiga.autor")).thenReturn("Autor A", "Autor B", "Autor C");
        when(mockResultSet.getDouble("knjiga.cenaZaNepovracaj")).thenReturn(1000.0, 1200.0, 1400.0);

        List<ApstraktniDomenskiObjekat> rezultat = k.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(3, rezultat.size());

        Knjiga k1 = (Knjiga) rezultat.get(0);
        assertEquals(1, k1.getIdKnjiga());
        assertEquals("Knjiga A", k1.getNaslov());
        assertEquals(1000.0, k1.getCenaZaNepovracaj());

        Knjiga k2 = (Knjiga) rezultat.get(1);
        assertEquals(2, k2.getIdKnjiga());
        assertEquals("Knjiga B", k2.getNaslov());
        assertEquals(1200.0, k2.getCenaZaNepovracaj());

        Knjiga k3 = (Knjiga) rezultat.get(2);
        assertEquals(3, k3.getIdKnjiga());
        assertEquals("Knjiga C", k3.getNaslov());
        assertEquals(1400.0, k3.getCenaZaNepovracaj());
    }
}