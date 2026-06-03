package rs.ac.bg.fon.server.operacija.citalac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Mesto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromeniCitalacTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private PromeniCitalac operacija;
    private Citalac ispravanCitalac;

    @BeforeEach
    void setUp() {
        operacija = new PromeniCitalac(mockBroker);

        Mesto m = new Mesto();
        m.setIdMesto(1);
        m.setNaziv("Beograd");

        ispravanCitalac = new Citalac();
        ispravanCitalac.setIdCitalac(10);
        ispravanCitalac.setIme("Nikola");
        ispravanCitalac.setPrezime("Nikolic");
        ispravanCitalac.setBrojTel("065987654");
        ispravanCitalac.setMesto(m);
    }

    @Test
    void testPromeniCitalacUspesno() throws Exception {
        operacija.izvrsi(ispravanCitalac);

        verify(mockBroker).connect();
        verify(mockBroker).edit(ispravanCitalac);
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testPromeniCitalacNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da izmeni citaoca", ex.getMessage());
        verify(mockBroker).rollback();
    }

    @Test
    void testPromeniCitalacPraznoImeBacaIzuzetak() throws Exception {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setIme("");
        });
        assertTrue(ex.getMessage().contains("Ime"));
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPromeniCitalacPraznoPrezimeBacaIzuzetak() throws Exception {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setPrezime(null);
        });
        assertTrue(ex.getMessage().contains("Prezime"));
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPromeniCitalacPredugacakTelefonBacaIzuzetak() throws Exception {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setBrojTel("06112345678912345"); 
        });
        assertTrue(ex.getMessage().contains("cifara"));
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPromeniCitalacMestoNullBacaIzuzetak() throws Exception {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setMesto(null);
        });
        assertTrue(ex.getMessage().contains("Mesto"));
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testPromeniCitalacGreskaUBaziPokreceRollback() throws Exception {
        doThrow(new Exception("Baza prekinula vezu")).when(mockBroker).edit(ispravanCitalac);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanCitalac));
        assertEquals("Baza prekinula vezu", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).edit(ispravanCitalac);
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}