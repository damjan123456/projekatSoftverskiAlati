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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbaciCitalacTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private UbaciCitalac operacija; 

    private Citalac ispravanCitalac;

    @BeforeEach
    void setUp() {
        operacija = new UbaciCitalac(mockBroker);

        Mesto m = new Mesto();
        m.setIdMesto(1);
        m.setNaziv("Beograd");

        ispravanCitalac = new Citalac();
        ispravanCitalac.setIme("Petar");
        ispravanCitalac.setPrezime("Petrovic");
        ispravanCitalac.setBrojTel("064123456");
        ispravanCitalac.setMesto(m);
    }

    @Test
    void testUbaciCitalacUspesno() throws Exception {
        operacija.izvrsi(ispravanCitalac);

        verify(mockBroker).connect();
        verify(mockBroker).add(ispravanCitalac);
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testUbaciCitalacNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da doda citaoca", ex.getMessage());
        
        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
        verify(mockBroker, never()).add(any());
        verify(mockBroker, never()).commit();
    }

    @Test
    void testUbaciCitalacPraznoImeBacaIzuzetak() throws Exception {
        // Hvata se IllegalArgumentException direktno iz modela pri setovanju
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setIme("");
        });
        assertTrue(ex.getMessage().contains("Ime"));
        
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testUbaciCitalacPredugacakTelefonBacaIzuzetak() throws Exception {
        // Hvata se IllegalArgumentException direktno iz modela pri setovanju
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setBrojTel("064123456789111"); 
        });
        assertTrue(ex.getMessage().contains("cifara"));
        
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testUbaciCitalacMestoNullBacaIzuzetak() throws Exception {
        // Hvata se IllegalArgumentException direktno iz modela pri setovanju
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            ispravanCitalac.setMesto(null);
        });
        assertTrue(ex.getMessage().contains("Mesto"));
        
        verify(mockBroker, never()).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testUbaciCitalacGreskaUBaziPokreceRollback() throws Exception {
        doThrow(new Exception("SQL Error")).when(mockBroker).add(ispravanCitalac);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanCitalac));
        assertEquals("SQL Error", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).add(ispravanCitalac);
        verify(mockBroker).rollback(); 
        verify(mockBroker, never()).commit();
    }
}