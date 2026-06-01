package rs.ac.bg.fon.server.operacija.zapis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PretraziZapisOIznajmljivanjuTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private PretraziZapisOIznajmljivanju operacija;
    private ZapisOIznajmljivanju kriterijumZapis;

    @BeforeEach
    void setUp() {
        operacija = new PretraziZapisOIznajmljivanju(mockBroker);

        Bibliotekar b = new Bibliotekar();
        b.setIdBibliotekar(5);

        Citalac c = new Citalac();
        c.setIdCitalac(12);

        kriterijumZapis = new ZapisOIznajmljivanju();
        kriterijumZapis.setIdZapis(101);
        kriterijumZapis.setDatumIznajmljivanja(new Date());
        kriterijumZapis.setUkupanIznos(500.0);
        kriterijumZapis.setBibliotekar(b);
        kriterijumZapis.setCitalac(c);
    }

    @Test
    void testPretraziZapisOIznajmljivanjuUspesno() throws Exception {
        List<StavkaZapisaOIznajmljivanju> lazneStavke = new ArrayList<>();
        lazneStavke.add(new StavkaZapisaOIznajmljivanju());
        lazneStavke.add(new StavkaZapisaOIznajmljivanju());

        String ocekivaniUslov = " JOIN knjiga ON stavkazapisaoiznajmljivanju.idKnjiga=knjiga.idKnjiga WHERE stavkazapisaoiznajmljivanju.idZapis=101";

        when(mockBroker.getAll(any(StavkaZapisaOIznajmljivanju.class), eq(ocekivaniUslov))).thenReturn(lazneStavke);

        operacija.izvrsi(kriterijumZapis);

        ZapisOIznajmljivanju rezultat = operacija.getZapis();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.getStavke().size());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testPretraziZapisNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da doda zapis o iznajmljivanju", ex.getMessage());
        
        verify(mockBroker).rollback();
    }

    @Test
    void testPretraziZapisGreskaBazePokreceRollback() throws Exception {
        String ocekivaniUslov = " JOIN knjiga ON stavkazapisaoiznajmljivanju.idKnjiga=knjiga.idKnjiga WHERE stavkazapisaoiznajmljivanju.idZapis=101";
        
        when(mockBroker.getAll(any(StavkaZapisaOIznajmljivanju.class), eq(ocekivaniUslov)))
                .thenThrow(new Exception("Konekcija sa bazom je prekinuta"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(kriterijumZapis));
        assertEquals("Konekcija sa bazom je prekinuta", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}