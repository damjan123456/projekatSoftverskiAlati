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
class PromeniZapisOIznajmljivanjuTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private PromeniZapisOIznajmljivanju operacija;
    private ZapisOIznajmljivanju noviZapisSveukupno;

    @BeforeEach
    void setUp() {
        operacija = new PromeniZapisOIznajmljivanju(mockBroker);

        Bibliotekar b = new Bibliotekar();
        b.setIdBibliotekar(1);

        Citalac c = new Citalac();
        c.setIdCitalac(1);

        noviZapisSveukupno = new ZapisOIznajmljivanju();
        noviZapisSveukupno.setIdZapis(50); 
        noviZapisSveukupno.setDatumIznajmljivanja(new Date());
        noviZapisSveukupno.setUkupanIznos(2000.0);
        noviZapisSveukupno.setBibliotekar(b);
        noviZapisSveukupno.setCitalac(c);
    }

    @Test
    void testPromeniZapisOIznajmljivanjuUspesnaSinhronizacijaStavki() throws Exception {
        List<StavkaZapisaOIznajmljivanju> stareStavkeIzBaze = new ArrayList<>();
        
        StavkaZapisaOIznajmljivanju stara1 = new StavkaZapisaOIznajmljivanju();
        stara1.setZapis(50);
        stara1.setRb(1);
        
        StavkaZapisaOIznajmljivanju stara2 = new StavkaZapisaOIznajmljivanju();
        stara2.setZapis(50);
        stara2.setRb(2);
        
        stareStavkeIzBaze.add(stara1);
        stareStavkeIzBaze.add(stara2);

        List<StavkaZapisaOIznajmljivanju> noveStavkeKorisnika = new ArrayList<>();
        
        StavkaZapisaOIznajmljivanju novaZaIzmenu = new StavkaZapisaOIznajmljivanju();
        novaZaIzmenu.setRb(1); 
        
        StavkaZapisaOIznajmljivanju potpunoNova = new StavkaZapisaOIznajmljivanju();
        potpunoNova.setRb(3); 
                
        noveStavkeKorisnika.add(novaZaIzmenu);
        noveStavkeKorisnika.add(potpunoNova);
        noviZapisSveukupno.setStavke(noveStavkeKorisnika);

        String ocekivaniUslov = " JOIN knjiga ON stavkazapisaoiznajmljivanju.idKnjiga=knjiga.idKnjiga WHERE stavkazapisaoiznajmljivanju.idZapis=50";
        when(mockBroker.getAll(any(StavkaZapisaOIznajmljivanju.class), eq(ocekivaniUslov))).thenReturn(stareStavkeIzBaze);

        operacija.izvrsi(noviZapisSveukupno);

        verify(mockBroker).connect();
        verify(mockBroker).edit(noviZapisSveukupno);
        
        verify(mockBroker).edit(novaZaIzmenu);
        verify(mockBroker).add(potpunoNova);
        verify(mockBroker).delete(stara2);
        
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testPromeniZapisNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da doda zapis o iznajmljivanju", ex.getMessage());
        verify(mockBroker).rollback();
    }

    @Test
    void testPromeniZapisGreskaPriIzmeniKrovnogZapisaPokreceRollback() throws Exception {
        doThrow(new Exception("Lock wait timeout exceeded")).when(mockBroker).edit(any(ZapisOIznajmljivanju.class));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(noviZapisSveukupno));
        assertEquals("Lock wait timeout exceeded", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
        verify(mockBroker, never()).getAll(any(), anyString());
    }
}