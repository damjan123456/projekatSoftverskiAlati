package rs.ac.bg.fon.zajednicki.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.Date;
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
public class StavkaZapisaOIznajmljivanjuTest {

    private StavkaZapisaOIznajmljivanju stavka;
    private Knjiga k;
    private Date datumVracanja;
    private Date maxDatumVracanja;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        datumVracanja = new Date();
        maxDatumVracanja = new Date(datumVracanja.getTime() + 864000000); // +10 dana
        
        k = new Knjiga();
        k.setIdKnjiga(50);
        k.setNaslov("Na Drini cuprija");
        
        stavka = new StavkaZapisaOIznajmljivanju(100, 1, 2, 300.0, datumVracanja, maxDatumVracanja, true, 1000.0, k);
    }

    @AfterEach
    void tearDown() {
        stavka = null;
        k = null;
    }

    @Test
    void testSetZapisUspesno() {
        stavka.setZapis(150);
        assertEquals(150, stavka.getZapis());
    }

    @Test
    void testSetZapisNula() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setZapis(0));
    }

    @Test
    void testSetZapisNegativan() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setZapis(-5));
    }

    @Test
    void testSetRbUspesno() {
        stavka.setRb(5);
        assertEquals(5, stavka.getRb());
    }

    @Test
    void testSetRbNula() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setRb(0));
    }

    @Test
    void testSetRbNegativan() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setRb(-1));
    }

    @Test
    void testSetKolicinaUspesnoDonjaGranica() {
        stavka.setKolicina(1);
        assertEquals(1, stavka.getKolicina());
    }

    @Test
    void testSetKolicinaUspesnoGornjaGranica() {
        stavka.setKolicina(10);
        assertEquals(10, stavka.getKolicina());
    }

    @Test
    void testSetKolicinaNula() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setKolicina(0));
    }

    @Test
    void testSetKolicinaPrekoDeset() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setKolicina(11));
    }

    @Test
    void testSetIznosUspesno() {
        stavka.setIznos(550.25);
        assertEquals(550.25, stavka.getIznos());
    }

    @Test
    void testSetIznosNegativan() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setIznos(-1.0));
    }

    @Test
    void testSetDatumVracanja() {
        Date noviDatum = new Date();
        stavka.setDatumVracanja(noviDatum);
        assertEquals(noviDatum, stavka.getDatumVracanja());
    }

    @Test
    void testSetMaxDatumVracanjaUspesno() {
        Date noviMax = new Date();
        stavka.setMaxDatumVracanja(noviMax);
        assertEquals(noviMax, stavka.getMaxDatumVracanja());
    }

    @Test
    void testSetMaxDatumVracanjaNull() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setMaxDatumVracanja(null));
    }

    @Test
    void testSetVracenoNaVreme() {
        stavka.setVracenoNaVreme(false);
        assertFalse(stavka.isVracenoNaVreme());
        stavka.setVracenoNaVreme(true);
        assertTrue(stavka.isVracenoNaVreme());
    }

    @Test
    void testSetCenaZaNepovracajUspesno() {
        stavka.setCenaZaNepovracaj(1200.0);
        assertEquals(1200.0, stavka.getCenaZaNepovracaj());
    }

    @Test
    void testSetCenaZaNepovracajNegativna() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setCenaZaNepovracaj(-100));
    }

    @Test
    void testSetKnjigaUspesno() {
        Knjiga novaK = new Knjiga();
        novaK.setIdKnjiga(99);
        stavka.setKnjiga(novaK);
        assertEquals(novaK, stavka.getKnjiga());
    }

    @Test
    void testSetKnjigaNull() {
        assertThrows(IllegalArgumentException.class, () -> stavka.setKnjiga(null));
    }

    @Test
    void testToString() {
        assertEquals(k.toString(), stavka.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("stavkazapisaoiznajmljivanju", stavka.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("rb,idZapis,datumVracanja,maxDatumVracanja,kolicina,iznos,cenaZaNepovracaj,vracenoNaVreme,idKnjiga", stavka.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum1 = new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 = new java.sql.Date(maxDatumVracanja.getTime());
        String ocekivano = "(1,100,'" + sqlDatum1 + "','" + sqlDatum2 + "',2,300.0,1000.0,true,50)";
        assertEquals(ocekivano, stavka.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("rb=1 AND idZapis=100", stavka.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        java.sql.Date sqlDatum1 = new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 = new java.sql.Date(maxDatumVracanja.getTime());
        String ocekivano = "rb=1,idZapis=100,datumVracanja='" + sqlDatum1 + "',maxDatumVracanja='" + sqlDatum2 + "',kolicina=2,iznos=300.0,cenaZaNepovracaj=1000.0,vracenoNaVreme=true";
        assertEquals(ocekivano, stavka.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> stavka.vratiObjekatIzRS(mockResultSet));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 100, 1, 100, true",
        "1, 100, 2, 100, false",
        "1, 100, 1, 150, false",
        "1, 100, 2, 150, false"
    })
    void testEquals(int rb1, int idZapis1, int rb2, int idZapis2, boolean jednako) {
        StavkaZapisaOIznajmljivanju s1 = new StavkaZapisaOIznajmljivanju();
        s1.setRb(rb1);
        s1.setZapis(idZapis1);

        StavkaZapisaOIznajmljivanju s2 = new StavkaZapisaOIznajmljivanju();
        s2.setRb(rb2);
        s2.setZapis(idZapis2);

        assertEquals(jednako, s1.equals(s2));
    }

    @Test
    void testVratiListuUspesno() throws Exception {
        java.sql.Date sqlDatum1 = new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 = new java.sql.Date(maxDatumVracanja.getTime());

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.idZapis")).thenReturn(100);
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.rb")).thenReturn(1);
        when(mockResultSet.getDate("stavkazapisaoiznajmljivanju.datumVracanja")).thenReturn(sqlDatum1);
        when(mockResultSet.getDate("stavkazapisaoiznajmljivanju.maxDatumVracanja")).thenReturn(sqlDatum2);
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.kolicina")).thenReturn(2);
        when(mockResultSet.getDouble("stavkazapisaoiznajmljivanju.iznos")).thenReturn(300.0);
        when(mockResultSet.getDouble("stavkazapisaoiznajmljivanju.cenaZaNepovracaj")).thenReturn(1000.0);
        when(mockResultSet.getBoolean("stavkazapisaoiznajmljivanju.vracenoNaVreme")).thenReturn(true);

        when(mockResultSet.getInt("knjiga.idKnjiga")).thenReturn(50);
        when(mockResultSet.getString("knjiga.naslov")).thenReturn("Na Drini cuprija");
        when(mockResultSet.getString("knjiga.autor")).thenReturn("Ivo Andric");
        when(mockResultSet.getDouble("knjiga.cenaZaNepovracaj")).thenReturn(1000.0);

        List<ApstraktniDomenskiObjekat> rezultat = stavka.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.size());
        StavkaZapisaOIznajmljivanju mapirana = (StavkaZapisaOIznajmljivanju) rezultat.get(0);
        assertEquals(100, mapirana.getZapis());
        assertEquals(1, mapirana.getRb());
        assertEquals(50, mapirana.getKnjiga().getIdKnjiga());
        assertEquals("Na Drini cuprija", mapirana.getKnjiga().getNaslov());
    }
}