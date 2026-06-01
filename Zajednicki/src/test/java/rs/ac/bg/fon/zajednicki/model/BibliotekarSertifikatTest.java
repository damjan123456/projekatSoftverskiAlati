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
public class BibliotekarSertifikatTest {

    BibliotekarSertifikat bs;
    Bibliotekar b;
    Sertifikat s;
    Date datum;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        b = new Bibliotekar();
        b.setIdBibliotekar(1);
        b.setIme("Damjan");
        b.setPrezime("Djuric");

        s = new Sertifikat();
        s.setIdSertifikat(10);
        s.setNaziv("Oracle Java");
        s.setInstitucija("Oracle");

        datum = new Date();
        bs = new BibliotekarSertifikat(b, s, datum);
    }

    @AfterEach
    void tearDown() {
        bs = null;
        b = null;
        s = null;
        datum = null;
    }

    @ParameterizedTest
    @CsvSource({
        "damjan, djuric, aaa, damjan, djuric, aaa, true",  
        "damjan, djuric, aaa, damjan, djuric, bbb, false",
        "damjan, djuric, aaa, damjan, pavlovic, aaa, false", 
        "damjan, djuric, aaa, mihailo, djuric, aaa, false",
        "damjan, djuric, bbb, damjan, djuric, aaa, false",
        "damjan, pavlovic, aaa, damjan, djuric, aaa, false", 
        "mihailo, djuric, aaa, damjan, djuric, aaa, false"    
    })
    void testEquals(String i1, String p1, String s1, String i2, String p2, String s2, boolean jednako) {
        Bibliotekar b1 = new Bibliotekar();
        b1.setKorisnickoIme(i1);
        b1.setSifra(p1);
        Sertifikat sert1 = new Sertifikat();
        sert1.setNaziv(s1);
        
        bs.setBibliotekar(b1);
        bs.setSertifikat(sert1);
        bs.setDatumIzdavanja(datum); 

        Bibliotekar b2 = new Bibliotekar();
        b2.setKorisnickoIme(i2);
        b2.setSifra(p2);
        Sertifikat sert2 = new Sertifikat();
        sert2.setNaziv(s2);
        
        BibliotekarSertifikat bs2 = new BibliotekarSertifikat();
        bs2.setBibliotekar(b2);
        bs2.setSertifikat(sert2);
        bs2.setDatumIzdavanja(datum); 

        assertEquals(jednako, bs.equals(bs2));
    }

    @Test
    void testSetBibliotekar() {
        Bibliotekar novi = new Bibliotekar();
        novi.setIdBibliotekar(5);
        bs.setBibliotekar(novi);
        assertEquals(novi, bs.getBibliotekar());
    }

    @Test
    void testSetSertifikat() {
        Sertifikat novi = new Sertifikat();
        novi.setIdSertifikat(99);
        bs.setSertifikat(novi);
        assertEquals(novi, bs.getSertifikat());
    }

    @Test
    void testSetDatumIzdavanja() {
        Date noviDatum = new Date(1262304000000L); 
        bs.setDatumIzdavanja(noviDatum);
        assertEquals(noviDatum, bs.getDatumIzdavanja());
    }

    @Test
    void testToString() {
        String ocekivano = b + ", sertifikat=" + s + ", datumIzdavanja=" + datum;
        assertEquals(ocekivano, bs.toString());
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
        String ocekivano = "(1,10,'" + datum + "')";
        assertEquals(ocekivano, bs.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("bibliotekarsertifikat.idBibliotekar=1 AND bibliotekarsertifikat.idSertifikat=10", bs.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "idBibliotekar=1,idSertifikat=10,datumIzdavanja='" + datum + "'";
        assertEquals(ocekivano, bs.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            bs.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
        when(mockResultSet.next()).thenReturn(true, true, false);

        java.sql.Date sqlDatum = new java.sql.Date(datum.getTime());
        when(mockResultSet.getDate("bibliotekarsertifikat.datumIzdavanja")).thenReturn(sqlDatum, sqlDatum);

        when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(10, 20);
        when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Ana", "Boris");
        when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Anić", "Borić");
        when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("061", "062");
        when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("ana", "boris");
        when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("ana123", "boris123");

        when(mockResultSet.getInt("sertifikat.idSertifikat")).thenReturn(100, 200);
        when(mockResultSet.getString("sertifikat.naziv")).thenReturn("AAA", "BBB");
        when(mockResultSet.getString("sertifikat.institucija")).thenReturn("Oracle", "Oracle");

        List<ApstraktniDomenskiObjekat> rezultat = bs.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());

        BibliotekarSertifikat bs1 = (BibliotekarSertifikat) rezultat.get(0);
        assertEquals(10, bs1.getBibliotekar().getIdBibliotekar());
        assertEquals("Ana", bs1.getBibliotekar().getIme());
        assertEquals(100, bs1.getSertifikat().getIdSertifikat());
        assertEquals("AAA", bs1.getSertifikat().getNaziv());

        BibliotekarSertifikat bs2 = (BibliotekarSertifikat) rezultat.get(1);
        assertEquals(20, bs2.getBibliotekar().getIdBibliotekar());
        assertEquals("Boris", bs2.getBibliotekar().getIme());
        assertEquals(200, bs2.getSertifikat().getIdSertifikat());
        assertEquals("BBB", bs2.getSertifikat().getNaziv());
    }
}