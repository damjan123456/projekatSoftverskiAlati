package rs.ac.bg.fon.zajednicki.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ZapisOIznajmljivanjuTest {

    private ZapisOIznajmljivanju z;
    private Bibliotekar b;
    private Citalac c;
    private List<StavkaZapisaOIznajmljivanju> stavke;
    private Date datum;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        datum = new Date();
        b = new Bibliotekar(1, "Damjan", "Djuric", "064123456", "damjan", "sifra123");
        
        c = new Citalac();
        c.setIdCitalac(10);
        
        stavke = new ArrayList<>();
        StavkaZapisaOIznajmljivanju s1 = new StavkaZapisaOIznajmljivanju();
        s1.setRb(1);

        Knjiga k = new Knjiga();
        s1.setKnjiga(k);
        stavke.add(s1);

        z = new ZapisOIznajmljivanju(datum, 500.0, b, c, stavke);
        z.setIdZapis(100);
    }

    @AfterEach
    void tearDown() {
        z = null;
        b = null;
        c = null;
        stavke = null;
    }

    @Test
    void testSetIdZapis() {
        z.setIdZapis(200);
        assertEquals(200, z.getIdZapis());
    }

    @Test
    void testSetDatumIznajmljivanjaUspesno() {
        Date noviDatum = new Date();
        z.setDatumIznajmljivanja(noviDatum);
        assertEquals(noviDatum, z.getDatumIznajmljivanja());
    }

    @Test
    void testSetDatumIznajmljivanjaNull() {
        assertThrows(IllegalArgumentException.class, () -> z.setDatumIznajmljivanja(null));
    }

    @Test
    void testSetUkupanIznosUspesno() {
        z.setUkupanIznos(1500.50);
        assertEquals(1500.50, z.getUkupanIznos());
    }

    @Test
    void testSetUkupanIznosNegativan() {
        assertThrows(IllegalArgumentException.class, () -> z.setUkupanIznos(-50));
    }

    @Test
    void testSetBibliotekarUspesno() {
        Bibliotekar noviB = new Bibliotekar(2, "Milica", "Zaric", "065123456", "milica", "sifra123");
        z.setBibliotekar(noviB);
        assertEquals(noviB, z.getBibliotekar());
    }

    @Test
    void testSetBibliotekarNull() {
        assertThrows(IllegalArgumentException.class, () -> z.setBibliotekar(null));
    }

    @Test
    void testSetCitalacUspesno() {
        Citalac noviC = new Citalac();
        noviC.setIdCitalac(20);
        z.setCitalac(noviC);
        assertEquals(noviC, z.getCitalac());
    }

    @Test
    void testSetCitalacNull() {
        assertThrows(IllegalArgumentException.class, () -> z.setCitalac(null));
    }

    @Test
    void testSetStavkeUspesno() {
        List<StavkaZapisaOIznajmljivanju> noveStavke = new ArrayList<>();
        StavkaZapisaOIznajmljivanju s = new StavkaZapisaOIznajmljivanju();
        s.setRb(1);
        noveStavke.add(s);
        
        z.setStavke(noveStavke);
        assertEquals(noveStavke, z.getStavke());
    }

    @Test
    void testSetStavkeNull() {
        assertThrows(IllegalArgumentException.class, () -> z.setStavke(null));
    }

    @Test
    void testToString() {
        String ocekivano = "idZapis=100, datumIznajmljivanja=" + datum + ", ukupanIznos=500.0, bibliotekar=" + b + ", citalac=" + c + ", stavke=" + stavke + '}';
        assertEquals(ocekivano, z.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("zapisoiznajmljivanju", z.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("datumIznajmljivanja,ukupanIznos,idCitalac,idBibliotekar", z.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = new java.sql.Date(datum.getTime());
        String ocekivano = "('" + sqlDatum + "',500.0,10,1)";
        assertEquals(ocekivano, z.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("zapisoiznajmljivanju.idZapis=100", z.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        java.sql.Date sqlDatum = new java.sql.Date(datum.getTime());
        String ocekivano = "datumIznajmljivanja='" + sqlDatum + "',ukupanIznos=500.0,idCitalac=10,idBibliotekar=1";
        assertEquals(ocekivano, z.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> z.vratiObjekatIzRS(mockResultSet));
    }

    @Test
    void testVratiListuUspesno() throws Exception {
        java.sql.Date sqlDatum = new java.sql.Date(datum.getTime());
        
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("zapisoiznajmljivanju.idZapis")).thenReturn(100);
        when(mockResultSet.getDate("zapisoiznajmljivanju.datumIznajmljivanja")).thenReturn(sqlDatum);
        when(mockResultSet.getDouble("zapisoiznajmljivanju.ukupanIznos")).thenReturn(500.0);
        
        when(mockResultSet.getInt("citalac.idCitalac")).thenReturn(10);
        when(mockResultSet.getString("citalac.ime")).thenReturn("Petar");
        when(mockResultSet.getString("citalac.prezime")).thenReturn("Peric");
        when(mockResultSet.getString("citalac.brojTel")).thenReturn("060111222");
        
        when(mockResultSet.getInt("mesto.idMesto")).thenReturn(11000);
        when(mockResultSet.getString("mesto.naziv")).thenReturn("Beograd");
        
        when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(1);
        when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Damjan");
        when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Djuric");
        when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("064123456");
        when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("damjan");
        when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("sifra123");

        List<ApstraktniDomenskiObjekat> rezultat = z.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.size());
        ZapisOIznajmljivanju mapirani = (ZapisOIznajmljivanju) rezultat.get(0);
        assertEquals(100, mapirani.getIdZapis());
        assertEquals(500.0, mapirani.getUkupanIznos());
        assertEquals(10, mapirani.getCitalac().getIdCitalac());
        assertEquals(1, mapirani.getBibliotekar().getIdBibliotekar());
        assertNotNull(mapirani.getStavke());
        assertEquals(0, mapirani.getStavke().size());
    }
}