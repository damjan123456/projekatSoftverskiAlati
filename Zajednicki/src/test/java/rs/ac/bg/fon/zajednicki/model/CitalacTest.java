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
public class CitalacTest {

    Citalac c;
    Mesto m;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        m = new Mesto();
        m.setIdMesto(11000);
        m.setNaziv("Beograd");

        c = new Citalac("Nikola", "Nikolic", "065111222", m);
        c.setIdCitalac(1);
    }

    @AfterEach
    void tearDown() {
        c = null;
        m = null;
    }

    @Test
    void testPodrazumevaniKonstruktor() {
        Citalac prazan = new Citalac();
        assertNotNull(prazan);
        assertEquals(0, prazan.getIdCitalac());
        assertNull(prazan.getIme());
        assertNull(prazan.getPrezime());
        assertNull(prazan.getBrojTel());
        assertNull(prazan.getMesto());
    }

    @ParameterizedTest
    @CsvSource({
        "065111222, 065111222, true",  
        "065111222, 065999999, false"  
    })
    void testEquals(String brTel1, String brTel2, boolean jednako) {
        c.setBrojTel(brTel1);

        Citalac c2 = new Citalac();
        c2.setBrojTel(brTel2);

        assertEquals(jednako, c.equals(c2));
    }

    @Test
    void testEqualsIstiObjekat() {
        assertTrue(c.equals(c));
    }

    @Test
    void testEqualsNull() {
        assertFalse(c.equals(null));
    }

    @Test
    void testEqualsRazlicitaKlasa() {
        assertFalse(c.equals((Object) new String("065111222")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "A", "Ab", "\n", "\t"})
    void testSetImeIzuzetak(String neispravnoIme) {
        assertThrows(IllegalArgumentException.class, () -> {
            c.setIme(neispravnoIme);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "B", "Bc", "\n", "\t"})
    void testSetPrezimeIzuzetak(String neispravnoPrezime) {
        assertThrows(IllegalArgumentException.class, () -> {
            c.setPrezime(neispravnoPrezime);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "123", "065111222334455", "\n", "\t"})
    void testSetBrojTelIzuzetak(String neispravanBroj) {
        assertThrows(IllegalArgumentException.class, () -> {
            c.setBrojTel(neispravanBroj);
        });
    }

    @Test
    void testSetMestoNullIzuzetak() {
        assertThrows(IllegalArgumentException.class, () -> {
            c.setMesto(null);
        });
    }

    @Test
    void testSetIdCitalac() {
        c.setIdCitalac(5);
        assertEquals(5, c.getIdCitalac());
    }

    @Test
    void testSetIme() {
        c.setIme("Marko");
        assertEquals("Marko", c.getIme());
    }

    @Test
    void testSetPrezime() {
        c.setPrezime("Markovic");
        assertEquals("Markovic", c.getPrezime());
    }

    @Test
    void testSetBrojTel() {
        c.setBrojTel("063777888");
        assertEquals("063777888", c.getBrojTel());
    }

    @Test
    void testSetMesto() {
        Mesto novoMesto = new Mesto();
        novoMesto.setIdMesto(21000);
        novoMesto.setNaziv("Novi Sad");
        
        c.setMesto(novoMesto);
        assertEquals(novoMesto, c.getMesto());
    }

    @Test
    void testToString() {
        String ocekivano = "Nikola Nikolic";
        assertEquals(ocekivano, c.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("citalac", c.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("ime,prezime,brojTel,idMesto", c.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "('Nikola','Nikolic','065111222',11000)";
        assertEquals(ocekivano, c.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("citalac.idCitalac=1", c.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "ime='Nikola',prezime='Nikolic',brojTel='065111222',idMesto=11000";
        assertEquals(ocekivano, c.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            c.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);

        when(mockResultSet.getInt("citalac.idCitalac")).thenReturn(100, 200);
        when(mockResultSet.getString("citalac.ime")).thenReturn("Petar", "Milica");
        when(mockResultSet.getString("citalac.prezime")).thenReturn("Petrovic", "Milovanovic");
        when(mockResultSet.getString("citalac.brojTel")).thenReturn("060111111", "060222222");

        when(mockResultSet.getInt("mesto.idMesto")).thenReturn(18000, 34000);
        when(mockResultSet.getString("mesto.naziv")).thenReturn("Nis", "Kragujevac");

        List<ApstraktniDomenskiObjekat> rezultat = c.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());

        Citalac c1 = (Citalac) rezultat.get(0);
        assertEquals(100, c1.getIdCitalac());
        assertEquals("Petar", c1.getIme());
        assertEquals(18000, c1.getMesto().getIdMesto());
        assertEquals("Nis", c1.getMesto().getNaziv());

        Citalac c2 = (Citalac) rezultat.get(1);
        assertEquals(200, c2.getIdCitalac());
        assertEquals("Milica", c2.getIme());
        assertEquals(34000, c2.getMesto().getIdMesto());
        assertEquals("Kragujevac", c2.getMesto().getNaziv());
    }
}