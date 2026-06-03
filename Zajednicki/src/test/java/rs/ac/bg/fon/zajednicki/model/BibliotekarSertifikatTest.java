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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BibliotekarSertifikatTest {

    private BibliotekarSertifikat bs;
    private Bibliotekar b;
    private Sertifikat s;
    private Date danas;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        danas = new Date();
        b = new Bibliotekar(1, "Damjan", "Djuric", "064123456", "damjan", "sifra123");
        
        s = new Sertifikat();
        s.setIdSertifikat(5);
        s.setNaziv("Oracle Java SE");
        s.setInstitucija("Oracle");

        bs = new BibliotekarSertifikat(b, s, danas);
    }

    @AfterEach
    void tearDown() {
        bs = null;
        b = null;
        s = null;
    }

    @Test
    void testSetBibliotekarUspesno() {
        Bibliotekar novi = new Bibliotekar(2, "Petar", "Peric", "065123456", "pera", "pera1234");
        bs.setBibliotekar(novi);
        assertEquals(novi, bs.getBibliotekar());
    }

    @Test
    void testSetBibliotekarNull() {
        assertThrows(IllegalArgumentException.class, () -> bs.setBibliotekar(null));
    }

    @Test
    void testSetSertifikatUspesno() {
        Sertifikat noviSertifikat = new Sertifikat();
        noviSertifikat.setIdSertifikat(10);
        bs.setSertifikat(noviSertifikat);
        assertEquals(noviSertifikat, bs.getSertifikat());
    }

    @Test
    void testSetSertifikatNull() {
        assertThrows(IllegalArgumentException.class, () -> bs.setSertifikat(null));
    }

    @Test
    void testSetDatumIzdavanjaUspesno() {
        Date noviDatum = new Date(danas.getTime() - 86400000); // juče
        bs.setDatumIzdavanja(noviDatum);
        assertEquals(noviDatum, bs.getDatumIzdavanja());
    }

    @Test
    void testSetDatumIzdavanjaNull() {
        assertThrows(IllegalArgumentException.class, () -> bs.setDatumIzdavanja(null));
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("bibliotekarsertifikat", bs.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("idBibliotekar,idSertifikat,datumIzdavanja", bs.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "(1,5,'" + danas + "')";
        assertEquals(ocekivano, bs.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        String ocekivano = "bibliotekarsertifikat.idBibliotekar=1 AND bibliotekarsertifikat.idSertifikat=5";
        assertEquals(ocekivano, bs.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "idBibliotekar=1,idSertifikat=5,datumIzdavanja='" + danas + "'";
        assertEquals(ocekivano, bs.vratiVrednostiZaIzmenu() == null ? bs.vratiVrednostiZaIzmenu() : bs.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> bs.vratiObjekatIzRS(mockResultSet));
    }

    @Test
    void testEqualsIsteVrednosti() {
        BibliotekarSertifikat bs2 = new BibliotekarSertifikat(b, s, danas);
        assertEquals(true, bs.equals(bs2));
    }

    @Test
    void testVratiListuUspesno() throws Exception {
        java.sql.Date sqlDatum = new java.sql.Date(danas.getTime());
        
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getDate("bibliotekarsertifikat.datumIzdavanja")).thenReturn(sqlDatum);
        
        when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(1);
        when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Damjan");
        when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Djuric");
        when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("064123456");
        when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("damjan");
        when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("sifra123");
        
        when(mockResultSet.getInt("sertifikat.idSertifikat")).thenReturn(5);
        when(mockResultSet.getString("sertifikat.naziv")).thenReturn("Oracle Java SE");
        when(mockResultSet.getString("sertifikat.institucija")).thenReturn("Oracle");

        List<ApstraktniDomenskiObjekat> rezultat = bs.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.size());
        
        BibliotekarSertifikat mapirani = (BibliotekarSertifikat) rezultat.get(0);
        assertEquals(1, mapirani.getBibliotekar().getIdBibliotekar());
        assertEquals(5, mapirani.getSertifikat().getIdSertifikat());
        assertEquals(sqlDatum, mapirani.getDatumIzdavanja());
    }
}