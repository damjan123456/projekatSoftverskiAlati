package rs.ac.bg.fon.server.operacija.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrijaviBibliotekarTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private PrijaviBibliotekar operacija;
    private Bibliotekar prosledjeniKredencijali;

    @BeforeEach
    void setUp() {
        operacija = new PrijaviBibliotekar(mockBroker);
        
        prosledjeniKredencijali = new Bibliotekar();
        prosledjeniKredencijali.setKorisnickoIme("damjan");
        prosledjeniKredencijali.setSifra("kod123");
    }

    @Test
    void testPrijaviBibliotekarUspesno() throws Exception {
        List<Bibliotekar> listaIzBaze = new ArrayList<>();
        Bibliotekar bIzBaze = new Bibliotekar();
        bIzBaze.setKorisnickoIme("damjan");
        bIzBaze.setSifra("kod123");
        bIzBaze.setIme("Damjan");
        listaIzBaze.add(bIzBaze);

        when(mockBroker.getAll(any(Bibliotekar.class), isNull())).thenReturn(listaIzBaze);

        operacija.izvrsi(prosledjeniKredencijali);

        Bibliotekar ulogovani = operacija.getBibliotekar();
        assertNotNull(ulogovani);
        assertEquals("Damjan", ulogovani.getIme());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
    }

    @Test
    void testPrijaviBibliotekarPogresniKredencijaliVracaNull() throws Exception {
        List<Bibliotekar> listaIzBaze = new ArrayList<>();
        Bibliotekar bNekiDrugi = new Bibliotekar();
        bNekiDrugi.setKorisnickoIme("petar");
        bNekiDrugi.setSifra("sifra1");
        listaIzBaze.add(bNekiDrugi);

        when(mockBroker.getAll(any(Bibliotekar.class), isNull())).thenReturn(listaIzBaze);

        operacija.izvrsi(prosledjeniKredencijali);

        assertNull(operacija.getBibliotekar());
        
        verify(mockBroker).connect();
        verify(mockBroker).commit(); 
    }

    @Test
    void testPrijaviBibliotekarNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Prijava nije moguca", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPrijaviBibliotekarPogresanTipParametraBacaIzuzetak() throws Exception {
        String pogresanParam = "Nisam objekat Bibliotekar";

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(pogresanParam));
        assertEquals("Prijava nije moguca", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPrijaviBibliotekarGreskaBazePokreceRollback() throws Exception {
        when(mockBroker.getAll(any(Bibliotekar.class), isNull())).thenThrow(new Exception("Database Timeout"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(prosledjeniKredencijali));
        assertEquals("Database Timeout", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}