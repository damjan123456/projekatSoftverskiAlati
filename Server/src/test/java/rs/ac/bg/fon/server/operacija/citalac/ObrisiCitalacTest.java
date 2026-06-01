package rs.ac.bg.fon.server.operacija.citalac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Citalac;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObrisiCitalacTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private ObrisiCitalac operacija;
    private Citalac citalac;

    @BeforeEach
    void setUp() {
        operacija = new ObrisiCitalac(mockBroker);
        citalac = new Citalac();
        citalac.setIdCitalac(1);
    }

    @Test
    void testObrisiCitalacUspesno() throws Exception {
        operacija.izvrsi(citalac);

        verify(mockBroker).connect();
        verify(mockBroker).delete(citalac);
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testObrisiCitalacNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da obrise citaoca", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
        verify(mockBroker, never()).delete(any());
    }

    @Test
    void testObrisiCitalacPogresanTipObjektaBacaIzuzetak() throws Exception {
        String pogresanParametar = "String umesto objekta Citalac";

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(pogresanParametar));
        assertEquals("Sistem ne moze da obrise citaoca", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testObrisiCitalacGreskaBazePokreceRollback() throws Exception {
        doThrow(new Exception("SQL FK Constraint Error")).when(mockBroker).delete(citalac);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(citalac));
        assertEquals("SQL FK Constraint Error", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).delete(citalac);
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}