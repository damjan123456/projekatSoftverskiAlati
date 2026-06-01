package rs.ac.bg.fon.zajednicki.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private Knjiga knjiga;
    private Date fiksniDatum;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        knjiga = new Knjiga(10, "Znakovi pored puta", "Ivo Andric", 2000.0);
        fiksniDatum = new Date();
        
        stavka = new StavkaZapisaOIznajmljivanju(1, 1, 2, 400.0, fiksniDatum, fiksniDatum, true, 2000.0, knjiga);
    }

    @AfterEach
    void tearDown() {
        stavka = null;
        knjiga = null;
        fiksniDatum = null;
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, 1, 1, true",   
        "1, 1, 1, 2, false",  
        "1, 1, 2, 1, false",  
        "1, 1, 5, 5, false"   
    })
    void testEquals(int idZapis1, int rb1, int idZapis2, int rb2, boolean jednako) {
        stavka.setZapis(idZapis1);
        stavka.setRb(rb1);

        StavkaZapisaOIznajmljivanju druga = new StavkaZapisaOIznajmljivanju();
        druga.setZapis(idZapis2);
        druga.setRb(rb2);

        assertEquals(jednako, stavka.equals(druga));
    }

    @Test
    void testSetZapis() {
        stavka.setZapis(100);
        assertEquals(100, stavka.getZapis());
    }

    @Test
    void testSetRb() {
        stavka.setRb(5);
        assertEquals(5, stavka.getRb());
    }

    @Test
    void testSetKolicina() {
        stavka.setKolicina(3);
        assertEquals(3, stavka.getKolicina());
    }

    @Test
    void testSetIznos() {
        stavka.setIznos(750.50);
        assertEquals(750.50, stavka.getIznos());
    }

    @Test
    void testSetDatumVracanja() {
        Date noviDatum = new Date(fiksniDatum.getTime() + 100000);
        stavka.setDatumVracanja(noviDatum);
        assertEquals(noviDatum, stavka.getDatumVracanja());
    }

    @Test
    void testSetMaxDatumVracanja() {
        Date noviMaxDatum = new Date(fiksniDatum.getTime() + 500000);
        stavka.setMaxDatumVracanja(noviMaxDatum);
        assertEquals(noviMaxDatum, stavka.getMaxDatumVracanja());
    }

    @Test
    void testSetVracenoNaVreme() {
        stavka.setVracenoNaVreme(false);
        assertEquals(false, stavka.isVracenoNaVreme());
    }

    @Test
    void testSetCenaZaNepovracaj() {
        stavka.setCenaZaNepovracaj(3500.0);
        assertEquals(3500.0, stavka.getCenaZaNepovracaj());
    }

    @Test
    void testSetKnjiga() {
        Knjiga novaKnjiga = new Knjiga(20, "Prokleta avlija", "Ivo Andric", 1500.0);
        stavka.setKnjiga(novaKnjiga);
        assertEquals(novaKnjiga, stavka.getKnjiga());
    }

    @Test
    void testToString() {
        assertEquals(knjiga.toString(), stavka.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("stavkazapisaoiznajmljivanju", stavka.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        String ocekivano = "rb,idZapis,datumVracanja,maxDatumVracanja,kolicina,iznos,cenaZaNepovracaj,vracenoNaVreme,idKnjiga";
        assertEquals(ocekivano, stavka.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sql1 = new java.sql.Date(fiksniDatum.getTime());
        java.sql.Date sql2 = new java.sql.Date(fiksniDatum.getTime());
        String ocekivano = "(1,1,'" + sql1 + "','" + sql2 + "',2,400.0,2000.0,true,10)";
        assertEquals(ocekivano, stavka.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("rb=1 AND idZapis=1", stavka.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        java.sql.Date sql1 = new java.sql.Date(fiksniDatum.getTime());
        java.sql.Date sql2 = new java.sql.Date(fiksniDatum.getTime());
        String ocekivano = "rb=1,idZapis=1,datumVracanja='" + sql1 + "',maxDatumVracanja='" + sql2 + "',kolicina=2,iznos=400.0,cenaZaNepovracaj=2000.0,vracenoNaVreme=true";
        assertEquals(ocekivano, stavka.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            stavka.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuDvaRedaUspesnoMapiranje() throws Exception {
        java.sql.Date sqlSad = new java.sql.Date(fiksniDatum.getTime());
        
        when(mockResultSet.next()).thenReturn(true, true, false);
        
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.idZapis")).thenReturn(5, 5);
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.rb")).thenReturn(1, 2);
        when(mockResultSet.getDate("stavkazapisaoiznajmljivanju.datumVracanja")).thenReturn(sqlSad, sqlSad);
        when(mockResultSet.getDate("stavkazapisaoiznajmljivanju.maxDatumVracanja")).thenReturn(sqlSad, sqlSad);
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.kolicina")).thenReturn(1, 2); 
        when(mockResultSet.getInt("stavkazapisaoiznajmljivanju.kolicina")).thenReturn(1, 2);
        when(mockResultSet.getDouble("stavkazapisaoiznajmljivanju.iznos")).thenReturn(200.0, 450.0);
        when(mockResultSet.getDouble("stavkazapisaoiznajmljivanju.cenaZaNepovracaj")).thenReturn(1800.0, 2200.0);
        when(mockResultSet.getBoolean("stavkazapisaoiznajmljivanju.vracenoNaVreme")).thenReturn(true, false);
        
        when(mockResultSet.getInt("knjiga.idKnjiga")).thenReturn(30, 40);
        when(mockResultSet.getString("knjiga.naslov")).thenReturn("Koreni", "Na Drini cuprija");
        when(mockResultSet.getString("knjiga.autor")).thenReturn("Dobrica Cosic", "Ivo Andric");
        when(mockResultSet.getDouble("knjiga.cenaZaNepovracaj")).thenReturn(1800.0, 2200.0);

        List<ApstraktniDomenskiObjekat> rezultat = stavka.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());
        
        StavkaZapisaOIznajmljivanju s1 = (StavkaZapisaOIznajmljivanju) rezultat.get(0);
        assertEquals(5, s1.getZapis());
        assertEquals(1, s1.getRb());
        assertEquals(1, s1.getKolicina());
        assertEquals(200.0, s1.getIznos());
        assertEquals(true, s1.isVracenoNaVreme());
        assertNotNull(s1.getKnjiga());
        assertEquals(30, s1.getKnjiga().getIdKnjiga());
        assertEquals("Koreni", s1.getKnjiga().getNaslov());
        
        StavkaZapisaOIznajmljivanju s2 = (StavkaZapisaOIznajmljivanju) rezultat.get(1);
        assertEquals(5, s2.getZapis());
        assertEquals(2, s2.getRb());
        assertEquals(2, s2.getKolicina());
        assertEquals(450.0, s2.getIznos());
        assertEquals(false, s2.isVracenoNaVreme());
        assertNotNull(s2.getKnjiga());
        assertEquals(40, s2.getKnjiga().getIdKnjiga());
        assertEquals("Na Drini cuprija", s2.getKnjiga().getNaslov());
    }
}