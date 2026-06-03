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
public class BibliotekarTest {

    private Bibliotekar b;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        b = new Bibliotekar(1, "Damjan", "Djuric", "064123456", "damjan", "sifra123");
    }

    @AfterEach
    void tearDown() {
        b = null;
    }

    @ParameterizedTest
    @CsvSource({
        "damjan, sifra123, damjan, sifra123, true",      
        "damjan, sifra123, pera,   sifra123, false",    
        "damjan, sifra123, damjan, pogresna123, false",     
        "damjan, sifra123, pera,   pogresna123, false"    
    })
    void testEquals(String korIme1, String sifra1, String korIme2, String sifra2, boolean jednako) {
        b.setKorisnickoIme(korIme1);
        b.setSifra(sifra1);
        
        Bibliotekar b2 = new Bibliotekar();
        b2.setKorisnickoIme(korIme2);
        b2.setSifra(sifra2);
        
        assertEquals(jednako, b.equals(b2));
    }

    @Test
    void testSetIdBibliotekar() {
        b.setIdBibliotekar(5);
        assertEquals(5, b.getIdBibliotekar());
    }

    @Test
    void testSetImeUspesno() {
        b.setIme("Nikola");
        assertEquals("Nikola", b.getIme());
    }

    @Test
    void testSetImeNull() {
        assertThrows(IllegalArgumentException.class, () -> b.setIme(null));
    }

    @Test
    void testSetImePrazno() {
        assertThrows(IllegalArgumentException.class, () -> b.setIme("   "));
    }

    @Test
    void testSetImePrekratko() {
        // Validacija baca izuzetak ako je dužina <= 2
        assertThrows(IllegalArgumentException.class, () -> b.setIme("An"));
    }

    @Test
    void testSetPrezimeUspesno() {
        b.setPrezime("Petrovic");
        assertEquals("Petrovic", b.getPrezime());
    }

    @Test
    void testSetPrezimeNull() {
        assertThrows(IllegalArgumentException.class, () -> b.setPrezime(null));
    }

    @Test
    void testSetPrezimePrazno() {
        assertThrows(IllegalArgumentException.class, () -> b.setPrezime("   "));
    }

    @Test
    void testSetPrezimePrekratko() {
        // PAŽNJA: U tvom kodu u setPrezime() greškom proveravaš dužinu atributa 'ime' umesto 'prezime'
        // (if (ime.trim().length() <= 2)). Test će pasti ako ime ima više od 2 karaktera.
        // Ovdje testiramo logiku kako je napisana.
        assertThrows(IllegalArgumentException.class, () -> b.setPrezime("jo"));
    }

    @Test
    void testSetBrojTelUspesnoDevetCifara() {
        b.setBrojTel("064123456");
        assertEquals("064123456", b.getBrojTel());
    }

    @Test
    void testSetBrojTelUspesnoDesetCifara() {
        b.setBrojTel("0641234567");
        assertEquals("0641234567", b.getBrojTel());
    }

    @Test
    void testSetBrojTelNull() {
        assertThrows(IllegalArgumentException.class, () -> b.setBrojTel(null));
    }

    @Test
    void testSetBrojTelPrazan() {
        assertThrows(IllegalArgumentException.class, () -> b.setBrojTel("  "));
    }

    @Test
    void testSetBrojTelPrekratak() {
        assertThrows(IllegalArgumentException.class, () -> b.setBrojTel("064123"));
    }

    @Test
    void testSetBrojTelPredugacak() {
        assertThrows(IllegalArgumentException.class, () -> b.setBrojTel("064123456789"));
    }

    @Test
    void testSetKorisnickoImeUspesno() {
        b.setKorisnickoIme("damjan_fon");
        assertEquals("damjan_fon", b.getKorisnickoIme());
    }

    @Test
    void testSetKorisnickoImeNull() {
        assertThrows(IllegalArgumentException.class, () -> b.setKorisnickoIme(null));
    }

    @Test
    void testSetKorisnickoImePrazno() {
        assertThrows(IllegalArgumentException.class, () -> b.setKorisnickoIme(" "));
    }

    @Test
    void testSetSifraUspesno() {
        b.setSifra("sigurna1");
        assertEquals("sigurna1", b.getSifra());
    }

    @Test
    void testSetSifraNull() {
        assertThrows(IllegalArgumentException.class, () -> b.setSifra(null));
    }

    @Test
    void testSetSifraPrekratka() {
        assertThrows(IllegalArgumentException.class, () -> b.setSifra("sif1"));
    }

    @Test
    void testToString() {
        assertEquals("Damjan Djuric", b.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("bibliotekar", b.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("ime,prezime,brojTel,korisnickoIme,sifra", b.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "('Damjan','Djuric','064123456','damjan','sifra123')";
        assertEquals(ocekivano, b.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("bibliotekar.idBibliotekar=1", b.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "ime='Damjan',prezime='Djuric',brojTel='064123456',korisnickoIme='damjan',sifra='sifra123'";
        assertEquals(ocekivano, b.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> b.vratiObjekatIzRS(mockResultSet));
    }

    @Test
    void testVratiListuUspesno() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false); 
        
        when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(10);
        when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Marko");
        when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Markovic");
        when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("065123456");
        when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("marko1");
        when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("sifra123");

        List<ApstraktniDomenskiObjekat> rezultat = b.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.size());
        Bibliotekar mapirani = (Bibliotekar) rezultat.get(0);
        assertEquals(10, mapirani.getIdBibliotekar());
        assertEquals("Marko", mapirani.getIme());
        assertEquals("Markovic", mapirani.getPrezime());
    }
}