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

    private ZapisOIznajmljivanju zapis;
    private Bibliotekar bibliotekar;
    private Citalac citalac;
    private Mesto mesto;
    private List<StavkaZapisaOIznajmljivanju> stavke;
    private Date fiksniDatum;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        fiksniDatum = new Date();
        
        mesto = new Mesto(11000, "Beograd");
        citalac = new Citalac("Laza", "Lazarevic", "064555666", mesto);
        citalac.setIdCitalac(12);

        bibliotekar = new Bibliotekar();
        bibliotekar.setIdBibliotekar(2);
        bibliotekar.setIme("Jovan");
        bibliotekar.setPrezime("Jovanovic");
        
        stavke = new ArrayList<>();
        
        zapis = new ZapisOIznajmljivanju(fiksniDatum, 1200.0, bibliotekar, citalac, stavke);
        zapis.setIdZapis(5);
    }

    @AfterEach
    void tearDown() {
        zapis = null;
        bibliotekar = null;
        citalac = null;
        mesto = null;
        stavke = null;
    }

    @Test
    void testSetIdZapis() {
        zapis.setIdZapis(10);
        assertEquals(10, zapis.getIdZapis());
    }

    @Test
    void testSetDatumIznajmljivanja() {
        Date noviDatum = new Date(fiksniDatum.getTime() + 200000);
        zapis.setDatumIznajmljivanja(noviDatum);
        assertEquals(noviDatum, zapis.getDatumIznajmljivanja());
    }

    @Test
    void testSetUkupanIznos() {
        zapis.setUkupanIznos(2500.0);
        assertEquals(2500.0, zapis.getUkupanIznos());
    }

    @Test
    void testSetBibliotekar() {
        Bibliotekar noviB = new Bibliotekar();
        noviB.setIdBibliotekar(9);
        zapis.setBibliotekar(noviB);
        assertEquals(noviB, zapis.getBibliotekar());
    }

    @Test
    void testSetCitalac() {
        Citalac noviC = new Citalac();
        noviC.setIdCitalac(88);
        zapis.setCitalac(noviC);
        assertEquals(noviC, zapis.getCitalac());
    }

    @Test
    void testSetStavke() {
        List<StavkaZapisaOIznajmljivanju> noveStavke = new ArrayList<>();
        noveStavke.add(new StavkaZapisaOIznajmljivanju());
        zapis.setStavke(noveStavke);
        assertEquals(noveStavke, zapis.getStavke());
        assertEquals(1, zapis.getStavke().size());
    }

    @Test
    void testToString() {
        String ocekivano = "idZapis=" + zapis.getIdZapis() + 
                           ", datumIznajmljivanja=" + fiksniDatum + 
                           ", ukupanIznos=1200.0" + 
                           ", bibliotekar=" + bibliotekar + 
                           ", citalac=" + citalac + 
                           ", stavke=" + stavke + '}';
        assertEquals(ocekivano, zapis.toString());
    }

    @Test
    void testVratiNazivTabele() {
        assertEquals("zapisoiznajmljivanju", zapis.vratiNazivTabele());
    }

    @Test
    void testVratiKoloneZaUbacivanje() {
        assertEquals("datumIznajmljivanja,ukupanIznos,idCitalac,idBibliotekar", zapis.vratiKoloneZaUbacivanje());
    }

    @Test
    void testVratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = new java.sql.Date(fiksniDatum.getTime());
        String ocekivano = "('" + sqlDatum + "',1200.0,12,2)";
        assertEquals(ocekivano, zapis.vratiVrednostiZaUbacivanje());
    }

    @Test
    void testVratiPrimarniKljuc() {
        assertEquals("zapisoiznajmljivanju.idZapis=5", zapis.vratiPrimarniKljuc());
    }

    @Test
    void testVratiVrednostiZaIzmenu() {
        java.sql.Date sqlDatum = new java.sql.Date(fiksniDatum.getTime());
        String ocekivano = "datumIznajmljivanja='" + sqlDatum + "',ukupanIznos=1200.0,idCitalac=12,idBibliotekar=2";
        assertEquals(ocekivano, zapis.vratiVrednostiZaIzmenu());
    }

    @Test
    void testVratiObjekatIzRS() {
        assertThrows(UnsupportedOperationException.class, () -> {
            zapis.vratiObjekatIzRS(mockResultSet);
        });
    }

    @Test
    void testVratiListuKompleksnoUspesnoMapiranje() throws Exception {
        java.sql.Date sqlSad = new java.sql.Date(fiksniDatum.getTime());

        when(mockResultSet.next()).thenReturn(true, false);

        when(mockResultSet.getInt("zapisoiznajmljivanju.idZapis")).thenReturn(50);
        when(mockResultSet.getDate("zapisoiznajmljivanju.datumIznajmljivanja")).thenReturn(sqlSad);
        when(mockResultSet.getDouble("zapisoiznajmljivanju.ukupanIznos")).thenReturn(3000.0);

        when(mockResultSet.getInt("citalac.idCitalac")).thenReturn(15);
        when(mockResultSet.getString("citalac.ime")).thenReturn("Pera");
        when(mockResultSet.getString("citalac.prezime")).thenReturn("Peric");
        when(mockResultSet.getString("citalac.brojTel")).thenReturn("061234");

        when(mockResultSet.getInt("mesto.idMesto")).thenReturn(21000);
        when(mockResultSet.getString("mesto.naziv")).thenReturn("Novi Sad");

        when(mockResultSet.getInt("bibliotekar.idBibliotekar")).thenReturn(3);
        when(mockResultSet.getString("bibliotekar.ime")).thenReturn("Ana");
        when(mockResultSet.getString("bibliotekar.prezime")).thenReturn("Anic");
        when(mockResultSet.getString("bibliotekar.brojTel")).thenReturn("062999");
        when(mockResultSet.getString("bibliotekar.korisnickoIme")).thenReturn("ana123");
        when(mockResultSet.getString("bibliotekar.sifra")).thenReturn("anaSifra");

        List<ApstraktniDomenskiObjekat> rezultat = zapis.vratiListu(mockResultSet);

        assertNotNull(rezultat);
        assertEquals(1, rezultat.size());

        ZapisOIznajmljivanju z = (ZapisOIznajmljivanju) rezultat.get(0);
        assertEquals(50, z.getIdZapis());
        assertEquals(3000.0, z.getUkupanIznos());
        
        assertNotNull(z.getCitalac());
        assertEquals(15, z.getCitalac().getIdCitalac());
        assertEquals("Pera", z.getCitalac().getIme());
        assertEquals(21000, z.getCitalac().getMesto().getIdMesto());
        assertEquals("Novi Sad", z.getCitalac().getMesto().getNaziv());

        assertNotNull(z.getBibliotekar());
        assertEquals(3, z.getBibliotekar().getIdBibliotekar());
        assertEquals("Ana", z.getBibliotekar().getIme());
        assertEquals("ana123", z.getBibliotekar().getKorisnickoIme());

        assertNotNull(z.getStavke());
        assertEquals(0, z.getStavke().size());
    }
}