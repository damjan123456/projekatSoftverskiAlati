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
    Bibliotekar b;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        b = new Bibliotekar(1, "Damjan", "Djuric", "064123456", "damjan", "sifra");
    }

    @AfterEach
    void tearDown() {
        b = null;
    }

    @ParameterizedTest
    @CsvSource({
        "damja, sifra123, damja, sifra123, true",      
        "damja, sifra123, pera,   sifra123, false",    
        "damja, sifra123, damja,  pogresna, false",     
        "damja, sifra123, pera,   pogresna, false"    
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
    void testSetBrojTel() {
        b.setBrojTel("064123456");
        assertEquals("064123456", b.getBrojTel());
    }

    @Test
    void testSetIdBibliotekar() {
        b.setIdBibliotekar(1);
        assertEquals(1, b.getIdBibliotekar());
    }

    @Test
    void testSetIme() {
        b.setIme("Damjan");
        assertEquals("Damjan", b.getIme());
    }

    @Test
    void testSetKorisnickoIme() {
        b.setKorisnickoIme("damjan");
        assertEquals("damjan", b.getKorisnickoIme());
    }

    @Test
    void testSetPrezime() {
        b.setPrezime("Djuric");
        assertEquals("Djuric", b.getPrezime());
    }

    @Test
    void testSetSifra() {
        b.setSifra("sifra");
        assertEquals("sifra", b.getSifra());
    }

    @Test
    void testToString() {
        String ocekivano = "Damjan Djuric";
        assertEquals(ocekivano, b.toString());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("ime,prezime,brojTel,korisnickoIme,sifra", b.vratiKoloneZaUbacivanje());
    }

    @Test
void testVratiListuViseRedovaUspesnoMapiranje() throws Exception {
    when(mockResultSet.next()).thenReturn(true, true, true, false); 
    
    when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(10, 20, 30);
    when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Ana", "Boris", "Ceca");
    when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Anić", "Borić", "Ceciće");
    when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("061", "062", "063");
    when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("ana", "boris", "ceca");
    when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("ana123", "boris123", "ceca123");

    List<ApstraktniDomenskiObjekat> rezultat = b.vratiListu(mockResultSet);

    assertNotNull(rezultat);
    assertEquals(3, rezultat.size(), "Lista bi trebalo da sadrži tačno 3 objekta.");
    
    Bibliotekar b1 = (Bibliotekar) rezultat.get(0);
    assertEquals(10, b1.getIdBibliotekar());
    assertEquals("Ana", b1.getIme());
    
    Bibliotekar b2 = (Bibliotekar) rezultat.get(1);
    assertEquals(20, b2.getIdBibliotekar());
    assertEquals("Boris", b2.getIme());
    
    Bibliotekar b3 = (Bibliotekar) rezultat.get(2);
    assertEquals(30, b3.getIdBibliotekar());
    assertEquals("Ceca", b3.getIme());
}

    @Test
    void testVratiNazivTabele() {
        assertEquals("bibliotekar", b.vratiNazivTabele());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            b.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("bibliotekar.idBibliotekar=1", b.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        String ocekivano = "ime='Damjan',prezime='Djuric',brojTel='064123456',korisnickoIme='damjan',sifra='sifra'";
        assertEquals(ocekivano, b.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        String ocekivano = "('Damjan','Djuric','064123456','damjan','sifra')";
        assertEquals(ocekivano, b.vratiVrednostiZaUbacivanje());
    }
}
