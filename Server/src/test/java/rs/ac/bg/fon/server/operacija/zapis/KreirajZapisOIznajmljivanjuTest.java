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
class KreirajZapisOIznajmljivanjuTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private KreirajZapisOIznajmljivanju operacija;
    private ZapisOIznajmljivanju ispravanZapis;

    @BeforeEach
    void setUp() {
        operacija = new KreirajZapisOIznajmljivanju();
        operacija = new KreirajZapisOIznajmljivanju();
    }
    
    private void inicijalizujOperaciju() {
        operacija = new KreirajZapisOIznajmljivanju(mockBroker);
        
        Bibliotekar b = new Bibliotekar();
        b.setIdBibliotekar(1);
        
        Citalac c = new Citalac();
        c.setIdCitalac(1);
        
        ispravanZapis = new ZapisOIznajmljivanju();
        ispravanZapis.setDatumIznajmljivanja(new Date());
        ispravanZapis.setUkupanIznos(1500.0);
        ispravanZapis.setBibliotekar(b);
        ispravanZapis.setCitalac(c);
        
        List<StavkaZapisaOIznajmljivanju> stavke = new ArrayList<>();
        stavke.add(new StavkaZapisaOIznajmljivanju());
        stavke.add(new StavkaZapisaOIznajmljivanju());
        ispravanZapis.setStavke(stavke);
    }

    @Test
    void testKreirajZapisOIznajmljivanjuUspesno() throws Exception {
        inicijalizujOperaciju();
        
        when(mockBroker.addReturnKey(ispravanZapis)).thenReturn(42);

        operacija.izvrsi(ispravanZapis);

        verify(mockBroker).connect();
        verify(mockBroker).addReturnKey(ispravanZapis);
        
        verify(mockBroker, times(2)).add(any(StavkaZapisaOIznajmljivanju.class));
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
        
        assertEquals(42, ispravanZapis.getStavke().get(0).getZapis());
        assertEquals(42, ispravanZapis.getStavke().get(1).getZapis());
    }

    @Test
    void testKreirajZapisNullObjekatBacaIzuzetak() throws Exception {
        operacija = new KreirajZapisOIznajmljivanju(mockBroker);
        
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da doda zapis o iznajmljivanju", ex.getMessage());
        verify(mockBroker).rollback();
    }

    @Test
    void testKreirajZapisDatumNullBacaIzuzetak() throws Exception {
        inicijalizujOperaciju();
        ispravanZapis.setDatumIznajmljivanja(null);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanZapis));
        assertEquals("GRESKA DATUM", ex.getMessage());
        
        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testKreirajZapisNegativanIznosBacaIzuzetak() throws Exception {
        inicijalizujOperaciju();
        ispravanZapis.setUkupanIznos(-50.0);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanZapis));
        assertEquals("GRESKA UKUPAN IZNOS", ex.getMessage());
        
        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testKreirajZapisBibliotekarNullBacaIzuzetak() throws Exception {
        inicijalizujOperaciju();
        ispravanZapis.setBibliotekar(null);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanZapis));
        assertEquals("GRESKA BIBLIOTEKAR", ex.getMessage());
        
        verify(mockBroker).rollback();
    }

    @Test
    void testKreirajZapisCitalacNullBacaIzuzetak() throws Exception {
        inicijalizujOperaciju();
        ispravanZapis.setCitalac(null);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanZapis));
        assertEquals("GRESKA CITALAC", ex.getMessage());
        
        verify(mockBroker).rollback();
    }

    @Test
    void testKreirajZapisGreskaPriUpisuStavkePokreceRollback() throws Exception {
        inicijalizujOperaciju();
        
        when(mockBroker.addReturnKey(ispravanZapis)).thenReturn(100);
        doNothing().doThrow(new Exception("Greška upisa stavke u DB")).when(mockBroker).add(any(StavkaZapisaOIznajmljivanju.class));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanZapis));
        assertEquals("Greška upisa stavke u DB", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}